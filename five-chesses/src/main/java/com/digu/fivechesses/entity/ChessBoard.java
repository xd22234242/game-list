package com.digu.fivechesses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChessBoard {
    int x;
    int y;
    boolean isWin;
    int isWinPlayer;
}
