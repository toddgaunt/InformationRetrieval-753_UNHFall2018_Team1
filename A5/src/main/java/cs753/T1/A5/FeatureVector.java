package cs753.T1.A5;

public class FeatureVector {
	String id;
	Integer relevance;
	Float[] rank;
	
	FeatureVector(String id, Integer numberOfFeatures) {
		this.id = id;
		this.relevance = 0;
		this.rank = new Float[numberOfFeatures];
	}
}
