package org.juego;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    public String validateAnswer(String answer, int questionNumber) {
        logger.info("Validating answer '{}' for question number {}", answer, questionNumber);
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
                logger.error("Invalid question number {}", questionNumber);
                return "Invalid question";
        }
    }

    public Question getFirstQuestion() {
        logger.info("Fetching the first question");
        return new Question("Bienvenid@: Desea iniciar el juego?", 1);
    }

    public Question getNextQuestion(String answer, int questionNumber) {
        logger.info("Getting next question for answer '{}' to question number {}", answer, questionNumber);
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
                logger.error("No next question available for question number {}", questionNumber);
                return null;
        }
    }
}
