import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TheSpamFilterV1 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TheSpamFilterV1 input_path output_path");
            System.exit(1);
        }
        BigramParser bigramParser = new BigramParser(args[0]);
        bigramParser.calculateDocumentProb();
        String bigramProb = bigramParser.getBigramProb();
        String bigramCount = bigramParser.getBigramCount();

        // write output to file
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(args[1]));
            writer.write(bigramProb + "\n");
            writer.write(bigramCount + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}