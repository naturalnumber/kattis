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
import java.util.StringTokenizer;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SolutionPractice2017C1 {

    private static final String PATH = "./Solutions/src/Practice2017/C/SampleIn";

    private static short seen_min = Short.MAX_VALUE;
    private static int n = -1;
    private static short v = -1;
    private static short[] cups = null;
    private static short[][][][][] space = null;
    protected static ArrayList<short[]> in, out;

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByWordsI(args.length > 0, PATH);
        //System.err.println("hi");

        try {
            n = io.getInt();
            //System.err.println("n: "+n);

            cups = new short[5];
            for (int i = 5; i > 5-n; i--) {
                cups[5-i] = (short) io.getInt();
                //System.err.println(i+": "+cups[5-i]);
            }

            v = (short) io.getInt();
            //System.err.println("v: "+v);

            if (cups[0] == v) {
                io.println(0);
                io.flush();
                io.close();
                System.exit(0);
            }

            short c[] = new short[6];
            c[0] = cups[0];


            //int[] temp = new int[5];
            //for (int i = 0; i < n; i++) temp[i] = cups[i];

            space = new short[cups[0]+1][cups[1]+1][cups[2]+1][cups[3]+1][cups[4]+1];

            space[cups[0]][0][0][0][0] = -1;

            short depth = 0;
            ArrayList<short[]> swap;
            in = new ArrayList<>(1000);
            out = new ArrayList<>(1000);
            in.add(c);
            Consumer<short[]> opp = (x) -> span(x, SolutionPractice2017C1.out);

            while (depth < 100) {
                //System.err.println("reached depth: "+depth);
                in.forEach(opp);

                in.clear();

                swap = out;
                out = in;
                in = swap;

                if (in.isEmpty()) break;
                depth++;
            }
            //System.err.println("final depth: "+depth);

            /*int min = Integer.MAX_VALUE, s;

            for (int i1 = 0; i1 <= cups[1]; i1++) {
                for (int i2 = 0; i2 <= cups[2]; i2++) {
                    for (int i3 = 0; i3 <= cups[3]; i3++) {
                        for (int i4 = 0; i4 <= cups[4]; i4++) {
                            System.err.println("checking: "+v+":"+i1+":"+i2+":"+i3+":"+i4);
                            s = space[v][i1][i2][i3][i4];
                            if (s>0 && s<min) min = s;
                        }
                    }
                }
            }

            if (min == Integer.MAX_VALUE) {
                io.println("impossible");
            } else {
                io.println(min);
            }*/
            if (seen_min == Short.MAX_VALUE) {
                io.println("impossible");
            } else {
                io.println(seen_min);
            }

            io.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
    }

    private static void span(short[] c, ArrayList<short[]> outList) {
        short t, s;
        short d, p[];
        //System.err.println("spanning: "+c[0]+":"+c[1]+":"+c[2]+":"+c[3]+":"+c[4]);

        if (c[5] >= seen_min) return;

        for (int i = 0; i < n; i++) if (c[i] > 0) {
            for (int j = 0; j < n; j++) if (j != i && c[j] < cups[j]) {
                //System.err.println("at: "+i+", "+j);
                d = (short) (cups[j] - c[j]);
                if (d > c[i]) d = c[i];
                t = (short) (c[5]+d);
                if (t < seen_min) {
                    p = Arrays.copyOf(c, 6);
                    p[i] -= d;
                    p[j] += d;

                    if (p[0] == v) {
                        seen_min = t;
                        space[p[0]][p[1]][p[2]][p[3]][p[4]] = t;
                        //System.err.println("finding: "+p[0]+":"+p[1]+":"+p[2]+":"+p[3]+":"+p[4]+" "+t);
                    } else {
                        s = space[p[0]][p[1]][p[2]][p[3]][p[4]];
                        //System.err.println("reached: "+p[0]+":"+p[1]+":"+p[2]+":"+p[3]+":"+p[4]+" "+s+" with "+t);
                        if (s == 0 || s > t) {
                            //System.err.println("storing at: "+p[0]+":"+p[1]+":"+p[2]+":"+p[3]+":"+p[4]+" "+s+" with "+t);
                            space[p[0]][p[1]][p[2]][p[3]][p[4]] = t;
                            p[5] = t;
                            outList.add(p);
                        }
                    }
                }

            }
        }
    }

    public static class MyKattioByWordsI extends MyKattioI {

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

    public static class MyKattioByLinesI extends MyKattioI {

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

    public static abstract class MyKattioI extends PrintWriter {

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
