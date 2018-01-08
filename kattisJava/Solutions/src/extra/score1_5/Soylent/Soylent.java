package extra.score1_5.Soylent;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Soylent {
    private static final String PATH = "./Solutions/src/extra/score1_5/Tri/SampleIn";

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByLinesI(args.length > 0, PATH);

        try {
            int n = io.getInt();

            for (int i = 0; i < n; i++) {
                io.println((io.getInt()+399)/400);
            }

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
