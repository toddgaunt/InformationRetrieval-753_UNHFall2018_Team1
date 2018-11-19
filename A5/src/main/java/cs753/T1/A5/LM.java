package cs753.T1.A5;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.LMSimilarity;

public class LM {
	public static LMSimilarity U_L(final long number_of_terms) {
		return new LMSimilarity() {
			@Override
			public String getName() {
				return "LM-U";
			}

			@Override
			protected float score(BasicStats stats, float freq, float docLen) {
				long tf = stats.getTotalTermFreq();
				return (freq + 1) / (tf + number_of_terms);
			}
		};
	}
	public static LMSimilarity U_JM() {
		return new LMSimilarity() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "U-JM";
			}

			@Override
			protected float score(BasicStats stats, float freq, float docLen) {
				// TODO Auto-generated method stub
				long lambda = (long) 0.9;
				long tf = stats.getTotalTermFreq();
				long pt = (long) (tf / (Math.log(stats.getNumberOfDocuments()) * Math.log(tf)));
				return (lambda * (freq/tf)) + ((1-lambda)*pt);
			}
		};
	}

    public static LMSimilarity U_DS() {
        return new LMSimilarity() {

            @Override
            public String getName() {
                return "U-DS";
            }

            @Override
            protected float score(BasicStats stats, float freq, float docLen) {
                long µ = 1000;
                long tf = stats.getTotalTermFreq();
                long D = stats.getNumberOfDocuments();
                long pt = (long) (tf / (Math.log(stats.getNumberOfDocuments()) * Math.log(tf)));
                return (D/(D+µ))*(tf/D) + (µ/(D+µ))*pt;
            }
        };
    }
}