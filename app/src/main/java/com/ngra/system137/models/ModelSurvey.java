package com.ngra.system137.models;

public class ModelSurvey {

    private String Subject;
    private String Description;
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String Answer4;

    public ModelSurvey(String subject, String description, String answer1, String answer2, String answer3, String answer4) {
        Subject = subject;
        Description = description;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
    }


    public String getSubject() {
        return Subject;
    }

    public String getDescription() {
        return Description;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }
}
