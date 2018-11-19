package cs753.T1.A5;

import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.util.BytesRef;

public class TFIDF {
	public static TFIDFSimilarity BnnBnnSim() {
		return new TFIDFSimilarity() {
			@Override
			public float tf(float freq) {
				if (freq > 0)
					return 1.0f;
				return 0.0f;
			}

			@Override
			public float idf(long docFreq, long docCount) {
				return docFreq;
			}

			@Override
			public float lengthNorm(int length) {
				return length; // TODO not sure how to implement
			}

			@Override
			public float sloppyFreq(int distance) {
				return 0;
			}

			@Override
			public float scorePayload(int doc, int start, int end, BytesRef payload) {
				return 0;
			}
			
		};
	}
	
	public static TFIDFSimilarity LncLtnSim() {
		return new TFIDFSimilarity() {
			@Override
			public float tf(float freq) {
				return 1.0f + (float)Math.log10(freq);
			}

			@Override
			public float idf(long docFreq, long docCount) {
				if (docFreq == 0)
					return 1;
				return (float)Math.log10(docCount/docFreq);
			}

			@Override
			public float lengthNorm(int length) {
				return length;
			}

			@Override
			public float sloppyFreq(int distance) {
				return 0;
			}

			@Override
			public float scorePayload(int doc, int start, int end, BytesRef payload) {
				return 0;
			}
			
		};
	}
}