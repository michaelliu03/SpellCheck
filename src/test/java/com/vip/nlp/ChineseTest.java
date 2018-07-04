package com.vip.nlp;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author michael.liu
 * @Title: ChineseTest
 * @ProjectName SpellCheck
 * @Description: TODO
 * @date 2018/7/415:39
 */


public class ChineseTest extends TestCase {

    public final static String  InputFilePath = "./data/mingci.csv";
    public void loadData(){
    try {
            BufferedReader br = new BufferedReader(new FileReader(new File(InputFilePath)));
            String line = "";

            while ((line = br.readLine()) != null) {
                String sentence = line.trim();


            }

            br.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}