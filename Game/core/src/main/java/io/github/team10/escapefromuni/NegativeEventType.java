package io.github.team10.escapefromuni;

public enum NegativeEventType {

    THE3("THE3 EXAM",
        "True or False:\nThe self-accepting problem SA \nis semi-decidable.",
        "True",
        "False",
        true),
    SYS2("SYS2 EXAM",
        "Which of the following is a valid\nIPv4 address?",
        "1.2.3.4",
        "0.1.2.3",
        true),
    ENG1("ENG1 COURSEWORK",
        "Which is an appropriate risk\nto mention in a risk assessment?",
        "A team member dies",
        "A team member\nbecomes unavailable",
        false),
    JOB("JOB INTERVIEW",
        "You're interviewing for an intern\nposition. How many years of experience\nwould you need?",
        "3",
        "10",
        false);

    private final String title;
    private final String question_text;
    private final String left_text;
    private final String right_text;
    private final boolean correct_answer;

    NegativeEventType(String title, String question_text, String left_text, String right_text, boolean correct_answer) {
        this.title = title;
        this.question_text =  question_text;
        this.left_text = left_text;
        this.right_text = right_text;
        this.correct_answer = correct_answer;
    }

    // getters and setters
    public String getTitle() { return title; }
    public String getQuestionText() { return question_text; }
    public String getLeftText() { return left_text; }
    public String getRightText() { return right_text; }
    public boolean getCorrectAnswer() { return correct_answer; }

}
