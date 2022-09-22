package org.example.other.structure;

/**
 * 前缀树（字典树）
 * 假设组成所有单词的字符仅是'a'~'z'，包含以下四个主要功能：
 * void insert(String word)：添加word，可重复添加
 * void delete(String word)：删除word，如果word添加过多次，仅删除一个
 * boolean search(String word)：查询word是否在字典树中
 * int prefixNumber(String pre)：返回以字符串pre为前缀的单词数量
 * <p>
 * 优点是利用前缀节约存储空间
 * <p>
 * 基本性质如下：
 * 1.根节点没有字符路径。除根节点外，每一个节点都被一个字符路径找到
 * 2.从根节点出发到任何一个节点，如果将沿途经过的字符连接起来，一定为某个加入过的字符串的前缀
 * 3.每个节点向下所有的字符路径上的字符都不同
 */
public class Trie {

    static class TrieNode {
        public int path; // 表示有多少个单词共用这个节点
        public int end; // 表示有多少个单词以这个节点结尾

        // 每个位置代表'a'~'z'中一个字符
        // nexts[i]==null表示没有走向这个字符的路
        public TrieNode[] nexts;

        public TrieNode() {
            nexts = new TrieNode[26];
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }


    // 假设单词长度为N，从左到右遍历每个字符，依次从头节点开始根据每一个word[i]找到下一个节点
    // 如果找的过程中节点不存在，就建立新节点，记为a，并令a.path=1
    // 如果存在，记为b，令b.path++
    // 通过最后一个字符（word[N-1]）找到最后一个节点时记为e，令e.path++，e.end++
    public void insert(String word) {
        if (word == null) {
            return;
        }

        char[] chars = word.toCharArray();
        TrieNode node = root;
        node.path++;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (node.nexts[index] == null) {
                node.nexts[index] = new TrieNode();
            }
            node = node.nexts[index];
            node.path++;
        }
        node.end++;
    }


    // 从左到右遍历word中每个字符，依次从头节点开始根据每一个word[i]找到下一个节点
    // 如果节点不存在，返回false
    // 如果能通过word[N-1]找到最后一个节点，记为e，如果e.end!=0，返回true，否则返回false
    public boolean search(String word) {
        if (word == null) {
            return false;
        }

        char[] chars = word.toCharArray();
        TrieNode node = root;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (node.nexts[index] == null) {
                return false;
            }
            node = node.nexts[index];
        }
        return node.end != 0;
    }

    // 先调用search(word)，看word是否存在
    // 若不存在，直接返回
    // 从左到右遍历word每个字符，依次从头节点开始往下找
    // 把过程中每一个节点的path减1。如果发现下一个节点的path值减完后已经为0，直接从当前节点的map中删除后序所有路径并返回
    // 如果遍历到最后一个节点，记为e，令e.path--，e.end--
    public void delete(String word) {
        if (search(word)) {
            char[] chars = word.toCharArray();
            TrieNode node = root;
            node.path--;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (node.nexts[index].path-- == 1) {
                    node.nexts[index] = null;
                    return;
                }
                node = node.nexts[index];
            }
            node.end--;
        }
    }


    // 和查找操作同理，假设最后找到的节点为e，返回e.path
    public int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        char[] chars = pre.toCharArray();
        TrieNode node = root;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (node.nexts[index] == null) {
                return 0;
            }
            node = node.nexts[index];
        }
        return node.path;
    }


}
