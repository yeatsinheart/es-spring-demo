package com.example.esspringdemo.service.game.model;


public class DaoArgs {
    public static String INDEX = "report-*";
    private Long startTime;
    private Long endTime;
    private String project="betball";
    // 默认为 2
    private Byte isFake = 2;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Byte getIsFake() {
        return isFake;
    }

    public void setIsFake(Byte isFake) {
        this.isFake = isFake;
    }
}
