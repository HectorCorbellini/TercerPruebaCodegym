package com.juego;

import java.util.logging.Logger;

public class GameService {

    private static final Logger LOGGER = Logger.getLogger(GameService.class.getName());

    public String validateAnswer(String answer, int questionNumber) {
        LOGGER.info("Validating answer: " + answer + " for question number: " + questionNumber);
        switch (questionNumber) {
            case 1:
                return answer.equals("No") ? "Good bye! Never come back" : null;
            case 2:
                return answer.equals("No") ? "You rejected the challenge. Defeat" : null;
            case 3:
                return answer.equals("No") ? "You didn't attend the negotiations. Defeat" : null;
            case 4:
                return answer.equals("No") ? "Your lie has been revealed. Defeat" : null;
            default:
                LOGGER.warning("Invalid question number: " + questionNumber);
                return "Invalid question";
        }
    }

    public Question getFirstQuestion() {
        LOGGER.info("Getting the first question.");
        return new Question("Welcome! Want to start the game?", 1);
    }

    public Question getNextQuestion(String answer, int questionNumber) {
        LOGGER.info("Getting next question after answering: " + answer + " for question number: " + questionNumber);
        switch (questionNumber) {
            case 1:
                return new Question("You lost your memory: Accept the UFO challenge?", 2);
            case 2:
                return new Question("You accepted the challenge: Are you going up to the captain's bridge?", 3);
            case 3:
                return new Question("You've gone up to the bridge: Will tell us who you are?", 4);
            case 4:
                return new Question("You've been returned home, victory! Start again?", 1);
            default:
                LOGGER.warning("No valid next question for question number: " + questionNumber);
                return null;
        }
    }
}
