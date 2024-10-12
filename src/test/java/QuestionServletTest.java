package com.juego;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionServletTest extends Mockito {

    private QuestionServlet questionServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        questionServlet = new QuestionServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        gameService = mock(GameService.class);
        questionServlet.setGameService(gameService);
    }

    @Test
    public void testDoGet_FirstQuestion() throws Exception {
        Question mockQuestion = new Question("Test Question", 1);
        when(gameService.getFirstQuestion()).thenReturn(mockQuestion);
        when(request.getRequestDispatcher("/question.jsp")).thenReturn(requestDispatcher);

        questionServlet.doGet(request, response);

        verify(request).setAttribute("questionText", "Test Question");
        verify(request).setAttribute("nextQuestionNumber", 1);
        verify(request).getRequestDispatcher("/question.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_ValidAnswer() throws Exception {
        when(request.getParameter("answer")).thenReturn("Yes");
        when(request.getParameter("questionNumber")).thenReturn("1");
        Question nextQuestion = new Question("Next question text", 2);
        when(gameService.getNextQuestion("Yes", 1)).thenReturn(nextQuestion);
        when(request.getRequestDispatcher("/question.jsp")).thenReturn(requestDispatcher);

        questionServlet.doPost(request, response);

        verify(request).setAttribute("questionText", "Next question text");
        verify(request).setAttribute("nextQuestionNumber", 2);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_InvalidAnswer() throws Exception {
        when(request.getParameter("answer")).thenReturn("No");
        when(request.getParameter("questionNumber")).thenReturn("1");
        when(gameService.validateAnswer("No", 1)).thenReturn("Good bye! Never come back");
        when(request.getRequestDispatcher("/error.jsp")).thenReturn(requestDispatcher);

        questionServlet.doPost(request, response);

        verify(request).setAttribute("message", "Good bye! Never come back");
        verify(requestDispatcher).forward(request, response);
    }
}