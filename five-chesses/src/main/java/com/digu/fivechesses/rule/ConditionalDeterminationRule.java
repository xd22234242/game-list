package com.digu.fivechesses.rule;

import org.springframework.stereotype.Service;

import com.digu.fivechesses.util.MyConstant;

/*
 * 此类主要功能为条件判断
 */
@Service
public class ConditionalDeterminationRule {
    /*
     * scan
     * 
     * 此函数功能为扫描棋子周围，判断是否存在获胜的可能
     * map -- 地图
     * x，y -- 棋子坐标
     * cheerType -- 棋子类型，1为黑，2为白
     */
    public boolean scan(int[] map, int x, int y, int chessType) {
        // 垂直判断
        if (vertical(map, x, y, chessType)) {
            return true;
        }

        // 水平判断
        if (horizontal(map, x, y, chessType)) {
            return true;
        }

        // \ 判断
        if (leftUp(map, x, y, chessType)) {
            return true;
        }

        // / 判断
        if (rightUp(map, x, y, chessType)) {
            return true;
        }

        return false;
    }

    // 条件 -- 垂直判断
    public boolean vertical(int map[], int x, int y, int chessType) {
        // 相同棋子的数量
        int cheerNum = 1;

        int xCopy = x;
        int yCopy = y;

        while (yCopy > 0) {
            if (map[xCopy + --yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }

        yCopy = y;

        while (yCopy < MyConstant.CHESS_BOARD_COLUMN) {
            if (map[xCopy + ++yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }

        return false;
    }

    // 条件 -- 水平判断
    public boolean horizontal(int map[], int x, int y, int chessType) {
        // 相同棋子的数量
        int cheerNum = 1;

        int xCopy = x;
        int yCopy = y;

        while (xCopy > 0) {
            if (map[--xCopy + yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }
        
        xCopy = x;

        while (xCopy < MyConstant.CHESS_BOARD_COLUMN) {
            if (map[++xCopy + yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }

        return false;
    }

    // 条件 -- 左上 -> 右下 判断 -- \
    public boolean leftUp(int map[], int x, int y, int chessType) {
        int cheerNum = 1;

        int xCopy = x;
        int yCopy = y;

        while (xCopy > 0 && yCopy > 0) {
            if (map[--xCopy + --yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }

        xCopy = x;
        yCopy = y;

        while (xCopy < MyConstant.CHESS_BOARD_COLUMN && yCopy < MyConstant.CHESS_BOARD_COLUMN) {
            if (map[++xCopy + ++yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }

        return false;
    }

    // 条件 -- 右上 -> 左下 判断 -- /
    public boolean rightUp(int map[], int x, int y, int chessType) {
        int cheerNum = 1;

        int xCopy = x;
        int yCopy = y;

        while (xCopy < MyConstant.CHESS_BOARD_COLUMN && yCopy > 0) {
            if (map[++xCopy + --yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }

        xCopy = x;
        yCopy = y;

        while (xCopy > 0 && yCopy < MyConstant.CHESS_BOARD_COLUMN) {
            if (map[--xCopy + ++yCopy * MyConstant.CHESS_BOARD_COLUMN] == chessType) {
                ++cheerNum;
            } else {
                break;
            }
        }

        if (cheerNum >= 5) {
            return true;
        }
        
        return false;
    }
}
