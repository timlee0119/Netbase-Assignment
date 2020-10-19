import java.io.*;
import java.util.*;

public class BigramParser {
    class Bigram {
        private List<String> words;
        public Bigram(String w1, String w2) {
            words = new ArrayList<>();
            words.add(w1);
            words.add(w2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bigram bigram = (Bigram) o;
            return this.words.get(0).equals(bigram.words.get(0)) && this.words.get(1).equals(bigram.words.get(1));
        }

        @Override
        public int hashCode() {
            return Objects.hash(words);
        }
    }

    private List<Bigram> bigrams = new ArrayList<>();

    private double documentProb;

    public BigramParser(String fileName) {
        // read all words
        List<String> allWords = new ArrayList<>();
        File file = new File(fileName);
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String lastWord = "";
        while (in.hasNextLine()) {
            String currentLine = in.nextLine();
            String[] words = currentLine.split(" ");
            for (String word : words) {
                word = word.trim();
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                if (word.isEmpty()) continue;
                word = word.toLowerCase();

                if (lastWord == "") {
                    lastWord = word;
                } else {
                    bigrams.add(new Bigram(lastWord, word));
                    lastWord = word;
                }
            }
        }
    }

    public void calculateDocumentProb() {
        // populate frequency map
        Map<Bigram, Integer> frequencyMap = new HashMap<>();
        for (Bigram bigram : bigrams) {
            if (!frequencyMap.containsKey(bigram)) {
                frequencyMap.put(bigram, 1);
            }
            else {
                Integer freq = frequencyMap.get(bigram) + 1;
                frequencyMap.put(bigram, freq);
            }
        }

        // calculate prob
        double prob = 1.0;
        for (Bigram bigram : bigrams) {
            prob *= frequencyMap.get(bigram);
        }
        documentProb = Math.pow(prob, 1.0 / bigrams.size());
    }

    public String getBigramProb() {
        return String.format("%.5f", documentProb);
    }

    public String getBigramCount() {
        return String.valueOf(bigrams.size());
    }
}
