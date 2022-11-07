package com.example.algorithmcontest;

import org.junit.Test;

import java.util.Arrays;

/**
 * 20221107题目：
 * 村⾥的每个⼈都能够到果园⾥去采摘，但规定每棵树上只能采摘1个果⼦（假设每棵树上的果⼦数量⽆
 * 限，每个⼈都能采到）。为了避免⼤家挤在⼀起产⽣踩踏事件，果园还规定在某颗果树采摘完后不允
 * 许采摘相邻（浅蓝⾊部分为⽰例）的果树，同时不能回头采已经⾛过的果树。现在假设市场上苹果的
 * 价格是4元/个，梨的价格是3元/个，桃⼦的价格是2元/个。
 */
public class PickFruit {
    /**
     * 对于m*n矩阵
     * 时间复杂度：o(m*n)
     * 空间复杂度：o(m*n*2)
     *
     * 由于题给金额[2,3,4]满足三角形的特性，最大的金额不会出现连续两次均不采摘的case
     * 因此Z字形遍历题给二维数组
     * dp[i][j][0]:表示不采摘data[i][j]时，能获得的最大金额
     *
     * @param data
     * @return
     */
    public int getMaxPrice(int[][] data) {
        int row = data.length;
        int col = data[0].length;
        int[][][] dp = new int[row][col][2];
        for (int i = 0; i < row; i++) {
            if (i % 2 == 0) {
                //行数索引为偶数，上方和左方都不存在元素才可以放置，左边不存在时，上边必也不存在
                for (int j = 0; j < col; j++) {
                    //计算dp[i][j][0]、dp[i][j][1]
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (i > 0 && j == 0) {
                        dp[i][j][0] = dp[i - 1][j][1];
                        dp[i][j][1] = dp[i - 1][j][0] + data[i][j];
                    } else {
                        dp[i][j][0] = dp[i][j - 1][1];
                        dp[i][j][1] = dp[i][j - 1][0] + data[i][j];
                    }
                }
            } else {
                //行数索引为奇数，上方和右方都不存在元素才可以放置，右边不存在时，上边必也不存在
                for (int j = col - 1; j > -1; j--) {
                    //计算dp[i][j][0]、dp[i][j][1]
                    if (i > 0 && j == col - 1) {
                        dp[i][j][0] = dp[i - 1][j][1];
                        dp[i][j][1] = dp[i - 1][j][0] + data[i][j];
                    } else {
                        dp[i][j][0] = dp[i][j + 1][1];
                        dp[i][j][1] = dp[i][j + 1][0] + data[i][j];
                    }

                }
            }

        }
        System.out.println(String.format("得到的dp数组为：%s", Arrays.deepToString(dp)));
        //计算采摘的最大金额
        int maxPrice = 0;
        if (row % 2 == 0) {
            maxPrice = Math.max(dp[row - 1][0][0], dp[row - 1][0][1]);
        } else {
            maxPrice = Math.max(dp[row - 1][col - 1][0], dp[row - 1][col - 1][1]);
        }
        System.out.println(String.format("采摘的最大金额：%s", maxPrice));
        return maxPrice;
    }

    @Test
    public void test1() {
        int[][] data = new int[][]{{0, 2, 4, 2, 4},
                {2, 4, 2, 4, 2}, {4, 2, 4, 2, 4}, {2, 4, 2, 4, 2}};
        getMaxPrice(data);
    }

    @Test
    public void test2() {
        int[][] data = new int[][]{{0, 4, 3, 2, 4},
                {3, 2, 4, 2, 4}, {3, 3, 4, 4, 2}, {4, 2, 3, 2, 3}};
        getMaxPrice(data);
    }

    @Test
    public void test3() {
        int[][] data = new int[][]{{0, 4, 3, 2, 4},
                {3, 2, 4, 2, 4}, {3, 3, 4, 4, 2}, {4, 2, 3, 2, 3}, {4, 2, 3, 2, 3}};
        getMaxPrice(data);
    }
}
