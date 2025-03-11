import com.juego.GameService;
import com.juego.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        // Get the singleton instance before each test
        gameService = GameService.getInstance();
    }

    @Test
    void testValidateAnswerCorrectAnswer() {
        // Test with a correct answer for question 1
        String result = gameService.validateAnswer("Yes", 1);
        assertNull(result, "Correct answer should not trigger expulsion.");
    }

    @Test
    void testValidateAnswerWrongAnswer() {
        // Test with a wrong answer for question 1
        String result = gameService.validateAnswer("No", 1);
        assertEquals("Good bye! Never come back", result, "Wrong answer should trigger expulsion.");
    }

    @Test
    void testValidateAnswerInvalidQuestion() {
        // Test with an invalid question number
        String result = gameService.validateAnswer("Yes", 999);
        assertEquals("Invalid question", result, "Invalid question number should return 'Invalid question'.");
    }

    @Test
    void testGetFirstQuestion() {
        // Test the first question retrieval
        Question firstQuestion = gameService.getFirstQuestion();
        assertNotNull(firstQuestion, "First question should not be null.");
        assertEquals("Welcome! Want to start the game?", firstQuestion.getQuestionText(), "First question text is incorrect.");
        assertEquals(1, firstQuestion.getNextQuestionNumber(), "First question number is incorrect.");
    }

    @Test
    void testGetNextQuestionValidAnswer() {
        // Test getting the next question after a correct answer to question 1
        Question nextQuestion = gameService.getNextQuestion("Yes", 1);
        assertNotNull(nextQuestion, "Next question should not be null.");
        assertEquals("You lost your memory: Accept the UFO challenge?", nextQuestion.getQuestionText(), "Next question text is incorrect.");
        assertEquals(2, nextQuestion.getNextQuestionNumber(), "Next question number is incorrect.");
    }

    @Test
    void testGetNextQuestionInvalidQuestion() {
        // Test getting the next question with an invalid question number
        Question nextQuestion = gameService.getNextQuestion("Yes", 999);
        assertNull(nextQuestion, "Next question for invalid question number should be null.");
    }

    @Test
    void testSingletonInstance() {
        // Test that the singleton instance is the same across calls
        GameService anotherInstance = GameService.getInstance();
        assertSame(gameService, anotherInstance, "Both instances should be the same.");
    }
}
