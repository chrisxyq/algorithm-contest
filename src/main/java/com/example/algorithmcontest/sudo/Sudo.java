package com.example.algorithmcontest.sudo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sudo {
    /**
     * 9*9棋盘
     */
    private char[][] board = new char[9][9];


    /**
     * 打印数独
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------").append(System.getProperty("line.separator"));
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] > 0)
                    sb.append(board[i][j]).append(" ");
                else
                    sb.append("* ");
            }
            sb.append(System.getProperty("line.separator"));
        }
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
