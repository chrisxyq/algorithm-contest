package com.example.algorithmcontest;



import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 20221010题目：
 * 判断欧拉回路
 * 存在欧拉回路充要条件：当且仅当图是连通的而且每个顶点的度是偶数
 */
@Slf4j
public class EulerCircuit {

    private static List<Integer> path;

    /**
     * this cost O(|E| + |V|) time
     *
     * @param unDirectedEdges: 邻接矩阵,[1,2] 表示从1到2
     * @param n:               图的节点数目
     * @param k:               欧拉回路的起始节点
     * @return
     * @throws NotFoundException
     */
    public static List<Integer> eulerCircuitByDFS(int[][] unDirectedEdges, int n, int k) throws NotFoundException {
        if (unDirectedEdges == null || unDirectedEdges.length <= 1 || n <= 2) {
            throw new NotFoundException();
        }
        //init undirected graph,use adjacency list
        //{key:1, value:<2, 3>} represents edge from node1 to node2,node3
        Map<Integer, List<Integer>> graph = new HashMap<>();
        //making graph takes O(E)
        //iterate the adjacency matrix
        for (int i = 0; i < unDirectedEdges.length; i++) {
            int[] edge = unDirectedEdges[i];
            if (!graph.containsKey(edge[0])) {
                graph.put(edge[0], new ArrayList<Integer>());
            }
            graph.get(edge[0]).add(edge[1]);
            if (!graph.containsKey(edge[1])) {
                graph.put(edge[1], new ArrayList<Integer>());
            }
            graph.get(edge[1]).add(edge[0]);
        }
        path = new ArrayList<>();
        // takes O(V)
        dfs(graph, k, k, path);
        return path;
    }

    /**
     * 欧拉回路 dfs
     *
     * @param graph
     * @param k:          start node
     * @param origin:     the origin start node
     * @param currentPath
     * @throws NotFoundException
     */
    public static void dfs(Map<Integer, List<Integer>> graph, int k, int origin, List<Integer> currentPath)
            throws NotFoundException {
        currentPath.add(k);
        for (int i = 0; i < graph.get(k).size(); i++) {
            int neighbor = graph.get(k).get(i);
            if (neighbor != origin && graph.get(neighbor).size() % 2 != 0) {
                throw new NotFoundException();
            }
            graph.get(k).remove(i);
            graph.get(neighbor).remove(Integer.valueOf(k));

            if (neighbor == origin) {
                currentPath.add(origin);
                boolean allSeen;
                do {
                    boolean tmp = true;
                    for (int j = 0; j < currentPath.size(); j++) {
                        int entryNode = currentPath.get(j);
                        tmp &= graph.get(entryNode).size() == 0;
                        if (!tmp) {
                            List<Integer> tempPath = new ArrayList<>();
                            dfs(graph, entryNode, entryNode, tempPath);
                            int index = currentPath.indexOf(entryNode);
                            currentPath.remove(index);
                            currentPath.addAll(index, tempPath);
                        }
                    }
                    allSeen = tmp;
                } while (!allSeen);
                return;
            } else {
                dfs(graph, neighbor, origin, currentPath);
            }

        }
    }

    public static class NotFoundException extends Exception {
        public NotFoundException() {
            super("Euler Circuit Not Found");
        }
    }

    /**
     * 下面是个简单的园区线路图，每一条双向箭头都表示了一条道路，
     * 可以双向通行，但只能走一次，那么如果我们从大棚(A)出发，
     * 有没有路线可以一次性走完所有的道路，又回到大棚的呢，
     * 如果有的话，请列举所有的路线，以每个地点的字母排列出来，例如：ABCDEF
     */
    @Test
    public  void test() throws NotFoundException {
        int testCase = 2;
        int n = 0, k = 0;
        int[][] unDirectedEdges = null;
        switch (testCase) {
            case 1:
                n = 3;
                //unDirectedEdges = new int[][]{{0, 1}, {1, 2}, {2, 0}};
                unDirectedEdges = letterToNumArr(new char[][]{{'A', 'B'}, {'B', 'C'}, {'C', 'A'}});
                k = 0;
                break;
            case 2:
                n = 6;
                unDirectedEdges = letterToNumArr(new char[][]{{'A', 'C'}, {'C', 'D'},
                        {'D', 'F'}, {'F', 'B'}, {'B', 'F'}, {'F', 'E'},
                        {'E', 'B'}, {'B', 'C'}, {'C', 'B'}, {'B', 'A'}});
                //unDirectedEdges = new int[][]{{0, 2}, {2, 3}, {3, 5}, {5, 1}, {1, 5}, {5, 4}, {4, 1}, {1, 2}, {2, 1}, {1, 0}};
                k = 0;
                break;
        }
        List<Integer> integers = eulerCircuitByDFS(unDirectedEdges, n, k);
        log.info(JSON.toJSONString(integers.stream().map(EulerCircuit::numToLetter).collect(Collectors.toList())));
    }

    /**
     * 将字母转换成数字
     * 'A'->0
     *
     * @param input
     */
    public static int letterToNum(char input) {
        return ((int) input) - 65;
    }
    public static int[][] letterToNumArr(char[][] charArr) {
        int[][] intArr = new int[charArr.length][];
        for (int x = 0; x < charArr.length; x++) {
            int[] subArr = new int[charArr[x].length];
            for (int y = 0; y < charArr[x].length; y++) {
                subArr[y] = letterToNum(charArr[x][y]);
            }
            intArr[x]=subArr;
        }
        return intArr;
    }


    /**
     * 将数字转换成字母
     * 0->'A'
     *
     * @param input
     */
    public static char numToLetter(int input) {
        return (char) (input + 65);
    }

}
