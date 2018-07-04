package com.vip.nlp.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author michael.liu
 * @Title: TrieNode
 * @ProjectName SpellCheck
 * @Description: trie node
 * @date 2018/7/414:10
 */


public class TrieNode<T> {
    private Character nodeKey;
    private T nodeValue;
    private boolean terminal;
    private Map<Character, TrieNode<T>> children = new HashMap<Character, TrieNode<T>>();

    public Character getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(Character nodeKey) {
        this.nodeKey = nodeKey;
    }

    public T getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(T nodeValue) {
        this.nodeValue = nodeValue;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public Map<Character, TrieNode<T>> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode<T>> children) {
        this.children = children;
    }

    public String toString()
    {
        return String.valueOf(nodeKey);
    }
}