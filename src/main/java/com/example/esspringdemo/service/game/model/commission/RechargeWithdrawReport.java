package com.example.esspringdemo.service.game.model.commission;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RechargeWithdrawReport {

    /**
     * withdrawCommFee : {"value":10000}
     * rechargeAmount : {"value":649870000}
     * rechargeCommFee : {"value":800000}
     * withdrawAmount : {"value":1000000}
     */

    private BigDecimal withdrawCommFee;
    private BigDecimal rechargeAmount;
    private BigDecimal rechargeCommFee;
    private BigDecimal withdrawAmount;

}
