package org.example.string;

import java.util.*;

/**
 * 给定两个字符串start和to，再给定一个字符串列表list，其中一定包含to且没有重复字符串。所有字符串都是小写的
 * 规定start每次只能改变一个字符，每次变成的字符都必须在list中，最终变成to的最短变换路径是什么
 */
public class MinPaths {

    // 步骤如下：
    // 1.把start加入list，再根据list生成每一个字符串的nexts信息
    //   nexts信息是指，如果只改变一个字符，该字符串可以变成哪些字符串
    //   1.1 把list中所有字符串放入哈希表set中，用来判断一个字符串是否在list中
    //   1.2 对每一个字符串，遍历变换每一位字符，生成nexts信息
    // 2.有了nexts信息后，相当于有了一张图，每个字符串相当于图中一个点
    //   从start出发，利用nexts信息和深度优先遍历，求出每一个字符串到start的最短距离
    // 3.从start出发往下走，保证每一步走到的字符串cur到start的最短距离都在加1
    //   如果能到to，收集整条路
    public List<List<String>> findMinPaths(String start, String to, List<String> list) {
        list.add(start);
        Map<String, List<String>> nexts = getNexts(list);
        Map<String, Integer> distances = getDistances(start, nexts);
        LinkedList<String> paths = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        getShortestPaths(start, to, nexts, distances, paths, res);
        return res;
    }

    private void getShortestPaths(String cur,
                                  String to,
                                  Map<String, List<String>> nexts,
                                  Map<String, Integer> distances,
                                  LinkedList<String> paths,
                                  List<List<String>> res) {
        paths.add(cur);
        if (to.equals(cur)) {
            res.add(new LinkedList<>(paths));
        } else {
            for (String next : nexts.get(cur)) {
                if (distances.get(next) == distances.get(cur) + 1) {
                    getShortestPaths(next, to, nexts, distances, paths, res);
                }
            }
        }
        paths.pollLast();
    }

    private Map<String, Integer> getDistances(String start, Map<String, List<String>> nexts) {
        Map<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        Set<String> set = new HashSet<>();
        set.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (String str : nexts.get(cur)) {
                if (!set.contains(str)) {
                    distances.put(str, distances.get(cur) + 1);
                    queue.add(str);
                    set.add(str);
                }
            }
        }
        return distances;
    }

    private Map<String, List<String>> getNexts(List<String> list) {
        Set<String> set = new HashSet<>(list);
        Map<String, List<String>> nexts = new HashMap<>();
        for (String word : list) {
            nexts.put(word, getNext(word, set));
        }
        return nexts;
    }

    private List<String> getNext(String word, Set<String> set) {
        List<String> res = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (char cur = 'a'; cur <= 'z'; cur++) {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != cur) {
                    char tmp = chars[i];
                    chars[i] = cur;
                    if (set.contains(String.valueOf(chars))) {
                        res.add(String.valueOf(chars));
                    }
                    chars[i] = tmp;
                }
            }
        }
        return res;
    }

}
