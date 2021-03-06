package Week5.A.Optimization;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Fire_1_Linked {

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByWordsI();

        try {
            final char wall = '#';
            final char pass = '.';
            final char joe  = 'J';
            final char fire = 'F';
            final int d = Integer.MIN_VALUE;

            int r = io.getInt();

            int c = io.getInt();

            int mrc = Math.max(r, c);
            int rm = r-1;
            int cm = c-1;

            int x, y, s;

            char ch;

            char[][] maze = new char[r][];

            for (int i = 0; i < r; i++) {
                maze[i] = io.getNext().toCharArray();
            }

            // Check for stupidity like all walls or next to edge?
            int goals = 0;

            for (int j = 0; goals == 0 && j < c; j++) {
                if (maze[0][j] == pass) goals++;
                if (maze[rm][j] == pass) goals++;
            }

            for (int i = 1; goals == 0 && i < rm; i++) {
                if (maze[i][0] == pass) goals++;
                if (maze[i][cm] == pass) goals++;
            }

            if (goals == 0) {
                goals = 0;

                for (int j = 0; goals == 0 && j < c; j++) {
                    if (maze[0][j] == joe) goals++;
                    if (maze[rm][j] == joe) goals++;
                }

                for (int i = 1; goals == 0 && i < rm; i++) {
                    if (maze[i][0] == joe) goals++;
                    if (maze[i][cm] == joe) goals++;
                }

                if (goals == 0) {
                    io.println("IMPOSSIBLE");
                } else {
                    io.println(1);
                }

                io.flush();
                io.close();

                System.exit(0);
            }

            Queue<Coordinate> qf = new LinkedList<>();
            Queue<Coordinate> qs = new LinkedList<>();
            Queue<Coordinate> qf2 = new LinkedList<>();
            Queue<Coordinate> qs2 = new LinkedList<>();
            Queue<Coordinate> qt;


            Coordinate at, start = null;
            int l;

            //int[][] fires = new int[r][c];

            // Is there a fast way to cut out dead-ends

            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) {
                ch = maze[i][j];
                if (ch == fire) {
                    //fires[i][j] = d;

                    qf.add(new Coordinate(j, i));
                } else if (ch == joe) {
                    start = new Coordinate(j, i);
                }
            }

            int[][] times = new int[r][c];
            qs.add(start);
            times[start.y][start.x] = d;
            l = d+1;

            while (!qs.isEmpty()) {
                while (!qf.isEmpty()) {
                    at = qf.remove();

                    // Track goals, if they go to zero, end? ****

                    x = at.x;
                    y = at.y;

                    addFireIf(qf, qf2, x, y + 1, c, r, maze, wall, times);
                    addFireIf(qf, qf2, x, y - 1, c, r, maze, wall, times);
                    addFireIf(qf, qf2, x + 1, y, c, r, maze, wall, times);
                    addFireIf(qf, qf2, x - 1, y, c, r, maze, wall, times);
                }

                while (!qs.isEmpty()) {
                    at = qs.remove();

                    x = at.x;
                    y = at.y;

                    // Find a way to put this in the add method?
                    if (x == 0 || x == cm || y == 0 || y == rm) {

                        s = l - d;

                        io.println(s);


                        io.flush();
                        io.close();

                        System.exit(0);
                    }//*/

                    addIf(qs, qs2, x, y+1, c, r, maze, wall, times, l);
                    addIf(qs, qs2, x, y-1, c, r, maze, wall, times, l);
                    addIf(qs, qs2, x+1, y, c, r, maze, wall, times, l);
                    addIf(qs, qs2, x-1, y, c, r, maze, wall, times, l);
                }

                qt = qf;
                qf = qf2;
                qf2 = qt;

                qt = qs;
                qs = qs2;
                qs2 = qt;

                l++;
            }

            //for (int i = 0; i < r; i++) {
            //    System.err.println(Arrays.toString(times[i]));
            //}

            //int min = 0;

            //for (int j = 0; j < c; j++) {
            //    min = tmin(min, times[0][j], times[rm][j]);
            //}

            //for (int i = 1; i < rm; i++) {
            //    min = tmin(min, times[i][0], times[i][cm]);
            //}

            //s = min - d + 1;

            //if (s > 0)
            //    io.println(s);
            //else
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

    private static void addFireIf(Queue q, Queue qn, int x, int y, int xM, int yM, char[][] check, char ignore, int[][] levels) { //, int[][] seen, int see
        if (x >= 0 && x < xM && y >= 0 && y < yM && check[y][x] != ignore && levels[y][x] == 0) {
            check[y][x] = ignore;

            //System.err.println("fire x: "+x+" y: "+y);
            //if (seen[y][x] < see) {
            qn.add(new Coordinate(x, y));
            //    seen[y][x]++;
            //}
        }
    }

    private static void addIf(Queue q, Queue qn, int x, int y, int xM, int yM, char[][] check, char ignore, int[][] levels, int l) { //, int[][] seen, int see
        if (x >= 0 && x < xM && y >= 0 && y < yM && check[y][x] != ignore && levels[y][x] == 0) {
            levels[y][x] = l;

            //System.err.println("move x: "+x+" y: "+y);
            //if (seen[y][x] < see) {
            qn.add(new Coordinate(x, y));
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
