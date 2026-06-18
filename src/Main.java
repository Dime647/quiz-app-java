import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.FileReader;
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
        FileHandler fileHandler= new FileHandler("quiz.json");
        List<Question>questions= fileHandler.loadQuestions();

        Player player = new Player("Atharva");
        Quiz quiz = new Quiz(questions, player);
        quiz.start();
    }
}

class FileHandler{
    private String filepath;

    FileHandler(String filepath){
        this.filepath=filepath;
    }

    public List<Question> loadQuestions(){
        try{
            BufferedReader reader= new BufferedReader(new FileReader(filepath));
            StringBuilder sb= new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
            reader.close();

            String content = sb.toString();

            Gson gson = new Gson();
            JsonArray jsonArray= JsonParser.parseString(content).getAsJsonArray();
            List<Question>questions= new ArrayList<>();

            for (JsonElement element: jsonArray){
                JsonObject obj= element.getAsJsonObject();
                String type= obj.get("type").getAsString();
                String questionText= obj.get("questionText").getAsString();
                int marks= obj.get("marks").getAsInt();

                if(type.equals("MCQ")) {
                    Type listType = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> options = gson.fromJson(obj.get("options"), listType);
                    int correctIndex = obj.get("correctIndex").getAsInt();
                    questions.add(new MCQQuestion(questionText, marks, options, correctIndex));
                }else if(type.equals("TrueFalse")){
                    boolean correctAnswer = obj.get("correctAnswer").getAsBoolean();
                    questions.add(new TrueFalseQuestion(questionText, marks, correctAnswer));
                }
            }
            return questions;
        }catch(Exception e){
            System.out.println("Error:"+e.getMessage());
            return new ArrayList<>();
        }
    }
}

