package org.example.other;

/**
 * 给定一个等概率随机产生1～5的随机函数rand1To5，除此之外不能使用任何额外随机机制，用这个函数实现等概率产生1～7的随机函数
 */
public class Random {

    // 随机产生1～5
    public int rand1To5() {
        return (int) (Math.random() * 5) + 1;
    }

    // 核心思路是插空法和筛选，步骤如下：
    // 1.rand1To5()-1等概率随机产生0~4
    // 2.(rand1To5()-1)*5等概率随机产生0,5,10,15,20
    // 3.(rand1To5()-1)*5+((rand1To5()-1)等概率随机产生0~24
    // 4.调用步骤3生成随机数，如果大于20则重新生成，这样等概率生成一个0~20的数
    // 5.对步骤4的结果进行%7运算，等概率生成0~6的数，再加1生成1~7的数
    // 插空法就是比如有一个产生0~M的函数，就用这个函数乘以M+1，然后再加上这个随机函数的结果，就把空填上了
    // 之所以要扩大这个随机范围，是因为要实现的随机范围比本身范围要大，否则可以直接进入筛选的过程
    // 本质是产生一个k位的m进制数，保证这个数要大于等于n
    public int rand1To7() {
        int num;
        do {
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        } while (num > 20);
        return num % 7 + 1;
    }


    // 补充问题：给定一个以p概率产生0，1-p概率产生1的随机函数rand01p，实现等概率随机生成1~6的随机函数
    public int rand01p() {
        // 可随意改变p
        double p = 0.83;
        return Math.random() < p ? 0 : 1;
    }

    // 虽然产生0和1的概率不相等，但产生01和10的概率相等
    // 下面代码的含义就是产生00或11就重试，产生01时返回0，产生10时返回1
    public int rand01() {
        int num;
        do {
            num = rand01p();
        } while (num == rand01p());
        return num;
    }

    // 插空法，实现0~3的随机
    public int rand0To3() {
        return rand01() * 2 + rand01();
    }

    public int rand1To6() {
        int num;
        do {
            num = rand0To3() * 4 + rand0To3(); // 0~15
        } while (num > 12);
        return num % 6 + 1;
    }


    // 进阶问题：给定一个等概率随机产生1～m的随机函数rand1ToM。实现等概率产生1~n的随机函数
    public int rand1ToM(int m) {
        return (int) (Math.random() * m) + 1;
    }


    public int rand1ToN(int n, int m) {
        int[] nMSys = getMSysNum(n - 1, m); // 本质上是利用0~m-1生成0~n-1，因此这里需要将n-1转换成m进制的数
        int[] randNum = getRanMSysNumLessN(nMSys, m);
        return getNumFromMSysNum(randNum, m) + 1;
    }

    // 将m进制表达的mSysNum转换成十进制
    private int getNumFromMSysNum(int[] mSysNum, int m) {
        int res = 0;
        for (int i = 0; i != mSysNum.length; i++) {
            res = res * m + mSysNum[i];
        }
        return res;
    }

    // 等概率随机产生一个0~nMSys范围的数，只不过用m进制数表达
    private int[] getRanMSysNumLessN(int[] nMSys, int m) {
        int[] res = new int[nMSys.length];
        int start = 0; // nMSys这个m进制的数最高位开始的位置
        while (nMSys[start] == 0) {
            start++;
        }
        int index = start;
        boolean lastEqual = true;
        while (index != nMSys.length) {
            res[index] = rand1ToM(m) - 1; // 在每一位上随机产生一个m进制的数

            // 如果res比nMSys大，则重新从第一个位置开始生成
            if (lastEqual) {
                if (res[index] > nMSys[index]) {
                    index = start;
                    continue;
                } else {
                    lastEqual = res[index] == nMSys[index];
                }
            }
            index++;
        }
        return res;
    }

    // 将value转成m进制数
    private int[] getMSysNum(int value, int m) {
        int[] res = new int[32];
        int index = res.length - 1;
        while (value != 0) {
            res[index--] = value % m;
            value = value / m;
        }
        return res;
    }


}
