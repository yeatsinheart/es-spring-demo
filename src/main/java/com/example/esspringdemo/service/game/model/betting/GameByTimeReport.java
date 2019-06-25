package com.example.esspringdemo.service.game.model.betting;

import java.math.BigDecimal;

public class GameByTimeReport {
    /**
     * 分时标题
     */
    private String title;
    /**
     * 游戏平台platformId
     */
    private Long platformId;
    /**
     * 游戏平台
     */
    private String game;
    /**
     * 公司总输赢
     */
    private BigDecimal totalWin;
    /**
     * 玩家总投注
     */
    private BigDecimal amount;

    public GameByTimeReport(String title, Long platformId, String game, BigDecimal totalWin, BigDecimal amount) {
        this.title = title;
        this.platformId = platformId;
        this.game = game;
        this.totalWin = totalWin;
        this.amount = amount;
    }
}
