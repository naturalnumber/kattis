package Week5.A.Optimization;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class Fire_1_L_NB2 {

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

            //Queue<int[]> qf = new ArrayDeque<>();
            //Queue<int[]> qs = new ArrayDeque<>();

            int[] qfx = new int[2000];
            int[] qfy = new int[2000];
            int qfi = 0;

            int[] qsx = new int[2000];
            int[] qsy = new int[2000];
            int qsi = 1;

            int[] qtx = new int[2000];
            int[] qty = new int[2000];
            int qti = 0;

            //int[] at = null;



            // Is there a fast way to cut out dead-ends

            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) {
                ch = maze[i][j];
                if (ch == fire) {
                    qfx[qfi] = j;
                    qfy[qfi] = i;
                    qfi++;
                    //qf.add(new int[] {j, i});
                } else if (ch == joe) {
                    qsx[0] = j;
                    qsy[0] = i;
                    //at = new int[] {j, i};
                }
            }

            //qs.add(at);
            int x, y, sN = 1, l = 1;//, fN = qf.size();
            //int atx = 0, aty = 0;

            while (sN > 0) {
                qti = 0;
                for (int i = 0; i < qfi; i++) {//fN
                    //at = qf.remove();

                    // Track goals, if they go to zero, end? ****

                    x = qfx[i];//at[0];
                    y = qfy[i];//at[1];

                    qti = addFireIf(qtx, qty, qti, x, y + 1, c, r, maze, wall, pass);
                    qti = addFireIf(qtx, qty, qti, x, y - 1, c, r, maze, wall, pass);
                    qti = addFireIf(qtx, qty, qti, x + 1, y, c, r, maze, wall, pass);
                    qti = addFireIf(qtx, qty, qti, x - 1, y, c, r, maze, wall, pass);
                }
                //fN = qf.size();
                qfx = qtx;
                qfy = qty;
                qfi = qti;

                qti = 0;
                for (int i = 0; i < qsi; i++) {
                    //at = qs.remove();

                    x = qsx[i];//at[0];
                    y = qsy[i];//at[1];

                    // Find a way to put this in the add method?
                    if (x == 0 || x == cm || y == 0 || y == rm) {

                        //s = l - d;

                        out.println(l+1);


                        out.flush();
                        out.close();

                        System.exit(0);
                    }//*/

                    qti = addIf(qtx, qty, qti, x, y+1, maze, pass, joe);
                    qti = addIf(qtx, qty, qti, x, y-1, maze, pass, joe);
                    qti = addIf(qtx, qty, qti, x+1, y, maze, pass, joe);
                    qti = addIf(qtx, qty, qti, x-1, y, maze, pass, joe);
                }
                //sN = qs.size();
                qsx = qtx;
                qsy = qty;
                qsi = qti;

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

    private static int addFireIf(int[] qtx, int[] qty, int qti, int x, int y, int xM, int yM, int[][] check, int ignore, int pass) { //, int[][] seen, int see
        if (x >= 0 && x < xM && y >= 0 && y < yM && check[y][x] == pass) {
            check[y][x] = ignore;

            qtx[qti] = x;
            qty[qti] = y;
            return qti + 1;
        }

        return qti;
    }

    private static int addIf(int[] qtx, int[] qty, int qti, int x, int y, int[][] check, int pass, int joe) { //, int[][] seen, int see
        if (check[y][x] == pass) {
            check[y][x] = joe;

            qtx[qti] = x;
            qty[qti] = y;
            return qti + 1;
        }

        return qti;
    }
}
