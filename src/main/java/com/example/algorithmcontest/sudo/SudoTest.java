package com.example.algorithmcontest.sudo;

import java.util.Scanner;

public class SudoTest {
    /**
     * 测试
     * 回溯解数独：
     * 时间复杂度：最坏情况，是O(9^M)，其中 M 是棋盘中空着的格⼦数量
     * 空间复杂度：O(n)
     *
     * @param args
     */
    public static void main(String[] args) {
        SudoGenerator generator = new SudoGenerator();
        int input = getInput();
        Sudo sudo = generator.genSudo(input);
        SudoSolver.hasUniqueAnswer(sudo.getBoard());
    }

    private static int getInput() {
        System.out.println("请输入数独非0数字个数:");
        while (true) {
            Scanner scan = new Scanner(System.in);
            int nonZeroNum = scan.nextInt();
            if (nonZeroNum > 0 && nonZeroNum < 81) {
                scan.close();
                return nonZeroNum;
            } else {
                System.out.println("输入数字需在[1,80]区间内");
            }
        }

    }
}
