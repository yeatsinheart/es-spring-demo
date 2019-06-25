package com.example.esspringdemo.service.game.model.commission;

import com.example.esspringdemo.service.game.model.DaoArgs;
import lombok.Data;

@Data
public class BettingArgs extends DaoArgs {
    private String proxyId;
}
