import java.io.*;
import java.util.*;

public class BigramParser {
    private List<List<String>> bigrams = new ArrayList<>();

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
        while (in.hasNextLine()) {
            String currentLine = in.nextLine();
            String[] words = currentLine.split(" ");
            for (String word : words) {
                word = word.trim();
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                if (word.isEmpty()) continue;

                allWords.add(word.toLowerCase());
            }
        }

        // get bigrams
        int len = allWords.size();
        for (int i = 0; i <= len - 2; ++i) {
            List<String> bigram = new ArrayList<>();
            bigram.add(allWords.get(i));
            bigram.add(allWords.get(i + 1));
            bigrams.add(bigram);
        }
    }

    public void calculateDocumentProb() {
        // populate frequency map
        Map<List<String>, Integer> frequencyMap = new HashMap<>();
        for (List<String> bigram : bigrams) {
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
        for (List<String> bigram : bigrams) {
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
