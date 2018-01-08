package Practice2017.C;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SolutionPractice2017CFinal {

    private static final String PATH = "./Solutions/src/Practice2017/C/SampleIn";

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByWordsI(args.length > 0, PATH);
        //System.err.println("hi");

        try {

            int n = io.getInt();
            //System.err.println("n: "+n);

            int[] cups = new int[n];
            for (int i = 0; i < n; i++) {
                cups[i] = (short) io.getInt();
                //System.err.println("\t"+i+": "+cups[i]);
            }

            int v = io.getInt();
            //System.err.println("v: "+v);

            if (cups[0] == v) {
                io.println(0);
                io.flush();
                io.close();
                System.exit(0);
            }

            Transformer<int[], MinimizingNode<int[]>> t = (node) -> {
                ArrayList<MinimizingNode<int[]>> out = new ArrayList<>();
                int[] c = node.state.state, p;
                int at = node.value, d;
                for (int i = 0; i < n; i++) {
                    for (int j = i+1; j < n; j++) {
                        if (c[i] > 0 && c[j] < cups[j]) {
                            d = Math.min(cups[j] - c[j], c[i]);
                            //t = at+d;
                            p = c.clone();
                            p[i] -= d;
                            p[j] += d;
                            out.add(new MinimizingNode<>(new IntState(p), at+d));
                        }
                        if (c[j] > 0 && c[i] < cups[i]) {
                            d = Math.min(cups[i] - c[i], c[j]);
                            //t = at+d;
                            p = c.clone();
                            p[j] -= d;
                            p[i] += d;
                            out.add(new MinimizingNode<>(new IntState(p), at+d));
                        }
                    }
                }
                return out;
            };

            StateChecker<State<int[]>, Integer> c = (state, target) -> state.state[0] == target;

            GraphMinimizer<int[]> min = new GraphMinimizer<int[]>(v, cups, t, c, false);

            int[] initial = new int[n];
            initial[0] = cups[0];

            IntState start = new IntState(initial);

            int best = min.traverse(start, 0);

            if (min.converged) {
                io.println(best);
            } else {
                io.println("impossible");
            }

            io.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
    }

    private static class GraphMinimizer<E> extends GraphTraverser<E, MinimizingNode<E>, Integer> {

        protected GraphMinimizer(int target, E bounds, Transformer<E, MinimizingNode<E>> transformer, StateChecker<State<E>, Integer> checker, boolean returnFirst) {
            super(target, bounds, transformer, checker, returnFirst);
            this.bestSeen = Integer.MAX_VALUE;
        }

        public int traverse(State start, int initial) {
            Queue<MinimizingNode>        searchQueue  = new PriorityQueue<>();
            Map<State, Integer> spannedGraph = new HashMap(Math.round((float) Math.sqrt(start.count(bounds))));

            searchQueue.add(new MinimizingNode(start, initial));
            spannedGraph.put(start, initial);

            MinimizingNode<E> current;
            Integer seen;
            Iterable<MinimizingNode<E>> neighbours;
            while (!searchQueue.isEmpty()) {
                current = searchQueue.poll();
                //seen = spannedGraph.putIfAbsent(current.state, current.value);
                //if (seen != null && seen < current.value) continue;

                //System.err.println("Visiting: " + current.state.toString() + " with "+current.value);

                if (checker.isTarget(current.state, target)) {
                    converged = true;
                    if (returnFirst) {
                        return bestSeen = current.value;
                    } else {
                        bestSeen = Math.min(bestSeen, current.value);
                        continue;
                    }
                }

                neighbours = transformer.neighbours(current);
                for(MinimizingNode<E> n : neighbours) {
                    if (!returnFirst && n.value > bestSeen) continue;

                    //System.err.println("Neighbour: " + n.state.toString() + " with "+n.value);
                    if (!spannedGraph.containsKey(n.state)) {
                        searchQueue.add(n);
                        spannedGraph.put(n.state, n.value);
                        //System.err.println("\t Added...");
                    } else {
                        seen = spannedGraph.get(n.state);
                        if (seen > n.value) {
                            spannedGraph.replace(n.state, n.value);
                            //searchQueue.remove(n);
                            searchQueue.add(n);
                        }
                    }//*/

                    /*seen = spannedGraph.get(n.state);
                    if (seen == null) {
                        searchQueue.add(n);
                        spannedGraph.put(current.state, current.value);
                        //System.err.println("\t Added...");
                    }
                    else if (seen > n.value) {
                        spannedGraph.replace(n.state, n.value);
                        searchQueue.remove(n);
                        searchQueue.add(n);
                        //System.err.println("\t Replaced...");
                    }//*/
                }

                //try { System.in.read(); } catch (Exception ignored) {}
            }

            fullySpanned = true;
            return bestSeen;
        }
    }

    private static class GraphMaximizer<E> extends GraphTraverser<E, MaximizingNode<E>, Integer> {

        protected GraphMaximizer(int target, E bounds, Transformer<E, MaximizingNode<E>> transformer, StateChecker<State<E>, Integer> checker, boolean returnFirst) {
            super(target, bounds, transformer, checker, returnFirst);
            this.bestSeen = Integer.MIN_VALUE;
        }

        public int traverse(State start, int initial) {
            Queue<MaximizingNode>        searchQueue  = new PriorityQueue<>();
            Map<State, Integer> spannedGraph = new HashMap(Math.round((float) Math.sqrt(start.count(bounds))));

            searchQueue.add(new MaximizingNode(start, initial));
            spannedGraph.put(start, initial);

            MaximizingNode<E> current;
            Integer seen;
            Iterable<MaximizingNode<E>> neighbours;
            while (!searchQueue.isEmpty()) {
                current = searchQueue.poll();
                //seen = spannedGraph.putIfAbsent(current.state, current.value);
                //if (seen != null && seen > current.value) continue;

                if (checker.isTarget(current.state, target)) {
                    converged = true;
                    if (returnFirst) {
                        return bestSeen = current.value;
                    } else {
                        bestSeen = Math.max(bestSeen, current.value);
                        continue;
                    }
                }

                neighbours = transformer.neighbours(current);
                for(MaximizingNode<E> n : neighbours) {
                    seen = spannedGraph.get(n.state);
                    if (seen == null) {
                        searchQueue.add(n);
                        spannedGraph.put(current.state, current.value);
                    }
                    else if (seen < n.value) {
                        spannedGraph.replace(n.state, n.value);
                        searchQueue.remove(n);
                        searchQueue.add(n);
                    }
                }
            }

            fullySpanned = true;
            return bestSeen;
        }
    }

    private static class IntState extends State<int[]> implements Comparable<IntState> {

        public IntState(int[] state) {
            super(state);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof IntState)) return false;

            IntState intState = (IntState) o;

            return Arrays.equals(state, intState.state);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(state);
        }

        @Override
        public boolean inBounds(int[] bounds) {
            for (int i = 0; i < this.state.length; i++) {
                if (this.state[i] > bounds[i]) return false;
            }
            return true;
        }

        @Override
        public boolean inBoundsStrict(int[] bounds) {
            for (int i = 0; i < this.state.length; i++) {
                if (this.state[i] >= bounds[i]) return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return Arrays.toString(this.state);
        }

        @Override
        public int count(int[] ranges) {
            int count = 1;
            for (int i : ranges) count *= i;
            return 0;
        }

        @Override
        public int compareTo(IntState o) {
            int c;
            for (int i = 0; i < this.state.length; i++) {
                c = Integer.compare(this.state[i], o.state[i]);
                if (c != 0) return c;
            }
            return 0;
        }
    }

    private static class MinimizingNode<E> extends Node<E> {

        protected MinimizingNode(State<E> state, int value) {
            super(state, value);
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(value, n.value);
        }
    }

    private static class MaximizingNode<E> extends Node<E> {

        protected MaximizingNode(State<E> state, int value) {
            super(state, value);
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(n.value, value);
        }
    }

    private static abstract class Node<E> implements Comparable<Node> {
        protected final State<E> state;
        protected final int value;

        protected Node(State<E> state, int value) {
            this.state = state;
            this.value = value;
        }

        public abstract int compareTo(Node<E> n);

        public boolean sameState(Node<E> n) {
            return this.state.equals(n.state);
        }

        public boolean sameState(State<E> s) {
            return this.state.equals(s);
        }

        public boolean identical(Node<E> node) {
            if (this == node) return true;

            if (value != node.value) return false;
            return state.equals(node.state);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node<E> node = (Node<E>) o;

            return state.equals(node.state);
        }

        @Override
        public int hashCode() {
            int result = state.hashCode();
            result = 31 * result + value;
            return result;
        }
    }

    private static abstract class State<E> {
        protected final E state;

        public State(E state) {
            this.state = state;
        }
        public abstract boolean equals(Object o);
        public abstract int hashCode();
        public abstract boolean inBounds(E bounds);
        public abstract boolean inBoundsStrict(E bounds);
        public abstract String toString();
        public abstract int count(E ranges);
    }

    private interface Transformer<E, N extends Node<E>> {
        Iterable<N> neighbours(N in);
    }

    private interface StateChecker<S extends State, T> {
        boolean isTarget(S in, T target);
    }

    private static abstract class GraphTraverser<E, N extends Node<E>, T> {
        protected final T target;
        protected final E bounds;
        protected final Transformer<E, N> transformer;
        protected final StateChecker<State<E>, T> checker;
        protected final boolean returnFirst;

        protected State<E> start;

        protected int bestSeen;
        protected boolean converged = false;
        protected boolean fullySpanned = false;

        protected GraphTraverser(T target, E bounds, Transformer<E, N> transformer, StateChecker<State<E>, T> checker, boolean returnFirst) {
            this.target = target;
            this.bounds = bounds;
            this.transformer = transformer;
            this.checker = checker;
            this.returnFirst = returnFirst;
        }

        abstract int traverse(State start, int initial);
    }

    private static class MyKattioByWordsI extends MyKattioI {

        public MyKattioByWordsI() {
        }

        public MyKattioByWordsI(InputStream i) {
            super(i);
        }

        public MyKattioByWordsI(OutputStream o) {
            super(o);
        }

        public MyKattioByWordsI(InputStream i, OutputStream o) {
            super(i, o);
        }

        public MyKattioByWordsI(boolean testing) {
            super(testing);
        }

        public MyKattioByWordsI(boolean testing, OutputStream o) {
            super(testing, o);
        }

        public MyKattioByWordsI(boolean testing, String path) {
            super(testing, path);
        }

        public MyKattioByWordsI(boolean testing, String path, OutputStream o) {
            super(testing, path, o);
        }

        public String getWord() {
            return nextToken();
        }

        protected String          line;
        protected StringTokenizer st;

        protected String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) { e.printStackTrace(); }
            return token;
        }
    }

    private static class MyKattioByLinesI extends MyKattioI {

        public MyKattioByLinesI() {}

        public MyKattioByLinesI(InputStream i) {
            super(i);
        }

        public MyKattioByLinesI(OutputStream o) {
            super(o);
        }

        public MyKattioByLinesI(InputStream i, OutputStream o) {
            super(i, o);
        }

        public MyKattioByLinesI(boolean testing) {
            super(testing);
        }

        public MyKattioByLinesI(boolean testing, OutputStream o) {
            super(testing, o);
        }

        public MyKattioByLinesI(boolean testing, String path) {
            super(testing, path);
        }

        public MyKattioByLinesI(boolean testing, String path, OutputStream o) {
            super(testing, path, o);
        }

        public String[] getWords() {
            String s = nextToken();
            return (s != null) ? s.split(" ") : null;
        }

        protected String peekToken() {
            if (token == null) try {
                token = r.readLine();
            } catch (IOException e) { e.printStackTrace(); }
            return token;
        }
    }

    private static abstract class MyKattioI extends PrintWriter {

        public static String path = "./Solutions/src";

        protected BufferedReader r;
        protected String         token;

        public MyKattioI() {
            this(System.in, System.out);
        }
        public MyKattioI(InputStream i) {
            this(i, System.out);
        }
        public MyKattioI(OutputStream o) {
            this(System.in, o);
        }
        public MyKattioI(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public MyKattioI(boolean testing) {
            this(testing, System.out);
        }
        public MyKattioI(boolean testing, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader((testing) ? getFileInputStream() : System.in));
        }

        public MyKattioI(boolean testing, String path) {
            this(testing, path, System.out);
        }
        public MyKattioI(boolean testing, String path, OutputStream o) {
            super(new BufferedOutputStream(o));
            this.path = path;
            r = new BufferedReader(new InputStreamReader((testing) ? getFileInputStream() : System.in));
        }

        private static FileInputStream getFileInputStream() {
            boolean check = true;
            FileInputStream fileIn = null;
            while (check) try {
                JFileChooser chooser = new JFileChooser(path);
                chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
                int n = chooser.showOpenDialog(null);
                if (n == JFileChooser.APPROVE_OPTION) {
                    fileIn = new FileInputStream(chooser.getSelectedFile());
                    check = false;
                }
                if (n == JFileChooser.CANCEL_OPTION) System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileIn;
        }

        @Override
        public void close() {
            super.close();
            try {
                r.close();
            } catch (Exception e) {}
        }

        public boolean hasMore() {
            return peekToken() != null;
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
        }

        public long getLong() {
            return Long.parseLong(nextToken());
        }

        public String getNext() {
            return nextToken();
        }

        protected String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }

        //  Abstract methods

        protected abstract String peekToken();
    }
}
