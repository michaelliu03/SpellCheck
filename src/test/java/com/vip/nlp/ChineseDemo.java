package com.vip.nlp;

import com.vip.nlp.automachine.DFA;
import com.vip.nlp.automachine.NFA;
import com.vip.nlp.trie.Trie;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author michael.liu
 * @Title: ChineseDemo
 * @ProjectName SpellCheck
 * @Description: TODO
 * @date 2018/7/415:39
 */


public class ChineseDemo {

    public final static String  InputFilePath = "./data/mingci.csv";
    public static Trie<String> stringTrie = new Trie<String>();

    public void loadData(){
    try {
            BufferedReader br = new BufferedReader(new FileReader(new File(InputFilePath)));
            String line = "";

            while ((line = br.readLine()) != null) {
                String sentence = line.trim();
                if(sentence.equals("ci_tiao,pin_yin,ci_xing"))
                    continue;
                else{
                    String [] nounWords= sentence.split(",");
                    stringTrie.add(nounWords[0],nounWords[0]);
                }
            }
           // System.out.println(stringTrie.find("信息"));
            br.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void NFAMatch(){
        NFA lev = new NFA("信息",1);
        DFA dfa = lev.toDFA();
        List<String> match = dfa.transduce(stringTrie);
        for(String s:match){
            System.out.println(s);
        }
    }

    public static void main(String [] args){
        ChineseDemo chineseDemo = new ChineseDemo();
        chineseDemo.loadData();
        chineseDemo.NFAMatch();
    }
}