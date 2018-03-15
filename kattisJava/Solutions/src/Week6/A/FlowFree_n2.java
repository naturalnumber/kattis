package Week6.A;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class FlowFree_n2 {

    private static final int   SIZE        = 4;
    private static final int   SIZE2       = SIZE * SIZE;
    private static final int   COLOURS_MAX = 4;
    private static final int   NUM_DELTAS  = 4;
    private static final int[] DELTA_X     = {1, 0, -1, 0};
    private static final int[] DELTA_Y     = {0, 1, 0, -1};

    //private static boolean[][]     seen  = new boolean[SIZE][SIZE];
    private static int[]     colours  = {1, 2, 3, 4};
    private static int[][]   puzzle   = new int[SIZE][SIZE];
    private static int[][][] ends     = new int[COLOURS_MAX][2][2];
    private static boolean   solved   = false;
    private static int       blank    = 0;
    private static int       nColours = 3;

    public static void main(String[] args) {
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter    out = new PrintWriter(new BufferedOutputStream(System.out));

        try {
            final int ignore = 'W';
            final int first  = 'R';
            final int second = 'G';
            final int third  = 'B';
            final int fourth = 'Y';
            //final int length = 8;

            int   ch, c;
            int[] temp, seenEnds;

            //int[][] puzzle = new int[4][4];
            //int nl = 3;


            seenEnds = new int[COLOURS_MAX];

            for (int i = 0; i < SIZE; i++) {
                temp = puzzle[i];
                for (int j = 0; j < SIZE; j++) {
                    ch = in.read();
                    switch (ch) {
                        case ignore:
                            temp[j] = blank;
                            break;
                        case first:
                            temp[j] = 1;
                            ends[0][seenEnds[0]++] = new int[]{j, i};
                            break;
                        case second:
                            temp[j] = 2;
                            ends[1][seenEnds[1]++] = new int[]{j, i};
                            break;
                        case third:
                            temp[j] = 3;
                            ends[2][seenEnds[2]++] = new int[]{j, i};
                            break;
                        case fourth:
                            temp[j] = 4;
                            ends[3][seenEnds[3]++] = new int[]{j, i};
                            nColours = 4;
                            break;
                    }
                }
                in.read();
            }

            //for (int i = 0; i < SIZE; i++) System.err.println(Arrays.toString(initial[i]));
            //for (c = 0; c < nColours; c++) for (int i = 0; i < 2; i++) System.err.println(Arrays.toString(ends[c][i]));

            search(0, 0);

            if (solved) {
                out.println("solvable");
            } else {
                out.println("not solvable");
            }

            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.close();

        System.exit(0);
    }

    private static void search(int x, int y) {

        if (y == 4) {
            if (x == 3) solved = isSolved();
            else search(x + 1, 0);
            return;
        } else if (puzzle[y][x] == blank) {
            for (int c = 0; c < nColours; ++c) {
                puzzle[y][x] = colours[c];
                search(x, y + 1);
                if (solved) return;
                puzzle[y][x] = blank;
            }
        } else search(x, y + 1);
    }

    private static int length(boolean[][] seen, int c, int startx, int starty, int endx, int endy) {

        if (startx == endx && starty == endy) return 1;

        seen[starty][startx] = true;

        int newx, newy, length = Integer.MIN_VALUE;

        for (int d = 0; d < NUM_DELTAS; ++d) {
            newx = startx + DELTA_X[d];
            newy = starty + DELTA_Y[d];
            if (0 <= newx && newx < SIZE && 0 <= newy && newy < SIZE &&
                puzzle[newy][newx] == c && !seen[newy][newx]) {
                length = Math.max(length, length(seen, c, newx, newy, endx, endy) + 1);
            }
        }

        seen[starty][startx] = false;

        return length;
    }

    private static boolean isSolved() {

        int length = 0, x, y;
        for (int c = 0; c < nColours; ++c) {
            x = ends[c][0][0];
            y = ends[c][0][1];
            length += length(new boolean[SIZE][SIZE], puzzle[y][x], x, y, ends[c][1][0],
                             ends[c][1][1]);
        }

        return length == SIZE2;
    }
}
