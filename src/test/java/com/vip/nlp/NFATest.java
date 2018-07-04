package com.vip.nlp;

import com.vip.nlp.automachine.DFA;
import com.vip.nlp.automachine.NFA;
import com.vip.nlp.trie.Trie;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * @author michael.liu
 * @Title: NFATest
 * @ProjectName SpellCheck
 * @Description: TODO
 * @date 2018/7/415:36
 */


public class NFATest extends TestCase {

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        testNFA();
        testMatch();
    }

    public static void testNFA() {
        NFA lev = new NFA("food",2);
        System.out.println(lev.toString());
    }

    public static void testMatch(){

        NFA lev = new NFA("fod",1);

        DFA dfa = lev.toDFA();


        Trie<String> stringTrie = new Trie<String>();
        stringTrie.add("food", "food");
        stringTrie.add("hammer", "hammer");
        stringTrie.add("hammock", "hammock");
        stringTrie.add("ipod", "ipod");
        stringTrie.add("iphone", "iphone");


        ArrayList<String> match = dfa.transduce(stringTrie);
        for(String s:match){
            System.out.println(s);
        }
    }

    public static void testDFA() {

        NFA lev = new NFA("foxd",2);
        System.out.println(lev.toString());

        DFA dfa = lev.toDFA();

        System.out.println(dfa.accept("food"));
        System.out.println(dfa.accept("fooxd"));
        System.out.println(dfa.accept("fooxdjj"));
    }
}