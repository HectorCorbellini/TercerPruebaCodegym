import org.juego.GameService;
import org.juego.Question;
import org.juego.QuestionServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServletTest {

    @InjectMocks
    private QuestionServlet questionServlet;

    @Mock
    private GameService gameService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setup() {
        // Initialize the GameService mock
        when(gameService.getFirstQuestion())
                .thenReturn(new Question("Bienvenid@: Desea iniciar el juego?", 1));
    }

    @Test
    public void testDoGet() throws Exception {
        // Set up the request and response
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/question.jsp")).thenReturn(dispatcher);

        // Call the doGet method
        questionServlet.doGet(request, response);

        // Verify that the request attributes were set correctly
        verify(request).setAttribute("questionText", "Bienvenid@: Desea iniciar el juego?");
        verify(request).setAttribute("nextQuestionNumber", 1);

        // Verify that the forward method is called with the correct JSP
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_InvalidAnswer() throws Exception {
        // Mock the request and response
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/error.jsp")).thenReturn(dispatcher);

        // Mock parameters
        when(request.getParameter("answer")).thenReturn("Invalid");
        when(request.getParameter("questionNumber")).thenReturn("1");

        // Mock gameService and its behavior
        when(gameService.validateAnswer("Invalid", 1)).thenReturn("Invalid answer");

        // Call the doPost method
        questionServlet.doPost(request, response);

        // Verify that the error page is dispatched
        verify(request).getRequestDispatcher("/error.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_ValidAnswer() throws Exception {
        // Set up the request and response
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/question.jsp")).thenReturn(dispatcher);

        when(request.getParameter("answer")).thenReturn("Yes");
        when(request.getParameter("questionNumber")).thenReturn("1");
        when(gameService.validateAnswer("Yes", 1)).thenReturn(null);
        when(gameService.getNextQuestion("Yes", 1)).thenReturn(new Question("You lost your memory: Accept the UFO challenge?", 2));

        // Call the doPost method
        questionServlet.doPost(request, response);

        // Verify that the next question was shown and the correct page was forwarded to
        verify(request).setAttribute("questionText", "You lost your memory: Accept the UFO challenge?");
        verify(request).setAttribute("nextQuestionNumber", 2);
        verify(dispatcher).forward(request, response);
    }
}
