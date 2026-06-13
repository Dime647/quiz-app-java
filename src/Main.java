import java.util.*;

abstract class Question {
    private String questionText;
    private int marks;

    Question(String questionText, int marks) {
        this.questionText = questionText;
        this.marks = marks;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getMarks() {
        return marks;
    }

    public abstract boolean checkAnswer(String answer);
    public abstract void displayOptions();
}


class MCQQuestion extends Question {
    private int correctIndex;
    private List<String> options;

    MCQQuestion(String questionText, int marks, List<String> options, int correctIndex) {
        super(questionText, marks);
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public void displayOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        return Integer.parseInt(answer) - 1 == correctIndex;
    }
}


class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    TrueFalseQuestion(String questionText, int marks, boolean correctAnswer) {
        super(questionText, marks);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equals("1") == correctAnswer;  // 1=true, 0=false
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }
    @Override
    public void displayOptions(){
        System.out.println("0. False");
        System.out.println("1. True");
    }
}

class Player {
    private String name;
    private int score;

    Player(String name){
        this.name=name;
        this.score=0;
    }

    public String getName() {
        return name;
    }
    public int getScore(){
        return score;
    }
    void updateScore(int points){
        score+= points;
    }
}

class Quiz {
    private List<Question> questions;
    private Player player;

    Quiz(List<Question> questions, Player player) {
        this.questions = questions;
        this.player = player;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("***Welcome to the Quiz***");
        System.out.println("1.Press One to start the quiz!");
        System.out.println("2.Press two to exit the quiz!");
        String choice = sc.nextLine();

        if(choice.equals("1")){
            runQuiz(sc);
        }else System.out.println("Goodbye!");


    }

    void runQuiz(Scanner sc) {

        for (Question q : questions) {
            System.out.println(q.getQuestionText());
            q.displayOptions();

            String answer = sc.nextLine();

            if (q.checkAnswer((answer))) {
                System.out.println("Your Answer is correct!" + q.getMarks());
                player.updateScore(q.getMarks());
            } else {
                System.out.println("Your Answer is Incorrect!");
            }
            System.out.println();
        }
        System.out.println("Quiz is now over!" + "Player Name:" + player.getName() + "Your Score:" + player.getScore());

    }
}

public class Main{
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new MCQQuestion("What is the Capital of India?", 10, Arrays.asList("Mumbai", "New Delhi", "Paris", "Kolkata"),1));
        questions.add(new TrueFalseQuestion(("Is Java a OOP language?"), 5, true));
        questions.add(new MCQQuestion("What is 6+7?", 10, Arrays.asList("25", "76", "67", "13"), 3));

        Player player = new Player("Atharva");
        Quiz quiz = new Quiz(questions, player);
        quiz.start();

    }
}