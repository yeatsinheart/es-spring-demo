package com.example.esspringdemo.service.game.model.betting;

import java.math.BigDecimal;

public class GameReport {
    /**
     * 游戏平台名称
     */
    private String game;
    /**
     * 游戏平台PlatformId
     */
    private Long gamePlatformId;
    /**
     * 公司总输赢
     */
    private BigDecimal win;
    /**
     * 游戏总投注
     */
    private BigDecimal amount;

    public GameReport(String game, Long gamePlatformId, BigDecimal win, BigDecimal amount) {
        this.game = game;
        this.gamePlatformId = gamePlatformId;
        this.amount = amount;
        this.win = win;
    }
}
