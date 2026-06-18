# Quiz App (Java OOP Project)

A console-based Quiz Application built in Java as a hands-on project to learn 
Object-Oriented Programming concepts.

## Concepts Used

- **Abstraction** — `Question` is an abstract class defining a contract for all question types
- **Inheritance** — `MCQQuestion` and `TrueFalseQuestion` extend `Question`
- **Polymorphism** — `checkAnswer()` and `displayOptions()` behave differently per question type, called through a common `Question` reference
- **Encapsulation** — all fields are private, accessed via getters/setters

## Class Structure

- `Question` (abstract)
  - `MCQQuestion`
  - `TrueFalseQuestion`
- `Player`
- `Quiz`

- **Question** — stores question text and marks, declares abstract `checkAnswer()` and `displayOptions()`
- **MCQQuestion** — multiple choice question with options list and correct index
- **TrueFalseQuestion** — true/false question (0 = false, 1 = true)
- **Player** — tracks player name and score
- **Quiz** — runs the quiz loop, takes input, checks answers, updates score

## How to Run

```bash
javac Main.java
java Main
```

Follow the on-screen prompts to start the quiz, answer questions, and view your final score.

## Roadmap

- [ ] Leaderboard — store and display top scores
- [ ] FileHandler — load questions from a `questions.json` file using Gson
- [ ] Persist player scores across sessions

## Author

Atharva Danait — First-year BTech Computer Engineering student
