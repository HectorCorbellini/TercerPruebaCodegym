# Preguntas - Application Overview

## Introduction
"Preguntas" is an interactive web-based question and answer game developed using Java Servlets and JSP. The application takes users through a narrative adventure where their choices determine the progression of the story. This document provides an in-depth overview of the application's architecture, functionality, and implementation details.

## Application Concept
The game presents users with a series of questions about an encounter with a UFO and aliens. Users can respond with either "Yes" or "No" to each question, with their answers determining how the story unfolds. The game follows a linear path if the user answers "Yes" to all questions, but will end prematurely with an "expulsion message" if the user answers "No" at any point.

## Technical Architecture

### MVC Pattern
The application follows the Model-View-Controller (MVC) design pattern:
- **Model**: Represented by the `Question` class and `GameService` class
- **View**: Implemented using JSP files (question.jsp and error.jsp)
- **Controller**: Handled by the `QuestionServlet` class

### Key Components

#### Question Class
The `Question` class represents a single question in the game. Each question has:
- Text content to display to the user
- A reference to the next question in the sequence
- An "expulsion message" that displays if the user answers "No"

#### GameService Class
The `GameService` class manages the game logic and question flow:
- Initializes the sequence of questions
- Provides methods to retrieve questions
- Validates user answers and determines the next steps
- Implements a singleton pattern to ensure consistent game state

#### QuestionServlet Class
The `QuestionServlet` class handles HTTP requests and responses:
- Processes GET requests to serve the first question
- Processes POST requests to validate answers and determine the next question
- Manages session attributes to maintain game state
- Implements logging for debugging purposes

#### JSP Files
- **question.jsp**: Displays the current question and form for user input
- **error.jsp**: Displays the expulsion message when a user answers "No"

### Data Flow
1. User accesses the application at the root URL
2. `QuestionServlet.doGet()` serves the first question via question.jsp
3. User selects "Yes" or "No" and submits the form
4. `QuestionServlet.doPost()` processes the answer:
   - If "Yes", the next question is displayed
   - If "No", the user is redirected to error.jsp with an expulsion message
5. The process repeats until the user completes the story or answers "No"

## Implementation Details

### Logging
The application implements logging using Java's built-in logging framework. Log entries are written to a file in the system's temporary directory, providing a record of user interactions and system events for debugging purposes.

### URL Mapping
The servlet is mapped to the root path (`/`) of the application context, making it accessible at `http://localhost:8080/preguntas/`. This provides a clean and intuitive URL structure for users.

### Form Handling
The application uses HTML forms with POST requests to submit user answers. Hidden form fields are used to track the current question number, ensuring proper progression through the story.

### Styling
Basic CSS styling is applied to enhance the user interface, with different styles for the question and error pages.

## Future Enhancements
Potential improvements for the application include:
- Adding more branching paths based on user choices
- Implementing a scoring system
- Adding multimedia elements (images, sounds) to enhance the storytelling
- Creating a more sophisticated UI with animations and transitions
- Implementing user accounts to save progress

## Conclusion
Preguntas demonstrates the implementation of a simple web application using Java Servlets and JSP. While the game itself is straightforward, the architecture showcases important concepts in web development, including request handling, session management, and the MVC pattern.
