package Week6.A;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class FlowFree_n1 {

    private static final int GRID_SIZE = 4;
    private static final int NUM_POSSIBLE_DIRECTIONS = 4;
    private static final int[] dx = {1, 0, -1,  0};
    private static final int[] dy = {0, 1,  0, -1};

    public static void main (String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        try {
            final int blank = 'W';
            final int first = 'R';
            final int second = 'G';
            final int third = 'B';
            final int fourth = 'Y';
            final int path = 8;

            int ch;

            int[][] puzzle = new int[4][4];
            int[] temp;// = new int[c];
            int nl = 3;


            for (int i = 0; i < 4; i++) {
                temp = puzzle[i];
                for (int j = 0; j < 4; j++) {
                    ch = in.read();
                    switch (ch) {
                        case blank:
                            temp[j] = 0;
                            break;
                        case first:
                            temp[j] = 1;
                            break;
                        case second:
                            temp[j] = 2;
                            break;
                        case third:
                            temp[j] = 3;
                            break;
                        case fourth:
                            temp[j] = 4;
                            nl = 4;
                            break;
                    }
                }
                in.read();
            }

            for (int i = 0; i < 4; i++) {
                System.out.println(Arrays.toString(puzzle[i]));
            }


            out.println("solvable");
            out.println("not solvable");

            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.close();

        System.exit(0);
    }

    private static boolean isSolved(int[][] init, int[][] p, int nl) {
        for (int l = 1; l <= nl; l++) {

        }
        return false;
    }

    private static boolean rCheck(int[][] init, int[][] p, int nl) {
        return false;
    }
}
