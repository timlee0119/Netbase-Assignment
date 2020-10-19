import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TheSpamFilterV2 {
    static Map<List<String>, Integer> occurrenceMap = new HashMap<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TheSpamFilterV2 input_directory_path");
            System.exit(1);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(12);
        // handle user interruption
        Runtime.getRuntime().addShutdownHook(new Thread(() -> threadPool.shutdownNow()));

        // get all file names in directory
        List<String> files = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {
            files = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // execute worker threads
        for (String file : files) {
            threadPool.execute(() -> {
                BigramParser bigramParser = new BigramParser(file);
                bigramParser.calculateDocumentProb();
                String bigramProb = bigramParser.getBigramProb();
                String bigramCount = bigramParser.getBigramCount();
//                List<String> countProbPair = Arrays.asList(bigramCount, bigramProb);
                synchronized (occurrenceMap) {
                    Integer occurrence = addOccurrence(bigramProb, bigramCount);
//                    Integer occurrence = occurrenceMap.get(countProbPair);
//                    if (null == occurrence) occurrence = 0;
//                    occurrence += 1;
//                    occurrenceMap.put(countProbPair, occurrence);
                    System.out.println(bigramCount + ", " + bigramProb + ", " + occurrence);
                }
            });
        }

        threadPool.shutdown();
    }

    static Integer addOccurrence(String bigramProb, String bigramCount) {
        List<String> countProbPair = Arrays.asList(bigramCount, bigramProb);
        Integer occurrence = occurrenceMap.get(countProbPair);
        if (null == occurrence) occurrence = 0;
        occurrence += 1;
        occurrenceMap.put(countProbPair, occurrence);
        return occurrence;
    }
}

@
class TheSpamFilterV2Test {


    public TheSpamFilterV2Test() {

    }

    @Test
    public whenAddOccurenceThreeTimesShouldMapHasCorrectValue() {
        Integer o1 = TheSpamFilterV2.addOccurrence("12345", "1");
        Integer o2 = TheSpamFilterV2.addOccurrence("1234", "2");
        Integer o3 = TheSpamFilterV2.addOccurrence("12345", "1");


        assertEqual(TheSpamFilterV2.occurrenceMap.get(Arrays.asList("12345", "1")), 2);
        assertEqual(TheSpamFilterV2.occurrenceMap.get(Arrays.asList("1234", "2")), 1);
        assertEqual(o1, 1);
        assertEqual(o2, 1);
        assertEqual(o3, 2);
    }
}
