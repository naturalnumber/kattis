package Week5.A.Optimization;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Fire_2_QLess {

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
            final int rcm = ((r > c) ? r : c) << 1;

            int goals = 0;

            int[][] maze = new int[r][];
            int[] temp;// = new int[c];


            maze[0] = temp = new int[c];
            for (int j = 0; j < c; j++) {
                ch = in.read();
                temp[j] = ch;
                if (ch == pass) ++goals;
            }
            in.read();

            for (int i = 1; i < rm; i++) {
                maze[i] = temp = new int[c];
                for (int j = 0; j < c; j++) {
                    temp[j] = in.read();
                }
                in.read();
            }

            maze[rm] = temp = new int[c];
            for (int j = 0; j < c; j++) {
                ch = in.read();
                temp[j] = ch;
                if (ch == pass) ++goals;
            }

            // Check for stupidity like all walls or next to edge?

            for (int i = 1; goals == 0 && i < rm; i++) {
                if (maze[i][0] == pass) ++goals;
                if (maze[i][cm] == pass) ++goals;
            }

            if (goals == 0) {

                for (int j = 0; j < c; j++) if (maze[0][j] == joe || maze[rm][j] == joe) {
                    out.println(1);

                    out.flush();
                    out.close();

                    System.exit(0);
                }

                for (int i = 1; i < rm; i++) if (maze[i][0] == joe || maze[i][cm] == joe) {
                    out.println(1);

                    out.flush();
                    out.close();

                    System.exit(0);
                }

                out.println("IMPOSSIBLE");

                out.flush();
                out.close();

                System.exit(0);
            }

            int[][] qf = new int[rcm][];//Queue<int[]> qf = new ArrayDeque<>();
            int[][] qs = new int[rcm][];//Queue<int[]> qs = new ArrayDeque<>();
            int[][] qt = new int[rcm][], qT;
            int[] at = null;
            int fN = 0;




            // Is there a fast way to cut out dead-ends

            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) {
                ch = maze[i][j];
                if (ch == fire) {
                    System.err.println("New f: "+j+" "+i);
                    qf[fN++] = new int[] {j, i}; //qf.add(new int[] {j, i});
                } else if (ch == joe) {
                    qs[0] = new int[] {j, i};
                }
            }

            //qs.add(at);
            int x, y, l = 1, tN = 0, sN = 1;//, sN = 1, fN = qf.size();

            while (sN > 0) {
                for (int i = 0; i < fN; i++) {
                    at = qf[i];//qf.remove();

                    // Track goals, if they go to zero, end? ****

                    x = at[0];
                    y = at[1];

                    System.err.println("See f: "+x+" "+y);

                    tN = addFireIf(qt, tN, x, y + 1, c, r, maze, wall, pass);
                    tN = addFireIf(qt, tN, x, y - 1, c, r, maze, wall, pass);
                    tN = addFireIf(qt, tN, x + 1, y, c, r, maze, wall, pass);
                    tN = addFireIf(qt, tN, x - 1, y, c, r, maze, wall, pass);
                }
                fN = tN;//qf.size();
                tN = 0;
                qT = qf;
                qf = qt;
                qt = qT;

                for (int i = 0; i < sN; i++) {
                    at = qs[i];//.remove();

                    x = at[0];
                    y = at[1];

                    System.err.println("See s: "+x+" "+y);

                    // Find a way to put this in the add method?
                    if (x == 0 || x == cm || y == 0 || y == rm) {

                        //s = l - d;

                        out.println(l);


                        out.flush();
                        out.close();

                        System.exit(0);
                    }//*/

                    tN = addIf(qt, tN, x, y+1, maze, pass, joe);
                    tN = addIf(qt, tN, x, y-1, maze, pass, joe);
                    tN = addIf(qt, tN, x+1, y, maze, pass, joe);
                    tN = addIf(qt, tN, x-1, y, maze, pass, joe);
                }
                sN = tN;// = qs.size();
                tN = 0;
                qT = qs;
                qs = qt;
                qt = qT;

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

    private static int addFireIf(int[][] q, int t, int x, int y, int xM, int yM, int[][] check, int ignore, int pass) { //, int[][] seen, int see
        if (x >= 0 && x < xM && y >= 0 && y < yM && check[y][x] == pass) {
            check[y][x] = ignore;

            //q.add(new int[] {x, y});
            q[t] = new int[] {x, y};

            return t+1;
        }

        return t;
    }

    private static int addIf(int[][] q, int t, int x, int y, int[][] check, int pass, int joe) { //, int[][] seen, int see
        if (check[y][x] == pass) {
            check[y][x] = joe;

            //q.add(new int[] {x, y});
            q[t] = new int[] {x, y};

            return t+1;
        }

        return t;
    }
}
