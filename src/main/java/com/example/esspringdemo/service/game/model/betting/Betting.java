package com.example.esspringdemo.service.game.model.betting;

import com.example.esspringdemo.service.game.model.DaoArgs;

public class Betting extends DaoArgs {
    private String proxyName;
    public String getProxyName() {
        return proxyName;
    }
    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }
}
