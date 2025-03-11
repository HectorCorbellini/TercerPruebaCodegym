package com.juego;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebServlet("/")
public class QuestionServlet extends HttpServlet {

    // Use the singleton instance of GameService
    private GameService gameService = GameService.getInstance();
    private static final Logger LOGGER = Logger.getLogger(QuestionServlet.class.getName());

    // Configure the logger to log into a file
    static {
        try {
            // Log file location in the system's temporary directory
            String logFilePath = System.getProperty("java.io.tmpdir") + "/question_servlet.log";
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Logger initialized. Log file: " + logFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing GET request to serve the first question.");

        // Serve the first question instead of index.jsp
        Question question = gameService.getFirstQuestion();
        request.setAttribute("questionText", question.getQuestionText());
        request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
        LOGGER.info("Serving question: " + question.getQuestionText());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String answer = request.getParameter("answer");
        int questionNumber = Integer.parseInt(request.getParameter("questionNumber"));

        LOGGER.info("Processing POST request with answer: " + answer + " for question number: " + questionNumber);

        String expulsionMessage = gameService.validateAnswer(answer, questionNumber);

        if (expulsionMessage != null) {
            LOGGER.warning("Expulsion triggered: " + expulsionMessage);
            showErrorPage(expulsionMessage, request, response);
        } else {
            Question nextQuestion = gameService.getNextQuestion(answer, questionNumber);
            if (nextQuestion == null) {
                LOGGER.severe("Invalid question encountered after question number: " + questionNumber);
                showErrorPage("Invalid question.", request, response);
            } else {
                LOGGER.info("Proceeding to next question: " + nextQuestion.getQuestionText());
                showNextQuestion(nextQuestion, request, response);
            }
        }
    }

    private void showNextQuestion(Question question, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Displaying next question: " + question.getQuestionText());
        request.setAttribute("questionText", question.getQuestionText());
        request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }

    private void showErrorPage(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.warning("Displaying error page with message: " + message);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}

/*
package com.juego;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebServlet("/")
public class QuestionServlet extends HttpServlet {

    private GameService gameService = new GameService();
    private static final Logger LOGGER = Logger.getLogger(QuestionServlet.class.getName());

    // Configure the logger to log into a file
    static {
        try {
            // Log file location in the system's temporary directory
            String logFilePath = System.getProperty("java.io.tmpdir") + "/question_servlet.log";
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Logger initialized. Log file: " + logFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing GET request to serve the first question.");

        // Serve the first question instead of index.jsp
        Question question = gameService.getFirstQuestion();
        request.setAttribute("questionText", question.getQuestionText());
        request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
        LOGGER.info("Serving question: " + question.getQuestionText());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String answer = request.getParameter("answer");
        int questionNumber = Integer.parseInt(request.getParameter("questionNumber"));

        LOGGER.info("Processing POST request with answer: " + answer + " for question number: " + questionNumber);

        String expulsionMessage = gameService.validateAnswer(answer, questionNumber);

        if (expulsionMessage != null) {
            LOGGER.warning("Expulsion triggered: " + expulsionMessage);
            showErrorPage(expulsionMessage, request, response);
        } else {
            Question nextQuestion = gameService.getNextQuestion(answer, questionNumber);
            if (nextQuestion == null) {
                LOGGER.severe("Invalid question encountered after question number: " + questionNumber);
                showErrorPage("Invalid question.", request, response);
            } else {
                LOGGER.info("Proceeding to next question: " + nextQuestion.getQuestionText());
                showNextQuestion(nextQuestion, request, response);
            }
        }
    }

    private void showNextQuestion(Question question, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Displaying next question: " + question.getQuestionText());
        request.setAttribute("questionText", question.getQuestionText());
        request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }

    private void showErrorPage(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.warning("Displaying error page with message: " + message);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
*/
