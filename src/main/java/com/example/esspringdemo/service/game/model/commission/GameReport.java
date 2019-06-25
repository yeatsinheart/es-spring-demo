package com.example.esspringdemo.service.game.model.commission;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GameReport {
    private Long platformId;
    private BigDecimal waterFee;
    private BigDecimal win;
    private BigDecimal totalWin;
}
