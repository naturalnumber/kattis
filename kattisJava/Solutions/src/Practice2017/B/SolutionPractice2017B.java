package Practice2017.B;

import arc.IO.*;

public class SolutionPractice2017B {

    private static final String SS = "Simon says";
    private static final String PATH = "./Solutions/src//Practice2017/B/SampleIn";

    public static void main (String[] args) {
        MyKattio io = new MyKattioByLines(args.length > 0, PATH);
        //System.err.println("hi");

        try {
            int n = io.getInt();
            //System.err.println(n);
            String s;

            for (int i = 0; i < n; i++) {
                s = io.getNext();
                //System.err.println(s);

                if (s.startsWith(SS)) {
                    io.println(s.substring(SS.length()+1));
                    //System.err.println("Listened");
                }
                //else System.err.println("Ignored");
            }
            io.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        io.close();

        System.exit(0);
    }
}
