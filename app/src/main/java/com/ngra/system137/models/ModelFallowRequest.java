package com.ngra.system137.models;

public class ModelFallowRequest {

    private Integer Code;
    private Integer Type;
    private String Subject;
    private String Description;
    private int Statue;

    public ModelFallowRequest(Integer code, Integer type, String subject, String description, int statue) {
        Code = code;
        Type = type;
        Subject = subject;
        Description = description;
        Statue = statue;
    }


    public Integer getCode() {
        return Code;
    }

    public Integer getType() {
        return Type;
    }

    public String getSubject() {
        return Subject;
    }

    public String getDescription() {
        return Description;
    }

    public int getStatue() {
        return Statue;
    }
}
