package com.example.esspringdemo.service.game.dto.commission;

public enum  ProxyUserType {
    MEMBER(0),PROXY(1);
    private int value;

    ProxyUserType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
