package com.juego;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private GameService gameService = new GameService();

    @Test
    public void testValidateAnswer_FirstQuestion_Yes() {
        String result = gameService.validateAnswer("Yes", 1);
        assertNull(result, "Answer 'Yes' for question 1 should not result in expulsion.");
    }

    @Test
    public void testValidateAnswer_FirstQuestion_No() {
        String result = gameService.validateAnswer("No", 1);
        assertEquals("Good bye! Never come back", result, "Answer 'No' for question 1 should expel the user.");
    }

    @Test
    public void testGetFirstQuestion() {
        Question question = gameService.getFirstQuestion();
        assertEquals("Bienvenid@: Desea iniciar el juego?", question.getQuestionText());
        assertEquals(1, question.getNextQuestionNumber());
    }

    @Test
    public void testGetNextQuestion_SecondQuestion() {
        Question question = gameService.getNextQuestion("Yes", 1);
        assertNotNull(question);
        assertEquals("You lost your memory: Accept the UFO challenge?", question.getQuestionText());
        assertEquals(2, question.getNextQuestionNumber());
    }

    @Test
    public void testGetNextQuestion_InvalidQuestionNumber() {
        Question question = gameService.getNextQuestion("Yes", 999);
        assertNull(question, "Invalid question number should return null.");
    }
}