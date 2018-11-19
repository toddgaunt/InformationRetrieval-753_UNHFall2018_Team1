package cs753.T1.A5;

import java.util.HashMap;

public class Rank {
	Integer relevance;
	String id;
	
	public static String rankLibFmtFromRanklist(Integer queryId, Rank[][] rankList) {
		Integer i, j;
		HashMap<String, FeatureVector> featureVectors = new HashMap<String, FeatureVector>();
		for (i = 0; i < rankList.length; ++i) {
			for (j = 0; j < rankList[i].length; ++j) {
				FeatureVector tmp = featureVectors.get(rankList[i][j].id);
				tmp.rank.set(i, 1.0f/((float)j));
				tmp.relevance = 1; //TODO(todd): This is just a placeholder
			}
		}

		// Create feature vector in RankLib format
		String rankLibFmt = "";
		for (FeatureVector entry : featureVectors.values()) {
			rankLibFmt += entry.relevance;  // Relevance
			rankLibFmt += " " + queryId;
			for (i = 0; i < entry.rank.size(); ++i) {
				rankLibFmt += " " + i + ": " + entry.rank.get(i);
			}
			rankLibFmt += " # " + entry.id + "\n";
		}

		// The returned string is a list of feature vectors compatible with rankLib
		return rankLibFmt;
	}
	
}