package com.vip.nlp.automachine;

import java.util.BitSet;

/**
 * @author michael.liu
 * @Title: StateSet
 * @ProjectName SpellCheck
 * @Description: StateSet
 * @date 2018/7/414:27
 */


public class StateSet {

    public BitSet table;


    public StateSet(int n, int e) {
        table = new BitSet((n + 1) * (e + 1));
    }


    public boolean contains(int o) {
        return table.get(o);
    }


    public boolean containsAny(StateSet s) {
        for (int state = s.table.nextSetBit(0); state >= 0; state = s.table
                .nextSetBit(state + 1)) {
            if (table.get(state))
                return true;

        }
        return false;
    }


    public void add(int s) {
        table.set(s);
    }


    public void add(StateSet s) {
        for (int state = s.table.nextSetBit(0); state >= 0; state = s.table
                .nextSetBit(state + 1)) {
            table.set(state);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int state = table.nextSetBit(0); state >= 0; state = table
                .nextSetBit(state + 1)) {
            sb.append(state + "\t");
        }
        return sb.toString();
    }
}