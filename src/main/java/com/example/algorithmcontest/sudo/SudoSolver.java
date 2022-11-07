package com.example.algorithmcontest.sudo;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数独求解类
 */
public class SudoSolver {
    static int        THRESHOLD = 9;
    static List<Sudo> sudoList;

    /**
     * 回溯求解数独,获得一个有效解就返回
     *
     * @param board
     * @return
     */
    public static boolean solveSudoGetOne(char[][] board) {
        return backtrackGetOne(board, 0, 0);
    }

    /**
     * 验证数独是否有唯一解
     *
     * @param board
     * @return
     */
    public static boolean hasUniqueAnswer(char[][] board) {
        List<Sudo> sudoList = solveSudoGetAll(board);
        boolean res = !CollectionUtils.isEmpty(sudoList) && sudoList.size() == 1;
        System.out.println(String.format("该数独是否有唯一解：%s",  res));
        return res;
    }

    /**
     * 回溯求解数独,获得所有有效解个数才返回
     *
     * @param board
     * @return
     */
    public static List<Sudo> solveSudoGetAll(char[][] board) {
        sudoList = new ArrayList<>();
        backtrackGetAll(board, 0, 0);
        return sudoList;
    }

    /**
     * 回溯求解数独
     *
     * @param board
     * @param row
     * @param col
     * @return
     */
    static boolean backtrackGetOne(char[][] board, int row, int col) {
        if (col == THRESHOLD) {
            // 穷举到最后一列的话就换到下一行重新开始。
            return backtrackGetOne(board, row + 1, 0);
        }
        if (row == THRESHOLD) {
            // 找到一个可行解，触发 base case
            System.out.println("回溯求解数独结果如下:");
            System.out.println(new Sudo(board));
            return true;
        }

        if (board[row][col] != '*') {
            // 如果有预设数字，不用我们穷举
            return backtrackGetOne(board, row, col + 1);
        }
        //穷举所有选择
        for (char ch = '1'; ch <= '9'; ch++) {
            // 判断 board[i][j] 是否可以填入 n,如果遇到不合法的数字，就跳过
            if (!isValid(board, row, col, ch)) {
                continue;
            }
            //做出选择
            board[row][col] = ch;
            // 回溯，如果找到一个可行解，立即结束
            if (backtrackGetOne(board, row, col + 1)) {
                return true;
            }
            //撤销选择
            board[row][col] = '*';
        }
        // 穷举完 1~9，依然没有找到可行解，此路不通
        return false;
    }

    static void backtrackGetAll(char[][] board, int row, int col) {
        //为防止计算太久，答案超过1了，说明没有唯一解，跳出
        if(sudoList.size()>1){
            return;
        }
        if (col == THRESHOLD) {
            // 穷举到最后一列的话就换到下一行重新开始。
            backtrackGetAll(board, row + 1, 0);
            return;
        }
        if (row == THRESHOLD) {
            // 找到一个可行解，触发 base case
            System.out.println("回溯求解数独结果如下:");
            Sudo sudo = new Sudo(board);
            System.out.println(sudo);
            sudoList.add(sudo);
            return;
        }

        if (board[row][col] != '*') {
            // 如果有预设数字，不用我们穷举
            backtrackGetAll(board, row, col + 1);
            return;
        }
        //穷举所有选择
        for (char ch = '1'; ch <= '9'; ch++) {
            // 判断 board[i][j] 是否可以填入 n,如果遇到不合法的数字，就跳过
            if (!isValid(board, row, col, ch)) {
                continue;
            }
            //做出选择
            board[row][col] = ch;
            // 回溯
            backtrackGetAll(board, row, col + 1);
            //撤销选择
            board[row][col] = '*';
        }
        // 穷举完 1~9，依然没有找到可行解
    }

    /**
     * 判断 board[i][j] 是否可以填入 n
     *
     * @param board
     * @param row
     * @param col
     * @param n
     * @return
     */
    static boolean isValid(char[][] board, int row, int col, char n) {
        for (int i = 0; i < 9; i++) {
            // 判断行是否存在重复
            if (board[row][i] == n) {
                return false;
            }
            // 判断列是否存在重复
            if (board[i][col] == n) {
                return false;
            }
            // 判断 3 x 3 方框是否存在重复
            if (board[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] == n) {
                return false;
            }
        }
        return true;
    }

}
