package com.example.esspringdemo.service.game.model.commission;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class BettingReport {
    private Long userId;
    private BigDecimal sumEffectiveAmount;
}
