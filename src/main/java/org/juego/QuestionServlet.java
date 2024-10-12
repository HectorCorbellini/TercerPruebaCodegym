package org.juego;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServlet.class);

    private GameService gameService = new GameService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Serve the first question instead of index.jsp
            Question question = gameService.getFirstQuestion();
            logger.info("Serving the first question: {}", question.getQuestionText());

            request.setAttribute("questionText", question.getQuestionText());
            request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
            request.getRequestDispatcher("/question.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error in doGet: ", e);
            showErrorPage("Error fetching the first question.", request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String answer = request.getParameter("answer");
            String questionNumStr = request.getParameter("questionNumber");

            if (answer == null || questionNumStr == null) {
                logger.error("Missing parameters: answer or questionNumber");
                showErrorPage("Missing parameters: answer or questionNumber", request, response);
                return;
            }

            int questionNumber = Integer.parseInt(questionNumStr);
            logger.info("Processing answer '{}' for question {}", answer, questionNumber);

            String mensajeDeExpulsion = gameService.validateAnswer(answer, questionNumber);

            if (mensajeDeExpulsion != null) {
                logger.warn("Invalid answer: {} for question number {}", answer, questionNumber);
                showErrorPage(mensajeDeExpulsion, request, response);
            } else {
                // Process the next question logic
                Question nextQuestion = gameService.getNextQuestion(answer, questionNumber);
                if (nextQuestion == null) {
                    logger.error("No next question found for question number {}", questionNumber);
                    showErrorPage("Invalid question.", request, response);
                } else {
                    showNextQuestion(nextQuestion, request, response);
                }
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid question number format", e);
            showErrorPage("Invalid question number format.", request, response);
        } catch (Exception e) {
            logger.error("Error processing doPost", e);
            showErrorPage("Unexpected error.", request, response);
        }
    }

    private void showNextQuestion(Question question, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("questionText", question.getQuestionText());
            request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
            logger.info("Forwarding to next question: {}", question.getQuestionText());
            request.getRequestDispatcher("/question.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error showing next question", e);
            showErrorPage("Error displaying the next question.", request, response);
        }
    }

    private void showErrorPage(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Displaying error page: {}", message);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
