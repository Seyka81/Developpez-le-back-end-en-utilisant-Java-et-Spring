package com.chatop.controllers;

import com.chatop.model.RentalDTO;
import com.chatop.model.UserDTO;
import com.chatop.services.RentalService;
import com.chatop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    /**
     * Correspond à http://localhost:3001 (voir application.properties)
     */
    @Value("${server.base-url}")
    private String baseUrl;

    /**
     * Méthode d'afficher une liste de rentals
     * 
     * @return un statut réponse 200
     */
    @GetMapping("rentals")
    public ResponseEntity<Map<String, List<RentalDTO>>> getAllRentals() {
        List<RentalDTO> rentals = rentalService.findAllRentals();

        rentals.forEach(rental -> rental.setPicture(baseUrl + rental.getPicture()));

        Map<String, List<RentalDTO>> response = new HashMap<>();
        response.put("rentals", rentals);

        return ResponseEntity.ok(response);
    }

    /**
     * Méthode pour obtenir un rental par son id
     * 
     * @param id
     * @return un statut réponse 200 et le rental trouvé
     */
    @GetMapping("rentals/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        RentalDTO rental = rentalService.findRentalById(id);

        // Set the full URL for the picture
        rental.setPicture(baseUrl + rental.getPicture());

        return ResponseEntity.ok(rental);
    }

    /**
     * Méthode de création d'un rental en base de donnée en vérifiant que la requête
     * comporte bien une picture, et la sauvegarde dans l'API
     * 
     * @param token
     * @param name
     * @param surface
     * @param price
     * @param description
     * @param picture
     * @return un statut réponse 201
     */
    @PostMapping("rentals")
    public ResponseEntity<?> createRental(@RequestHeader("Authorization") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surface", required = false) Float surface,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {
        // Check for missing parameters
        if (token == null || token.isBlank() ||
                name == null || name.isBlank() ||
                surface == null ||
                price == null ||
                description == null || description.isBlank() ||
                picture == null || picture.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }

        try {
            Path uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
                    "static", "pictures");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = Paths.get(picture.getOriginalFilename()).getFileName().toString();

            Path filePath = uploadDir.resolve(fileName);
            picture.transferTo(filePath.toFile());

            UserDTO userDto = userService.findUserByToken(token);
            Long ownerId = userDto.getId();

            RentalDTO rentalDto = new RentalDTO();
            rentalDto.setName(name);
            rentalDto.setSurface(surface);
            rentalDto.setPrice(price);
            rentalDto.setDescription(description);
            rentalDto.setPicture("/pictures/" + fileName);
            rentalDto.setCreated_at(LocalDate.now().toString());

            rentalService.saveRental(rentalDto, ownerId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Rental created successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving picture");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating rental");
        }
    }

    /**
     * Méthode de modification d'un rental (name, surface, price ou description),
     * après avoir vérifié que la requête provient bien du possesseur du rental
     * 
     * @param token
     * @param id
     * @param name
     * @param surface
     * @param price
     * @param description
     * @return un statut réponse 200
     */
    @PutMapping("rentals/{id}")
    public ResponseEntity<String> updateRental(@RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") float surface,
            @RequestParam("price") double price,
            @RequestParam("description") String description) {
        try {
            RentalDTO existingRental = rentalService.findRentalById(id);
            if (existingRental == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found");
            }

            UserDTO userDto = userService.findUserByToken(token);
            Long ownerId = userDto.getId();

            if (existingRental.getOwner_id() != ownerId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this rental");
            }

            RentalDTO rental = rentalService.findRentalById(id);
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rentalService.updateRental(rental);

            return ResponseEntity.ok("Rental updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating rental");
        }
    }

}
