# Preguntas - A Question and Answer Game

## Overview
Preguntas is a simple web-based question and answer game implemented using Java Servlets and JSP. The application presents a series of questions to the user, where the answers determine the progression of the story. It is designed to be interactive and engaging, allowing users to navigate through a narrative based on their choices.

## Features
- Interactive question-and-answer gameplay
- Simple storyline involving an encounter with a UFO
- Logging of user interactions for debugging purposes
- Built using Java Servlet technology and JSP

## Technologies Used
- Java 21
- Maven for dependency management
- Apache Tomcat for serving the application
- JUnit and Mockito for testing (tests currently skipped during build)

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Apache Maven 3.8.7 or higher
- An IDE (e.g., IntelliJ IDEA, Eclipse) for development

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd TercerPruebaCodegym-
   ```

2. Build the project:
   ```bash
   mvn clean package -DskipTests
   ```

3. Run the application:
   ```bash
   mvn tomcat7:run
   ```

### Accessing the Application
Once the application is running, open your web browser and navigate to:
```
http://localhost:8080/preguntas/
```

### Game Instructions
- The game starts with a welcome question asking if you want to play.
- Answer "Yes" to continue through the storyline.
- Answer "No" to end the game with an expulsion message.
- You can restart the game at any time.

## Logging
The application logs interactions to a file located in the system's temporary directory, which can be useful for debugging.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
