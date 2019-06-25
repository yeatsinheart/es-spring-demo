package com.example.esspringdemo.service.game.model.betting;

import com.example.esspringdemo.service.game.model.DaoArgs;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ListArgs extends DaoArgs {
    private int from;
    private int page=1;
    private int size=20;
    //派彩时间
    private Long payoutStartTime;
    private Long payoutEndTime;
    // 同步时间
    private Long createStartTime;
    private Long createEndTime;
    // 投注额
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    // 公司输赢
    private BigDecimal minProfit;
    private BigDecimal maxProfit;
    // 会员账号
    private String username;
    // 所属代理
    private String proxy;
    // 注单号
    private String orderId;
    // 第三方单号
    private String merOrderId;
    // 玩法
    private Integer betWays;
    // 游戏平台
    private List<Long> gamePlatformId;
    // 注单状态
    private Byte status;

    private  int shouldMatchNum;



}
