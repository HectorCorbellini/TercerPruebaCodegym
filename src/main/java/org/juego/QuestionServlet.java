package org.juego;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    private GameService gameService = new GameService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Serve the first question instead of index.jsp
        Question question = gameService.getFirstQuestion();
        request.setAttribute("questionText", question.getQuestionText());
        request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String answer = request.getParameter("answer");
        int questionNumber = Integer.parseInt(request.getParameter("questionNumber"));

        String mensajeDeExpulsion = gameService.validateAnswer(answer, questionNumber);

        if (mensajeDeExpulsion != null) {
            // If the answer leads to an expulsion, show the error page and stop processing
            showErrorPage(mensajeDeExpulsion, request, response);
        } else {
            // Otherwise, process the next question logic
            Question nextQuestion = gameService.getNextQuestion(answer, questionNumber);
            if (nextQuestion == null) {
                showErrorPage("Invalid question.", request, response);
            } else {
                showNextQuestion(nextQuestion, request, response);
            }
        }
    }

    private void showNextQuestion(Question question, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("questionText", question.getQuestionText());
        request.setAttribute("nextQuestionNumber", question.getNextQuestionNumber());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }

    private void showErrorPage(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the error page, JSP will handle the response lifecycle
        request.setAttribute("message", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}