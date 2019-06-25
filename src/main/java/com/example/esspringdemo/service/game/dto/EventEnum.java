package com.example.esspringdemo.service.game.dto;

public enum EventEnum {
    BETTING(1003),
    COMMISSION(1009)
    ;
    private int value;

    EventEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
