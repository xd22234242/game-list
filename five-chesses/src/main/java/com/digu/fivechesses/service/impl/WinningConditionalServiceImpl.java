package com.digu.fivechesses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digu.fivechesses.rule.ConditionalDeterminationRule;
import com.digu.fivechesses.service.WinningConditionalService;

@Service
public class WinningConditionalServiceImpl implements WinningConditionalService{

    @Autowired
    ConditionalDeterminationRule winConditionalRule;

    @Override
    public boolean winnerConditionalScan(int[] map, int x, int y, int chessType) {
        return winConditionalRule.scan(map, x, y, chessType);
    } 
    
}
