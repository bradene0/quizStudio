import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Question {
    private String text;
    private boolean isCorrect;

    public Question(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public boolean checkAnswer(boolean answer) {
        return isCorrect == answer;
    }

    public void displayQuestion() {
        System.out.println(text);
    }
}

class MultipleChoiceQuestion extends Question {
    private List<String> choices;
    private int correctChoiceIndex;

    public MultipleChoiceQuestion(String text, int correctChoiceIndex, List<String> choices) {
        super(text, false);
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
    }

    public void displayChoices() {
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
    }

    public boolean checkAnswer(int userChoice) {
        return correctChoiceIndex == userChoice;
    }
}

class CheckboxQuestion extends Question {
    private List<Integer> correctChoices;

    public CheckboxQuestion(String text, List<Integer> correctChoices) {
        super(text, false);
        this.correctChoices = correctChoices;
    }

    public void displayChoices(List<String> choices) {
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
    }

    public boolean checkAnswer(List<Integer> answers) {
        return correctChoices.equals(answers);
    }
}

class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(String text, boolean isTrue) {
        super(text, isTrue);
    }
}

class Quiz {
    private List<Question> questions;

    public Quiz() {
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void conductQuiz() {
        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            question.displayQuestion();

            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                mcQuestion.displayChoices();

                System.out.print("Enter your choice (1, 2, 3, or 4): ");
                int userChoice = scanner.nextInt();
                boolean isCorrect = mcQuestion.checkAnswer(userChoice - 1); // Adjust for 0-based index
                if (isCorrect) {
                    score++;
                }
            } else if (question instanceof TrueFalseQuestion) {
                System.out.print("Enter your answer (true or false): ");
                String userAnswer = scanner.next();
                boolean isCorrect = question.checkAnswer(userAnswer.equalsIgnoreCase("true"));
                if (isCorrect) {
                    score++;
                }
            } else if (question instanceof CheckboxQuestion) {
                CheckboxQuestion cbQuestion = (CheckboxQuestion) question;
                cbQuestion.displayChoices(List.of("1", "2", "3", "4")); //can be modified

                System.out.print("Enter your choices (e.g., '1,3' for A and C): ");
                String userInput = scanner.next();
                String[] userChoicesStr = userInput.split(",");
                List<Integer> userChoices = new ArrayList<>();
                for (String choice : userChoicesStr) {
                    userChoices.add(Integer.parseInt(choice) - 1);
                }

                boolean isCorrect = cbQuestion.checkAnswer(userChoices);
                if (isCorrect) {
                    score++;
                }
            }

            System.out.println();
        }

        scanner.close();

        if (score >= 2) {
            System.out.println("Good Job!");
        } else {
            System.out.println("Fail.");
        }

        System.out.println("Your score: " + score + "/" + questions.size());

    }
}

public class Main {
    public static void main(String[] args) {

        TrueFalseQuestion tfQuestion = new TrueFalseQuestion(
                "Earth has no oceans. (true or false)",
                false
        );
        CheckboxQuestion cbQuestion = new CheckboxQuestion(
                "Select all even numbers (choose by typing numbers, e.g., '1,3'):",
                List.of(1, 3)
        );
        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion(
                "What color is SpongeBob?",
                1,
                List.of("Orange", "Yellow", "Blue", "Green")
        );

        Quiz quiz = new Quiz();
        quiz.addQuestion(tfQuestion);
        quiz.addQuestion(cbQuestion);
        quiz.addQuestion(mcQuestion);

        quiz.conductQuiz();
    }
}
