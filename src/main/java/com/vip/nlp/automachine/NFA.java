package com.vip.nlp.automachine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

/**
 * @author michael.liu
 * @Title: NFA
 * @ProjectName SpellCheck
 * @Description: TODO
 * @date 2018/7/415:28
 */


public class NFA {
    public int getStateNo(int c, int e) {
        int hash = e * (this.n+1) + c;
        return hash;
    }

    private int n, k;

    private int _startState;
    HashMap<Integer, HashMap<Character, Integer>> transitions = new HashMap<Integer, HashMap<Character, Integer>>();
    HashMap<Integer, StateSet> anyTrans = new HashMap<Integer, StateSet>();
    HashMap<Integer, Integer>  epsilonTrans = new HashMap<Integer, Integer>();

    StateSet finalStates = new StateSet(n, k);

    public StateSet startState() {
        StateSet start = new StateSet(n, k);
        start.add(_startState);
        return expand(start);
    }

    public void addAnyTrans(int src, int dest) {
        StateSet transition = anyTrans.get(src);
        if (transition == null) {
            transition = new StateSet(n, k);
            anyTrans.put(src, transition);
        }

        transition.add(dest);
    }

    public void addTransition(int src, char c, int dest) {
        HashMap<Character, Integer> transition = transitions.get(src);
        if (transition == null) {
            transition = new HashMap<Character, Integer>();
            transitions.put(src, transition);
        }

        transition.put(c, dest);
    }

    public void addFinalState(int state) {
        finalStates.add(state);
    }


    public NFA(String term, int k) {
        this._startState = getStateNo(0, 0);
        this.n = term.length();
        this.k = k;

        for (int i = 0; i < n; ++i) {
            char c = term.charAt(i);
            for (int e = 0; e < (k + 1); ++e) {

                addTransition(getStateNo(i, e), c, getStateNo(i + 1, e));
                if (e < k) {

                    addAnyTrans(getStateNo(i, e),getStateNo(i,e + 1));


                    epsilonTrans.put(getStateNo(i, e), getStateNo(i + 1, e + 1));


                    addAnyTrans(getStateNo(i, e),getStateNo(i+1,e + 1));
                }
            }
        }
        for (int e = 0; e < (k + 1); ++e) {
            if (e < k){
                addAnyTrans(getStateNo(term.length(), e),getStateNo(term.length(), e + 1));
            }
            addFinalState(getStateNo(term.length(), e)); //���ý���״̬
        }
    }

    public HashSet<Character> getInputs(StateSet current) {
        HashSet<Character> inputs = new HashSet<Character>();

        for (int state = current.table.nextSetBit(0); state >= 0; state = current.table
                .nextSetBit(state + 1)) {
            HashMap<Character, Integer> transition = transitions.get(state);
            if (transition == null)
                continue;
            for (Map.Entry<Character, Integer> e : transition.entrySet()) {
                inputs.add(e.getKey());
            }
        }
        return inputs;
    }

    public StateSet expand(StateSet states) {
        Stack<Integer> frontier = new Stack<Integer>();
        for (int state = states.table.nextSetBit(0); state >= 0; state = states.table
                .nextSetBit(state + 1)) {
            frontier.add(state);
        }
        //System.out.println("expand");

        while (!frontier.isEmpty()) {
            //System.out.println("in frontier");
            Integer state = frontier.pop();
            HashMap<Character, Integer> transition = transitions
                    .get(state);

            Integer newStates = null;
            if (transition != null) {
                newStates = epsilonTrans.get(state);
            }

            if(newStates==null){
                continue;
            }

            if(states.table.get(newStates)){
                newStates = null;
            }

            frontier.add(newStates);

            if(newStates!=null)
                states.add(newStates);
        }
        return states;
    }

    public DFA toDFA() {
        StateSet start = startState();
        //System.out.println("start:" + start);
        DFA dfa = new DFA(new DFA.State(start)); //����DFA
        Stack<StateSet> frontier = new Stack<StateSet>();
        frontier.add(start);
        HashSet<DFA.State> seen = new HashSet<DFA.State>();
        while (!frontier.isEmpty()) {
            StateSet current = frontier.pop();
            HashSet<Character> inputs = getInputs(current);
            if (inputs == null)
                continue;
            for (char input : inputs) {
                StateSet newState = nextState(current, input);
                if (!seen.contains(newState)) {
                    frontier.add(newState);
                    seen.add(new DFA.State(newState));
                    if (isFinal(newState)) {
                        dfa.addFinalState(new DFA.State(newState));
                    }
                }
                dfa.addTransition(new DFA.State(current), input,
                        new DFA.State(newState));
            }


            StateSet anyStates = new StateSet(n, k);
            for (int state = current.table.nextSetBit(0); state >= 0; state = current.table.nextSetBit(state + 1)) {
                anyStates.add(state);
            }
            dfa.setDefaultTransition(new DFA.State(current),
                    new DFA.State(anyStates));
        }
        return dfa;
    }

    private boolean isFinal(StateSet newState) {
        return finalStates.containsAny(newState);
    }

    private StateSet nextState(StateSet current, char input) {
        StateSet destStateSet = new StateSet(n, k);

        for (int state = current.table.nextSetBit(0); state >= 0; state = current.table.nextSetBit(state + 1)) {
            HashMap<Character, Integer> stateTransition = transitions
                    .get(state);
            if (stateTransition == null) {
                continue;
            }
            Integer temp = stateTransition.get(input);
            if (temp != null) {
                destStateSet.add(temp);
            }
            StateSet t = anyTrans.get(state);
            if (t != null)
                destStateSet.add(t);
        }

        return expand(destStateSet);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Map.Entry<Integer, HashMap<Character, Integer>> e : transitions
                .entrySet()) {
            sb.append(e.getKey() + " -> " + e.getValue() + "\n");
        }
        sb.append("\n");
        for(Map.Entry<Integer, Integer> e :epsilonTrans.entrySet()){
            sb.append(e.getKey() + " -> " + e.getValue() + "\n");
        }
        sb.append("\n");
        for(Map.Entry<Integer, StateSet> e :anyTrans.entrySet()){
            sb.append(e.getKey() + " -> " + e.getValue() + "\n");
        }
        sb.append("̬\n");

        sb.append(finalStates.toString());
        //for (int state = finalStates.table.nextSetBit(0); state >= 0; state = finalStates.table.nextSetBit(state + 1)) {
        //	sb.append(state + "\n");
        //}

        return sb.toString();
    }
}