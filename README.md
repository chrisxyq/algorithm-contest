## 20221003题目：无效访问的userId

```
/**
 * 20221003题目：
 * 如果出现下述两种情况（任一条件满足，同时满足就算两次无效访问）表示访问无效，
 * 找出无效访问的userId，并按照userId出现的不合法访问次数倒序排序
 * - 同一用户访问时间间隔小于3s
 * - 同一用户10s连续访问三次
 * 限定时间一个自然日
 * 
 * 时间复杂度o(n^^2)
 * 空间复杂度o(n)
 */
```

### 解答：

- step1:计算 用户-访问时间map
- step2:为每个用户计算不合法访问次数，维护三个指针移动
- step3：将UserAndNum实体按照userId出现的不合法访问次数倒序排序，取UserId并返回

### 时间空间复杂度：

 * 时间复杂度o(n^^2)
 * 空间复杂度o(n)

------

## 20221010题目：园区散步路线

```
/**
 * 20221010题目：
 * 天气凉快了，大家都喜欢饭后在园区进行散步，园区的道路四通八达，
 * 大家都有自己习惯的散步路线，那有没有什么路线是可以一次性走完所有的道路而不重复的呢，
 * 下面是个简单的园区线路图，每一条双向箭头都表示了一条道路，可以双向通行，但只能走一次，
 * 那么如果我们从大棚(A)出发，有没有路线可以一次性走完所有的道路，又回到大棚的呢，
 * 如果有的话，请列举所有的路线，以每个地点的字母排列出来，例如：ABCDEF
 * 
 * 判断欧拉回路
 * 存在欧拉回路充要条件：当且仅当图是连通的而且每个顶点的度是偶数
 */
```

![image-20221107195545138](https://raw.githubusercontent.com/chrisxyq/pic/main/image-20221107195545138.png)

### 解答：

### 时间空间复杂度：

----

## 20221031题目：

### 解答：

### 时间空间复杂度：

## 20221107题目：

```
/**
 * 20221107题目：
 * 村⾥的每个⼈都能够到果园⾥去采摘，但规定每棵树上只能采摘1个果⼦（假设每棵树上的果⼦数量⽆
 * 限，每个⼈都能采到）。为了避免⼤家挤在⼀起产⽣踩踏事件，果园还规定在某颗果树采摘完后不允
 * 许采摘相邻（浅蓝⾊部分为⽰例）的果树，同时不能回头采已经⾛过的果树。现在假设市场上苹果的
 * 价格是4元/个，梨的价格是3元/个，桃⼦的价格是2元/个。
 */
```

![image-20221107200224391](https://raw.githubusercontent.com/chrisxyq/pic/main/image-20221107200224391.png)

### 解答：

### 

### 时间空间复杂度：

```
* 对于m*n矩阵
* 时间复杂度：o(m*n)
* 空间复杂度：o(m*n*2)
```