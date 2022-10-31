package com.example.algorithmcontest.sudo;


import java.util.List;
import java.util.Scanner;

/**
 * 入门：设计9*9规模的数独游戏的初始化算法（随机设置初盘数字数量）
 * 进阶（选答）：证明具有唯一解
 * <p>
 * 数独的生成总体思路是挖洞法。
 * 首先在二维数组第一行随机填充1-9 9个数字，
 * 然后将这9个数字随机分布到整个二维数组中，
 * 然后使用求解数独的算法对此时的数组进行求解，得到一个完整的数独，
 * 然后按照用户输入的提示数量进行随机挖洞，得到最终的数独题目。
 *
 * @description 数独生成和求解
 * @limit 支持从1-80的数字提示数量
 * @method 深度优先搜索/回溯法
 */

public class SudoGenerator {

    /**
     * 生成数独
     * 方法：挖洞法
     */
    public Sudo genSudo(int nonZeroNum) {
        //step1：将1-9 9个数字放在二维数组中随机位置
        char[][] board = genInitSudo();

        //step2：通过9个数字求出一个可行解
        SudoSolver.solveSudoGetOne(board);


        //step3：根据题目要求，在生成的满棋盘答案中，随机挖出需要数量的洞
        makeHole(board, nonZeroNum);

        System.out.println("数组题目生成如下:");
        Sudo sudo = new Sudo(board);
        System.out.println(sudo);
        return sudo;
    }

    /**
     * step3：根据题目要求，在生成的满棋盘答案中，随机挖出需要数量的洞
     *
     * @param board
     * @param nonZeroNum
     */
    private void makeHole(char[][] board, int nonZeroNum) {
        int zeroNum = 81 - nonZeroNum;
        for (int i = 0; i < zeroNum; ++i) {
            //为随机得到的非0位置，赋值为0
            int row = getRandomEight();
            int col = getRandomEight();
            if (board[row][col] != '*') {
                board[row][col] = '*';
            } else {
                //如果是0，重试
                i--;
            }
        }
    }

    /**
     * step1：将1-9 9个数字放在二维数组中随机位置
     *
     * @return
     */
    private char[][] genInitSudo() {
        char[][] board = new char[9][9];
        for (int i = 0; i < 9; ++i) {
            //int转char：(char)('0' +1)
            board[0][i] = (char) ('0' + i + 1);
        }
        for (int i = 1; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                board[i][j] = '*';
            }
        }
        //首行，行内交换
        for (int i = 0; i < 9; ++i) {
            int col1 = getRandomEight();
            int col2 = getRandomEight();
            char temp = board[0][col1];
            board[0][col1] = board[0][col2];
            board[0][col2] = temp;
        }
        //任意交换元素
        for (int i = 0; i < 9; ++i) {
            int row = getRandomEight();
            int col = getRandomEight();
            char temp = board[0][i];
            board[0][i] = board[row][col];
            board[row][col] = temp;
        }
        return board;
    }

    /**
     * 生成[0,8]的数字
     *
     * @return
     */
    private int getRandomEight() {
        return (int) (Math.random() * 10) % 9;
    }





}
