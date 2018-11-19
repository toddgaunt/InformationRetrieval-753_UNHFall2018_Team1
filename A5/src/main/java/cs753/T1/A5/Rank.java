package cs753.T1.A5;

import java.util.HashMap;

public class Rank {
	String id;
	Integer relevance;
	
	Rank(String id, Integer relevance) {
		this.id = id;
		this.relevance = relevance;
	}
	
	public static String rankLibFmtFromRanklist(Integer queryId, Rank[][] rankList) {
		Integer i, j;
		HashMap<String, FeatureVector> featureVectors = new HashMap<String, FeatureVector>();
		for (i = 0; i < rankList.length; ++i) {
			for (j = 0; j < rankList[i].length; ++j) {
				FeatureVector tmp = featureVectors.get(rankList[i][j].id);
				if (null == tmp) {
					featureVectors.put(rankList[i][j].id, new FeatureVector(rankList[i][j].id, rankList.length));
					tmp = featureVectors.get(rankList[i][j].id);
				}
				tmp.rank[i] = (1.0f + 1.0f)/((float)(j + 1));
				tmp.relevance = rankList[i][j].relevance;
			}
		}

		// Create feature vector in RankLib format
		String rankLibFmt = "";
		for (FeatureVector entry : featureVectors.values()) {
			rankLibFmt += entry.relevance;  // Relevance
			rankLibFmt += " " + queryId;
			for (i = 0; i < entry.rank.length; ++i) {
				Float rank = 0.0f;
				if (null != entry.rank[i])
					rank = entry.rank[i];
				rankLibFmt += " " + (i + 1) + ":" + rank;
			}
			rankLibFmt += " # " + entry.id + "\n";
		}

		// The returned string is a list of feature vectors compatible with rankLib
		return rankLibFmt;
	}
	
}