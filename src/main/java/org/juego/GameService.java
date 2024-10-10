package org.juego;

public class GameService {

    public String validateAnswer(String answer, int questionNumber) {
        // Since the radio button values are either "Yes" or "No", we can compare directly.
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
                return "Invalid question";
        }
    }

    public Question getFirstQuestion() {
        return new Question("Bienvenid@: Desea iniciar el juego?", 1);
    }

    public Question getNextQuestion(String answer, int questionNumber) {
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
                return null;
        }
    }
}