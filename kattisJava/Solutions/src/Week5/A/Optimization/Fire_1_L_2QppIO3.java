package Week5.A.Optimization;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class Fire_1_L_2QppIO3 {

    public static void main (String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        try {
            final int wall = '#';
            final int pass = '.';
            final int joe  = 'J';
            final int fire = 'F';

            int ch;

            int r = in.read() - '0';

            ch = in.read();
            while (ch != ' ') {
                r = r*10 + (ch - '0');
                ch = in.read();
            }

            int c = in.read() - '0';
            ch = in.read();
            while (ch != '\n') {
                c = c*10 + (ch - '0');
                ch = in.read();
            }

            final int rm = r-1;
            final int cm = c-1;

            int[][] maze = new int[r][];
            int[] temp;// = new int[c];

            for (int i = 0; i < r; i++) {
                maze[i] = temp = new int[c];
                for (int j = 0; j < c; j++) {
                    temp[j] = in.read();
                }
                in.read();
            }

            // Check for stupidity like all walls or next to edge?
            int goals = 0;

            for (int j = 0; goals == 0 && j < c; j++) {
                if (maze[0][j] == pass) ++goals;
                if (maze[rm][j] == pass) ++goals;
            }

            for (int i = 1; goals == 0 && i < rm; i++) {
                if (maze[i][0] == pass) ++goals;
                if (maze[i][cm] == pass) ++goals;
            }

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
                    out.println("IMPOSSIBLE");
                } else {
                    out.println(1);
                }

                out.flush();
                out.close();

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

                        out.println(l);


                        out.flush();
                        out.close();

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

            out.println("IMPOSSIBLE");

            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.close();

        System.exit(0);
    }

    private static void addFireIf(Queue q, int x, int y, int xM, int yM, int[][] check, int ignore, int pass) { //, int[][] seen, int see
        if (x >= 0 && x < xM && y >= 0 && y < yM && check[y][x] == pass) {
            check[y][x] = ignore;

            q.add(new int[] {x, y});
        }
    }

    private static void addIf(Queue q, int x, int y, int[][] check, int pass, int joe) { //, int[][] seen, int see
        if (check[y][x] == pass) {
            check[y][x] = joe;

            q.add(new int[] {x, y});
        }
    }
}
