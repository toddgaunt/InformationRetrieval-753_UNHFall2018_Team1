package cs753.T1.A5;

import java.util.HashMap;
public class A5 {

    public static void main(String[] args) {
        // Create docs from Question 1
        HashMap<String, Doc> docs = new HashMap<String, Doc>();
        for (int i = 0; i < 12; i++) {
            String id = "D" + (i + 1);
            docs.put(id, new Doc(id));
        }

        // Create rankings from Question 1
        String qid = "1"; // query id.
        String rankList1[] = {"D1", "D2", "D3", "D4", "D5", "D6"};
        String rankList2[] = {"D2", "D5", "D6", "D7", "D8", "D9", "D10", "D11"};
        String rankList3[] = {"D1", "D2", "D5"};
        String rankList4[] = {"D1", "D2", "D8", "D10", "D12"};
        String rankingslist[][] = {rankList1, rankList2, rankList3, rankList4};

        // Store ranking info from each feature in each Doc object
        // Iterate through each feature ranking list
        for (int i = 0; i < rankingslist.length; i++) {
            String featureId = "" + (i + 1);

            // Update each Doc with its rank from a feature
            for (int j = 0; j < rankingslist[i].length; j++) {
                String docId = rankingslist[i][j];
                int rank = j + 1;
                docs.get(docId).addFeatureRank(featureId, rank);
            }
        }

        // Create feature vector in RankLib format
        String featureVector = "";
        for (HashMap.Entry<String, Doc> entry : docs.entrySet()) {
            Doc d = entry.getValue();
            featureVector += "?"; // TODO what is target field in RankLib file format?
            featureVector += " 1"; // qid (query id)
            featureVector += " 1:" + d.getFeatureRank("1");
            featureVector += " 2:" + d.getFeatureRank("2");
            featureVector += " 3:" + d.getFeatureRank("3");
            featureVector += " 4:" + d.getFeatureRank("4");
            featureVector += " # " + d.id() + '\n'; // info
        }

        // Print the feature vector in RankLib format
        System.out.println(featureVector);

        // TODO execute RankLib with our feature vector to train using Coordinate Ascent optimizing MAP (use --qrels)
    }
}
