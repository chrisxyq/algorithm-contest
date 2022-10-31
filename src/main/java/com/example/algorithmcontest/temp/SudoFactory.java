//package com.example.algorithmcontest.temp;
//
//
//import java.util.Scanner;
//
///**
// * 入门：设计9*9规模的数独游戏的初始化算法（随机设置初盘数字数量）
// * 进阶（选答）：证明具有唯一解
// *
// * @description 数独生成和求解
// * @limit 支持从1-80的数字提示数量
// * @method 深度优先搜索/回溯法
// */
//public class SudoFactory {
//
//    /**
//     * 9*9棋盘
//     */
//    private int[][] board = new int[9][9];
//    /**
//     * 棋盘中的0的个数
//     */
//    private int     zeroNum;
//    /**
//     * 棋盘中的非0的个数
//     */
//    private int     nonZeroNum;
//
//    /**
//     * 生成数独
//     * 方法：挖洞法
//     */
//    public void genSudo() {
//        /*step1：将1-9 9个数字放在二维数组中随机位置*/
//        zeroNum = 81 - 9;
//        for (int i = 0; i < 9; ++i) {
//            board[0][i] = i + 1;
//        }
//        //首行，行内交换
//        for (int i = 0; i < 9; ++i) {
//            int col1 = getRandomEight();
//            int col2 = getRandomEight();
//            int temp = board[0][col1];
//            board[0][col1] = board[0][col2];
//            board[0][col2] = temp;
//        }
//        //任意交换元素
//        for (int i = 0; i < 9; ++i) {
//            int row = getRandomEight();
//            int col = getRandomEight();
//            int temp = board[0][i];
//            board[0][i] = board[row][col];
//            board[row][col] = temp;
//        }
//        /*step2：通过9个数字求出一个可行解*/
//        solveSudo();
//        //step3：根据题目要求，在生成的满棋盘答案中，随机挖出需要数量的洞
//        zeroNum = 81 - nonZeroNum;
//        for (int i = 0; i < zeroNum; ++i) {
//            //为随机得到的非0位置，赋值为0
//            int ta = getRandomEight();
//            int tb = getRandomEight();
//            if (board[ta][tb] != 0) {
//                board[ta][tb] = 0;
//            } else {
//                //如果是0，重试
//                i--;
//            }
//        }
//        System.out.println("数组题目生成如下:");
//        System.out.println(this);
//    }
//
//    /**
//     * 生成[0,8]的数字
//     *
//     * @return
//     */
//    private int getRandomEight() {
//        return (int) (Math.random() * 10) % 9;
//    }
//
//    /**
//     * 求解数独
//     *
//     * @return 是否有解的boolean标识
//     */
//    public boolean solveSudo() {
//        if (dfs()) {
//            System.out.println("数组求解完成");
//            System.out.println(this);
//            return true;
//        } else {
//            System.out.println("数组求解失败");
//            return false;
//        }
//    }
//
//    /**
//     * 打印数独
//     *
//     * @return
//     */
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("-----------------").append(System.getProperty("line.separator"));
//        for (int i = 0; i < 9; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                if (board[i][j] > 0)
//                    sb.append(board[i][j]).append(" ");
//                else
//                    sb.append("* ");
//            }
//            sb.append(System.getProperty("line.separator"));
//        }
//        sb.append(System.getProperty("line.separator"));
//        return sb.toString();
//    }
//
//    /**
//     * 计算某格子的可填数字个数，即不确定度
//     *
//     * @param row：格子行索引
//     * @param col：格子列的索引
//     * @param possibleArray：可取为0，不可取为1
//     * @return 不确定度
//     */
//    private int calPossibleCnt(int row, int col, int[] possibleArray) {
//        //数组初始化为0
//        for (int temp = 0; temp < 10; ++temp) {
//            possibleArray[temp] = 0;
//        }
//        //这个格子所在的列和行，存在过的元素都不可取
//        for (int i = 0; i < 9; ++i) {
//            possibleArray[board[i][col]] = 1;
//            possibleArray[board[row][i]] = 1;
//        }
//        //这个格子所在的3×3的小方格中的列和行，存在过的元素都不可取
//        int startRow = (row / 3) * 3;
//        int startCol = (col / 3) * 3;
//        for (int i = 0; i < 3; ++i) {
//            for (int j = 0; j < 3; ++j) {
//                possibleArray[board[startRow + i][startCol + j]] = 1;
//            }
//        }
//        //mark数组中，所有依旧为0的位置，即为可取的数字
//        int possibleCnt = 0;
//        for (int i = 1; i < 10; ++i) {
//            if (possibleArray[i] == 0) {
//                possibleCnt++;
//            }
//        }
//        return possibleCnt;
//    }
//
//    /**
//     * 供solve调用的深度优先搜索
//     * 回溯解数独：
//     * 时间复杂度：最坏情况，是O(9^M)，其中 M 是棋盘中空着的格⼦数量
//     * 空间复杂度：O(n)
//     *
//     * @return 是否有解的boolean标识
//     */
//    private boolean dfs() {
//        //递归结束条件：数组被填满，求解成功直接返回
//        if (zeroNum == 0) {
//            return true;
//        }
//        /*找到不确定度最小的格子*/
//        int minPossibleCnt = 10;
//        int minPossibleI = 0, minPossibleJ = 0;
//        int[] possibleArray = new int[10];
//        for (int i = 0; i < 9; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                //题给数字，不必计算不确定度
//                if (board[i][j] != 0) {
//                    continue;
//                }
//                int possibleCnt = calPossibleCnt(i, j, possibleArray);
//                //发现不确定度为0的格子，直接返回，数独无法求解
//                if (possibleCnt == 0) {
//                    return false;
//                }
//                //遍历过程中，更新不确定度最小的格子位置
//                if (possibleCnt < minPossibleCnt) {
//                    minPossibleCnt = possibleCnt;
//                    minPossibleI = i;
//                    minPossibleJ = j;
//                }
//            }
//        }
//        /*优先处理不确定度最小的格子*/
//        calPossibleCnt(minPossibleI, minPossibleJ, possibleArray);
//        //step1：找到不确定度最小的格子后，遍历所有选择
//        for (int i = 1; i < 10; ++i) {
//            //possibleArray[i]可选
//            if (possibleArray[i] == 0) {
//                //step2：做出选择，给当前不确定度最小的格子赋值
//                board[minPossibleI][minPossibleJ] = i;
//                zeroNum--;
//                dfs();
//                if (zeroNum == 0) {
//                    return true;
//                }
//                //step3：回溯:撤销选择
//                board[minPossibleI][minPossibleJ] = 0;
//                zeroNum++;
//            }
//        }
//        return true;
//    }
//
//
//    /**
//     * 测试
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        SudoFactory instance = new SudoFactory();
//        System.out.println("请输入数独非0数字个数:");
//        Scanner scan = new Scanner(System.in);
//        instance.nonZeroNum = scan.nextInt();
//        scan.close();
//        instance.genSudo();
//        instance.solveSudo();
//    }
//}
