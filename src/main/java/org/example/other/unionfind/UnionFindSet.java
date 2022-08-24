package org.example.other.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 给定一个没有重复值的整型数组arr。初始时认为arr中每一个数各自都是一个单独的集合
 * 请设计一种叫UnionFind的结构，提供以下两个操作：
 * 1.boolean isSameSet(int a, int b)：查询a和b两个数是否属于同一个集合
 * 2.void union(int a, int b)：把a所在的集合与b所在的集合合并在一起，原本两个集合各自的元素以后都算作同一个集合
 * 要求：单词调用isSameSet或union方法的平均时间复杂度为O(1)
 */
public class UnionFindSet<V> {

    // 初始化：初始化时，每个元素自己单独构成一个集合，每个元素的father是它自己，可以用哈希表进行表达
    // 每个元素还有一个信息叫rank，只有一个集合的代表节点才会有这个信息，为代表节点所在集合一共有多少个元素。初始化为1
    // 所有集合的所有元素的father信息维护在fatherMap中，所有代表节点的rank信息维护在rankMap中
    // 集合中有多个元素时，下层节点的father为上层节点，最上层节点的father是它自己，最上层节点就是代表节点

    // 查找和路径压缩：在并查集中，若要查一个节点属于哪个集合，就是在查这个节点所在集合的代表节点是什么，这个过程叫findFather过程
    // findFather过程会进行路径压缩，一个节点找到最上层节点后，会将整条路径每个节点的father都设置为最上层节点，下一次查找就更快

    // 合并：首先，两个集合进行合并操作时，参数并不是两个集合，而是并查集中任意两个节点a和b
    // 分别找到各自的代表节点aF和bF，然后用如下策略决定由哪个代表节点作为合并后的代表节点：
    // 1.如果aF==bF，说明本来就是同一个集合
    // 2.如果aF!=bF，可以在rankMap中查询到各自rank值：
    //   2.1 如果aFrank<bFrank，那么把aF的father设置为bF，同时bFrank+=aFrank。删除aF的rank信息
    //   2.2 如果aFrank>bFrank同理
    //   2.3 如果相等，谁做代表节点都可以，更新rankMap
    static class Element<V> {
        public V value;

        public Element(V value) {
            this.value = value;
        }
    }

    public Map<V, Element<V>> elementMap;
    public Map<Element<V>, Element<V>> fatherMap;
    public Map<Element<V>, Integer> rankMap;

    public UnionFindSet(List<V> list) {
        elementMap = new HashMap<>();
        fatherMap = new HashMap<>();
        rankMap = new HashMap<>();
        for (V value : list) {
            Element<V> element = new Element<>(value);
            elementMap.put(value, element);
            fatherMap.put(element, element);
            rankMap.put(element, 1);
        }
    }

    private Element<V> findHead(Element<V> element) {
        Stack<Element<V>> path = new Stack<>();
        while (element != fatherMap.get(element)) {
            path.push(element);
            element = fatherMap.get(element);
        }
        while (!path.isEmpty()) {
            fatherMap.put(path.pop(), element);
        }
        return element;
    }

    public boolean isSameSet(V a, V b) {
        if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
            return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
        }
        return false;
    }


    public void union(V a, V b) {
        if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
            Element<V> aF = findHead(elementMap.get(a));
            Element<V> bF = findHead(elementMap.get(b));
            if (aF != bF) {
                Element<V> big = rankMap.get(aF) >= rankMap.get(bF) ? aF : bF;
                Element<V> small = big == aF ? bF : aF;
                fatherMap.put(small, big);
                rankMap.put(big, rankMap.get(aF) + rankMap.get(bF));
                rankMap.remove(small);
            }
        }
    }

}
