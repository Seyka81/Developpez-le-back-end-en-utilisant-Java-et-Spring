# Backend - Chatop API

Ce projet est une API pour la gestion de locations immobilières, développée avec **Spring Boot**. Elle prend en charge la gestion des utilisateurs, des annonces, et des interactions.

## **Prérequis**

- **Java 17**
- **Maven** pas forcément obligatoire car il est souvent intégré par les IDE mais peut être utile
- **Docker Desktop** installé et configuré.

## **Installation et Exécution**

### **Étape 1 : Cloner le projet**

```bash
git clone https://github.com/Seyka81/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
cd backend
```

### **Étape 2 : Générer une clé secrète**

Avant de démarrer le projet, vous devez générer une clé secrète (pour la partie JWT).

Afin de créer la clé voici les étapes :

1. Ouvrez un terminal PowerShell.
2. Exécutez la commande suivante:

```powershell
    $bytes = New-Object byte[] 64
    [Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
    [BitConverter]::ToString($bytes).Replace("-", "")
```

3. Copiez la clé générée.
4. Ajoutez-la dans le fichier `src/main/resources/application.properties` en remplacant jwtsecret:

```properties
jwt.secret=jwtsecret
```

### **Étape 3 : Configuration de la base de données avec Docker**

1. Exécutez la commande suivante pour démarrer un conteneur MySQL avec Docker (springbootdb et springuser et secretpassword et rootpassword sont bien évidemment des valeurs par défault):

```bash
docker run -d --name springboot-mysql -e MYSQL_DATABASE=springbootdb -e MYSQL_USER=springuser -e MYSQL_PASSWORD=secretpassword -e MYSQL_ROOT_PASSWORD=rootpassword -p 3306:3306 mysql:latest
```

1. Ouvrez le fichier `src/main/resources/application.properties`.
2. Assurez-vous que les configurations MySQL correspondent aux paramètres du conteneur :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdb
spring.datasource.username=springuser
spring.datasource.password=secretpassword
```

### **Étape 5 : Démarrer le backend**

Vous pouvez utiliser plusieurs IDE pour développer et exécuter le projet. **IntelliJ IDEA** simple et efficace, mais ce tutoriel se concentre sur **Visual Studio Code**.

#### **1. Installer les extensions nécessaires**

Pour configurer un environnement de développement efficace dans VS Code, installez les extensions suivantes :

- **Maven for Java** : Maven.
- **Database Client** et **Database Client JDBC** : Accès et gestion des bases de données.
- **Extension Pack for Java** : Fournit des outils essentiels pour le développement Java.

#### **2. Étapes pour démarrer le projet**

1. **Effectuer un `clean` et un `install` avec Maven** :

   - Accédez à l'onglet **Maven** dans VS Code.
   - Exécutez la commande `clean`, puis `install` pour télécharger les dépendances et compiler le projet.

2. **Lancer le projet** :
   - Accédez à l'onglet **Java Projects** (si le projet n'apparaît pas, vous pouvez ajouter le projet) puis exécutez le projet en mode **debug** (mieux pour les développeurs).
   - Sinon, restez dans l'onglet Maven :
     - Naviguez dans la section **plugins > spring-boot**.
     - Lancer run.

### **Étape 6 : Accéder à la documentation swagger de l'API (optionnel)**

Une fois le projet lancé, vous pouvez accéder au Swagger à l'adresse suivante :

http://localhost:3001/swagger-ui/index.html
