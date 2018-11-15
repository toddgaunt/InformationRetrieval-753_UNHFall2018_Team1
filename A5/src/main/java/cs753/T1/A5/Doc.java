package cs753.T1.A5;

import java.util.HashMap;

public class Doc {
    private String id;
    private HashMap<String, Integer> featureRanks;

    public Doc(String id) {
        this.id = id;
        featureRanks = new HashMap<String, Integer>();
    }

    public String id() { return this.id; }

    public boolean equals(Doc that) {
        return this.id == that.id();
    }

    public void addFeatureRank(String featureId, int rank) {
        featureRanks.put(featureId, rank);
    }

    public int getFeatureRank(String featureId) {
        Integer rank = featureRanks.get(featureId);
        return (rank == null) ? 0 : rank.intValue();
    }
}
