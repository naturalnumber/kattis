package extra.score1_7.Timebomb;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Timebomb {
    private static final String PATH = "./Solutions/src/extra/score1_7/YinandYangStones/SampleIn";

    private static byte[][] values = getValues();

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByLinesI(args.length > 0, PATH);

        try {
            //for (int i = 0; i < values.length; i++) {
            //    System.err.println(Arrays.toString(values[i]));
            //}


            String[] input = new String[5];
            byte[] f = new byte[5];

            for (int i = 0; i < 5; i++) {
                input[i] = io.getNext();
            }

            int n = (input[0].length() + 1) / 4;
            int total = 0;
            byte temp;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i < n-1) f[j] = decodeSection(input[j].substring(4*i, 4*(i+1)-1));
                    else f[j] = decodeSection(input[j].substring(4*i));
                }

                //System.err.println(Arrays.toString(f));

                temp = decode(f);

                if (temp >= 0) {
                    total *= 10;
                    total += temp;
                } else {
                    total = -1;
                    break;
                }
            }

            //System.err.println(total);

            if (total == -1) {
                io.println("BOOM!!");
            } else if (total % 6 == 0) {
                io.println("BEER!!");
            } else {
                io.println("BOOM!!");
            }

            io.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
    }

    private static byte decodeSection(String code) {
        byte decode = 0;

        for (int i = 0; i < code.length(); i++) {
            decode <<= 1;
            if (code.charAt(i) == '*') {
                decode += 1;
            }
        }

        return decode;
    }

    private static byte decode(byte[] f) {
        boolean is;

        for (int i = 0; i < 10; i++) {
            is = true;
            for (int j = 0; j < 5 && is; j++) {
                is = f[j] == values[i][j];
            }
            if (is) return (byte) i;
        }

        return -1;
    }

    private static byte[][] getValues() {
        String[] calibrate = new String[5];
        byte[][] values = new byte[10][5];

        calibrate[0] = "***   * *** *** * * *** *** *** *** ***";
        calibrate[1] = "* *   *   *   * * * *   *     * * * * *";
        calibrate[2] = "* *   * *** *** *** *** ***   * *** ***";
        calibrate[3] = "* *   * *     *   *   * * *   * * *   *";
        calibrate[4] = "***   * *** ***   * *** ***   * *** ***";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (i < 9) values[i][j] = decodeSection(calibrate[j].substring(4*i, 4*(i+1)-1));
                else values[i][j] = decodeSection(calibrate[j].substring(4*i));
            }
        }

        return values;
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
