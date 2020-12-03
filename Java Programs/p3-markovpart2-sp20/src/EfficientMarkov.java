import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;

	public EfficientMarkov() {
		myOrder = super.myOrder;
		myMap = new HashMap<>();
	}

	public EfficientMarkov(int order) {
		/**
		 * @param order size of string for Markov model
		 */
		super(order);
		myMap = new HashMap<>();
	}

	@Override
	public void setTraining(String text) {
		/**
		 * @param text used to create map for training
		 */
		super.setTraining(text);
		myMap.clear();
		for (int i = 0; i < myText.length() - myOrder + 1; i++) {
			String k = myText.substring(i, i + myOrder);
			myMap.putIfAbsent(k, new ArrayList<>());
			if (i < myText.length() - myOrder) {
				String nextWord = myText.substring(i + myOrder, i + myOrder + 1);
				myMap.get(k).add(nextWord);
			}
			else if (i == myText.length() - myOrder) {
				myMap.get(k).add(super.PSEUDO_EOS);
			}
		}
	}

	@Override
	public ArrayList<String> getFollows(String k) {
		/**
		 * @param k key string for map
		 * @return myMap.get(k) the ArrayList that follows k
		 */
		if (myMap.size() == 0) {
			this.setTraining(myText);
		}
		if (! myMap.keySet().contains(k)) {
			throw new NoSuchElementException(k + " not in map");
		}
		return myMap.get(k);
	}
}	
