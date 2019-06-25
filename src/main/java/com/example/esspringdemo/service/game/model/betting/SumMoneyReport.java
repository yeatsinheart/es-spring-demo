package com.example.esspringdemo.service.game.model.betting;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SumMoneyReport {

    /**
     * syncTime : 1561426931217
     * game : 15
     * orderId : MW201906250942DVM699
     * playerAmount : 0
     * merUsername : MrBallDev000005ad
     * channel : 23
     * prize : 6850000
     * playerWin : 0
     * playerPrize : 0
     * payoutTime : 1561426498000
     * betWays : 0
     * gameName : MW捕鱼
     * gamePlatformId : 23004
     * effectiveAmount : 4550000
     * roundId : 5241000416cl
     * win : 2300000
     * merOrderId : 5241000416cl
     * waterFeeReal : 0
     * amount : 4550000
     * companyWin : 0
     * totalWin : -2300000
     * waterFee : 0
     * validAmount : 4550000
     * createTime : 1561426498000
     * channelName : MW游戏
     * gameCode : 1051
     * status : 1
     */

    private Byte betWays;
    private Byte status;

    private BigDecimal playerAmount;
    private BigDecimal prize;
    private BigDecimal playerWin;
    private BigDecimal playerPrize;
    private BigDecimal effectiveAmount;
    private BigDecimal win;
    private BigDecimal waterFeeReal;
    private BigDecimal amount;
    private BigDecimal companyWin;
    private BigDecimal totalWin;
    private BigDecimal waterFee;
    private BigDecimal validAmount;


    private Long syncTime;
    private Long game;
    private Long channel;
    private Long payoutTime;
    private Long createTime;
    private Long gamePlatformId;


    private String orderId;
    private String channelName;
    private String gameCode;
    private String merOrderId;
    private String roundId;
    private String gameName;
    private String merUsername;
    private String betDetails;
}
