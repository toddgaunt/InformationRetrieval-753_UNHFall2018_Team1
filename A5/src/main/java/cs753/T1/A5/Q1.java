package cs753.T1.A5;
import java.io.PrintWriter;

public class Q1 {
    public static void main(String[] args) {
        Integer qid = 1;
        Rank D1 = new Rank("D1", 0);
        Rank D2 = new Rank("D2", 1);
        Rank D3 = new Rank("D3", 1);
        Rank D4 = new Rank("D4", 0);
        Rank D5 = new Rank("D5", 1);
        Rank D6 = new Rank("D6", 0);
        Rank D7 = new Rank("D7", 0);
        Rank D8 = new Rank("D8", 0);
        Rank D9 = new Rank("D9", 0);
        Rank D10 = new Rank("D10", 0);
        Rank D11 = new Rank("D11", 0);
        Rank D12 = new Rank("D12", 0);

        Rank rankList1[] = {D1, D2, D3, D4, D5, D6};
        Rank rankList2[] = {D2, D5, D6, D7, D8, D9, D10, D11};
        Rank rankList3[] = {D1, D2, D5};
        Rank rankList4[] = {D1, D2, D8, D10, D12};
        Rank rankingsList[][] = {rankList1, rankList2, rankList3, rankList4};

        String rlf = Rank.rankLibFmtFromRanklist(qid, rankingsList);
        System.out.println(rlf);

        try {
            PrintWriter outfile = new PrintWriter("rankLibQ1.train", "UTF-8");
            outfile.print(rlf);
            outfile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
