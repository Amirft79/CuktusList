package com.example.cuktuslist;

public class Todolist {
    private int todoid;
    private String job;
    private String instructions;
    private boolean ischeck;

    public static final String job_name="job";
    public static final String instructions_name="instructions";
    public static final String todoid_name="id";

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public int getTodoid() {
        return todoid;
    }

    public void setTodoid(int todoid) {
        this.todoid = todoid;
    }

}
