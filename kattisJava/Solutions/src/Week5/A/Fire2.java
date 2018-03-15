package Week5.A;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Fire2 {
    private static final String PATH = "./Solutions/src/";

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByWordsI(args.length > 0, PATH);

        try {
            final char wall = '#';
            final char pass = '.';
            final char joe  = 'J';
            final char fire = 'F';
            final int d = Integer.MIN_VALUE;

            int r = io.getInt();
            int rp = r+1;
            int rpp = r+2;

            int c = io.getInt();
            int cp = c+1;
            int cpp = c+2;

            int s;

            char ch;

            char[][] maze = new char[rpp][];
            char[] temp;

            for (int i = 1; i < rp; i++) {
                temp = new char[cpp];
                System.arraycopy(io.getNext().toCharArray(), 0, temp, 1, c);
                temp[0] = temp[cp] = wall;

                maze[i] = temp;
            }

            temp = new char[cpp];
            Arrays.fill(temp, wall);
            maze[0] = maze[rp] = temp;


            // Check for stupidity like all walls or next to edge?

            Queue<Coordinate> q = new ArrayDeque<>(Math.max(r, c));
            Coordinate at, start = null;
            int l;

            int[][] fires = new int[rpp][cpp];

            for (int i = 1; i < rp; i++) for (int j = 1; j < cp; j++) {
                ch = maze[i][j];
                if (ch == fire) {
                    fires[i][j] = d;

                    q.add(new Coordinate(j, i));
                } else if (ch == joe) {
                    start = new Coordinate(j, i);
                }
            }

            while (!q.isEmpty()) {
                at = q.remove();
                l = fires[at.y][at.x]+1;

                addIf(q, at.x, at.y+1, maze, wall, fires, l);
                addIf(q, at.x, at.y-1, maze, wall, fires, l);
                addIf(q, at.x+1, at.y, maze, wall, fires, l);
                addIf(q, at.x-1, at.y, maze, wall, fires, l);
            }

            int[][] times = new int[rpp][cpp];

            q.add(start);

            times[start.y][start.x] = d;

            while (!q.isEmpty()) {
                at = q.remove();
                l = times[at.y][at.x]+1;

                addIf(q, at.x, at.y+1, maze, wall, fires, l, times); //, seen, 2
                addIf(q, at.x, at.y-1, maze, wall, fires, l, times);
                addIf(q, at.x+1, at.y, maze, wall, fires, l, times);
                addIf(q, at.x-1, at.y, maze, wall, fires, l, times);
            }

            //for (int i = 0; i < r; i++) {
            //    System.err.println(Arrays.toString(times[i]));
            //}

            int min = 0;
            //int rm = r-1;
            //int cm = c-1;

            for (int j = 1; j < cp; j++) {
                min = tmin(min, times[1][j], times[rp][j]);
            }

            for (int i = 2; i < r; i++) {
                min = tmin(min, times[i][1], times[i][cp]);
            }

            s = min - d + 1;

            if (s > 0)
                io.println(s);
            else
                io.println("IMPOSSIBLE");


            io.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
    }

    private static int tmin(int x, int y, int z) {
        return (x < y) ? (x < z) ? x : z : (y < z) ? y : z;
    }

    private static void addIf(Queue q, int x, int y, char[][] check, char ignore, int[][] levels, int l) { //, int[][] seen, int see
        if (check[y][x] != ignore && levels[y][x] > l) {
            levels[y][x] = l;

            //System.err.println("fire x: "+x+" y: "+y);
            //if (seen[y][x] < see) {
            q.add(new Coordinate(x, y));
            //    seen[y][x]++;
            //}
        }
    }

    private static void addIf(Queue q, int x, int y, char[][] check, char ignore, int[][] levels, int l, int[][] places) { //, int[][] seen, int see
        if (check[y][x] != ignore && levels[y][x] > l) {
            places[y][x] = l;

            //System.err.println("move x: "+x+" y: "+y);
            //if (seen[y][x] < see) {
            q.add(new Coordinate(x, y));
            //    seen[y][x]++;
            //}
        }
    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
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
