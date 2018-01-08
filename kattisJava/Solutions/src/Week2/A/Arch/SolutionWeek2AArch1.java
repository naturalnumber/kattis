package Week2.A.Arch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SolutionWeek2AArch1 {

    private static final String PATH = "./Solutions/src/Week2/A/SampleIn";

    private static final int MAX_STOCK = 100000;
    private static final int START_MONEY = 100;

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByLinesI(args.length > 0, PATH);
        //System.err.println("hi");

        try {
            int d = io.getInt();
            //System.err.println("d: "+d);

            int[] prices = new int[d];

            for (int i = d; i > 0; i--) {
                prices[i-1] = io.getInt();
                //System.err.println("p"+(-(i))+": "+prices[i]);
            }

            //System.err.println(Arrays.toString(prices));

            int stocks = 0;
            int money = START_MONEY;
            int d0 = d;
            int d1 = -1;

            while (prices[d0-1] > START_MONEY && d0 > 1) {
                d0--;
            }
            //System.err.println("init d0="+d0+" at price "+prices[d0-1]);


            do {
                //  find next min
                while (d0 > 1) {
                    if (prices[d0-1] > prices[d0-2]) d0--;
                    else break;
                }
                //  No next min
                if (d0 <= 1) {
                    io.println(money);
                    io.flush();
                    System.exit(0);
                }
                //System.err.println("min at d0="+d0+" at price "+prices[d0-1]);

                d1 = d0;

                //  find next max
                while (d1 > 1) {
                    if (prices[d1-1] < prices[d1-2]) d1--;
                    else break;
                }
                //  No next max
                if (d1 < 1) {
                    io.println(money);
                    io.flush();
                    System.exit(0);
                }
                //System.err.println("max at d1="+d1+" at price "+prices[d1-1]);


                //System.err.print("money from "+money);
                //  Buy
                stocks = money/prices[d0-1];
                money -= stocks*prices[d0-1];
                //  Sell
                money += stocks*prices[d1-1];
                stocks = 0;
                //System.err.print(" to "+money);

                d0 = d1;

                //System.in.read();
            } while (d1 > 1);

            io.println(money); // Impossible?

            io.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
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
