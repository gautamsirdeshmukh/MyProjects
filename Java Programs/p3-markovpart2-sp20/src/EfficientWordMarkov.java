import java.util.*;

public class EfficientWordMarkov extends BaseWordMarkov{
    private Map<WordGram, ArrayList<String>> myMap;

    public EfficientWordMarkov(int order) {
        /**
         * @param order size of string for Markov model
         */
        super(order);
        myMap = new HashMap<>();

    }

    public EfficientWordMarkov() {
        myMap = new HashMap<>();
        myOrder = super.myOrder;
    }

    @Override
    public void setTraining(String text) {
        /**
         * @param text used to create map for training
         */
        super.setTraining(text);
        myMap.clear();
        WordGram wg = new WordGram(myWords, 0, myOrder);
        for (int i = 0; i < myWords.length - myOrder + 1; i++) {
            myMap.putIfAbsent(wg, new ArrayList<>());

            if (i < myWords.length - myOrder) {
                String nextWord = myWords[myOrder + i];
                myMap.get(wg).add(nextWord);
                wg = wg.shiftAdd(nextWord);
            }

            if (i == myWords.length - myOrder) {
                myMap.get(wg).add(PSEUDO_EOS);
            }

        }
    }

    @Override
    public ArrayList<String> getFollows(WordGram wg) {
        /**
         * @param k key string for map
         * @return myMap.get(k) the ArrayList that follows k
         */
        if (! myMap.keySet().contains(wg)) {
            throw new NoSuchElementException(wg + " not in map");
        }
        return myMap.get(wg);
    }
}
