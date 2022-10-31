package com.example.algorithmcontest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 时间复杂度o(n^^2)
 * 空间复杂度o(n)
 */
@Slf4j
public class InvalidVisit {
    private static final Map<String, List<String>> idTimeMap       = new LinkedHashMap<>();
    private static final SimpleDateFormat          HH_MM_SS_FORMAT = new SimpleDateFormat("HH:mm:ss");

    static class UserAndNum {
        private String userId;
        private int    invalidNum;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getInvalidNum() {
            return invalidNum;
        }

        public void setInvalidNum(int invalidNum) {
            this.invalidNum = invalidNum;
        }

        @Override
        public String toString() {
            return "UserAndNum{" +
                    "userId='" + userId + '\'' +
                    ", invalidNum=" + invalidNum +
                    '}';
        }
    }

    /**
     * 找出无效访问的userId，并按照不合法访问次数倒序排序
     * 同一用户访问时间间隔小于3s
     * 同一用户10s连续访问三次
     *
     * @param keyUserId
     * @param keyTime
     * @return
     */
    public static List<String> alertUserId(List<String> keyUserId, List<String> keyTime) throws ParseException {
        List<UserAndNum> userAndNumList = new ArrayList<>();
        //o(n)
        getIdTimeMap(keyUserId, keyTime);
        log.info("idTimeMap:{}", idTimeMap);
        if (idTimeMap != null && !idTimeMap.isEmpty()) {
            //o(n)
            for (String key : idTimeMap.keySet()) {
                //o(n)
                int invalidNum = computeInvalidNum(idTimeMap.get(key));
                if (invalidNum > 0) {
                    UserAndNum userAndNum = new UserAndNum();
                    userAndNum.setUserId(key);
                    userAndNum.setInvalidNum(invalidNum);
                    userAndNumList.add(userAndNum);
                }
            }
        }
        //o(n)
        return userAndNumList.stream().sorted(new Comparator<UserAndNum>() {
            @Override
            public int compare(UserAndNum o1, UserAndNum o2) {
                return o2.getInvalidNum() - o1.getInvalidNum();
            }
        }).map(UserAndNum::getUserId).collect(Collectors.toList());
    }

    /**
     * 根据访问时间数组判断不合法次数
     * * 同一用户访问时间间隔小于3s
     * * 同一用户10s连续访问三次
     *
     * @param timeList
     * @return
     */
    private static int computeInvalidNum(List<String> timeList) throws ParseException {
        if (timeList == null || timeList.size() < 2) {
            return 0;
        } else if (timeList.size() == 2) {
            return isInSeconds(timeList.get(1), timeList.get(0), 3000, true) ? 1 : 0;
        } else {
            int ptr = 0, next = 1, twoNext = 2;
            int invalidNum = 0;
            Collections.sort(timeList);
            while (next < timeList.size()) {
                if (twoNext < timeList.size() && isInSeconds(timeList.get(twoNext), timeList.get(ptr), 10000, true)) {
                    invalidNum++;
                }
                if (isInSeconds(timeList.get(next), timeList.get(ptr), 3000, false)) {
                    invalidNum++;
                }
                ptr++;
                next++;
                twoNext++;
            }
            return invalidNum;
        }
    }

    /**
     * 两个时间点是否在seconds内
     *
     * @param after
     * @param before
     * @param containsEqual
     * @return
     */
    private static boolean isInSeconds(String after, String before, int seconds, boolean containsEqual) throws ParseException {
        Date afterDate = HH_MM_SS_FORMAT.parse(after);
        Date beforeDate = HH_MM_SS_FORMAT.parse(before);
        long abs = Math.abs(afterDate.getTime() - beforeDate.getTime());
        if (containsEqual) {
            return abs <= seconds;
        } else {
            return abs < seconds;
        }
    }

    /**
     * 初始化用户-访问时间map
     *
     * @param keyUserId
     * @param keyTime
     */
    private static void getIdTimeMap(List<String> keyUserId, List<String> keyTime) {
        if (keyUserId != null && !keyUserId.isEmpty() && keyTime != null
                && !keyTime.isEmpty() && keyUserId.size() == keyTime.size()) {
            for (int i = 0; i < keyUserId.size(); i++) {
                String tempKey = keyUserId.get(i);
                if (idTimeMap.containsKey(tempKey)) {
                    idTimeMap.get(tempKey).add(keyTime.get(i));
                } else {
                    idTimeMap.put(tempKey, new ArrayList<>(Collections.singletonList(keyTime.get(i))));
                }
            }
        }
    }
    @Test
    public  void test() throws ParseException {
        List<String> keyUserId = new ArrayList<>(Arrays.asList("rs01",
                "rs01", "rs02", "rs05", "rs03", "rs02", "rs04", "rs02", "rs01"));
        List<String> keyTime = new ArrayList<>(Arrays.asList("00:00:01",
                "00:00:02", "00:00:04", "00:00:05", "00:00:08", "00:00:09", "00:00:12", "00:00:13", "00:00:14"));
        log.info(String.valueOf(alertUserId(keyUserId, keyTime)));
    }
}
