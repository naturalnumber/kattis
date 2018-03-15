package Week5.A.Optimization;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Fire_1_L_2QppIO {

    public static void main (String[] args) {
        MyKattioI io = new MyKattioByWordsI();

        try {
            final char wall = '#';
            final char pass = '.';
            final char joe  = 'J';
            final char fire = 'F';
            //final int d = Integer.MIN_VALUE;

            final int r = io.getInt();

            final int c = io.getInt();

            //int mrc = Math.max(r, c);

            //, i, j;s,

            char ch;

            /*for (int i = 0; i < r; i++) {
                maze[i] = io.getNext().toCharArray();
            }//*/

            final int rm = r-1;
            final int cm = c-1;

            // Check for stupidity like all walls or next to edge?
            int goals = 0;


            char[][] maze = new char[r][];
            maze[0] = io.getNext().toCharArray();

            for (int j = 0; goals == 0 && j < c; j++) {
                if (maze[0][j] == pass) goals++;
            }

            for (int i = 1; i < rm; i++) {
                maze[i] = io.getNext().toCharArray();

                if (goals == 0) {
                    if (maze[i][0] == pass) goals++;
                    if (maze[i][cm] == pass) goals++;
                }

            }

            maze[rm] = io.getNext().toCharArray();

            for (int j = 0; goals == 0 && j < c; j++) {
                if (maze[rm][j] == pass) goals++;
            }

            /*
            for (int j = 0; goals == 0 && j < c; j++) {
                if (maze[0][j] == pass) ++goals;
                if (maze[rm][j] == pass) ++goals;
            }

            for (int i = 1; goals == 0 && i < rm; i++) {
                if (maze[i][0] == pass) ++goals;
                if (maze[i][cm] == pass) ++goals;
            }//*/

            if (goals == 0) {
                goals = 0;

                for (int j = 0; goals == 0 && j < c; j++) {
                    if (maze[0][j] == joe) ++goals;
                    if (maze[rm][j] == joe) ++goals;
                }

                for (int i = 1; goals == 0 && i < rm; i++) {
                    if (maze[i][0] == joe) ++goals;
                    if (maze[i][cm] == joe) ++goals;
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

            Queue<int[]> qf = new ArrayDeque<>();
            Queue<int[]> qs = new ArrayDeque<>();


            int[] at = null;


            // Is there a fast way to cut out dead-ends

            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) {
                ch = maze[i][j];
                if (ch == fire) {
                    qf.add(new int[] {j, i});
                } else if (ch == joe) {
                    at = new int[] {j, i};
                }
            }

            qs.add(at);
            int x, y, sN = 1, l = 1, fN = qf.size();

            while (sN > 0) {
                for (int i = 0; i < fN; i++) {
                    at = qf.remove();

                    // Track goals, if they go to zero, end? ****

                    x = at[0];
                    y = at[1];

                    addFireIf(qf, x, y + 1, c, r, maze, wall, pass);
                    addFireIf(qf, x, y - 1, c, r, maze, wall, pass);
                    addFireIf(qf, x + 1, y, c, r, maze, wall, pass);
                    addFireIf(qf, x - 1, y, c, r, maze, wall, pass);
                }
                fN = qf.size();

                for (int i = 0; i < sN; i++) {
                    at = qs.remove();

                    x = at[0];
                    y = at[1];

                    // Find a way to put this in the add method?
                    if (x == 0 || x == cm || y == 0 || y == rm) {

                        //s = l - d;

                        io.println(l);


                        io.flush();
                        io.close();

                        System.exit(0);
                    }//*/

                    addIf(qs, x, y+1, maze, pass, joe);
                    addIf(qs, x, y-1, maze, pass, joe);
                    addIf(qs, x+1, y, maze, pass, joe);
                    addIf(qs, x-1, y, maze, pass, joe);
                }
                sN = qs.size();

                l++;
            }


            io.println("IMPOSSIBLE");


            io.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
    }

    private static void addFireIf(Queue q, int x, int y, int xM, int yM, char[][] check, char ignore, char pass) { //, int[][] seen, int see
        if (x >= 0 && x < xM && y >= 0 && y < yM && check[y][x] == pass) {
            check[y][x] = ignore;

            q.add(new int[] {x, y});
        }
    }

    private static void addIf(Queue q, int x, int y, char[][] check, char pass, char joe) { //, int[][] seen, int see
        if (check[y][x] == pass) {
            check[y][x] = joe;

            q.add(new int[] {x, y});
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
