package poet;

import graph.ConcreteVerticesGraph;  // Import ConcreteVerticesGraph from the 'graph' package

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class GraphPoet {

    private final ConcreteVerticesGraph graph;

    /**
     * Create a new poet with the graph from a corpus file.
     *
     * @param corpusFile a File object representing the corpus file
     * @throws IOException if there is an issue reading the corpus file
     */
    public GraphPoet(File corpusFile) throws IOException {
        List<String> corpusLines = Files.readAllLines(corpusFile.toPath());
        this.graph = buildGraph(corpusLines);
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split("\\s+");
        StringBuilder poemBuilder = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            String bridgeWord = findBridgeWord(word1, word2);
            poemBuilder.append(word1).append(" ");

            if (bridgeWord != null) {
                poemBuilder.append(bridgeWord.toLowerCase()).append(" ");
            }
        }

        // Append the last word
        poemBuilder.append(words[words.length - 1]);

        return poemBuilder.toString().trim();
    }

    private ConcreteVerticesGraph buildGraph(List<String> corpus) {
        ConcreteVerticesGraph newGraph = new ConcreteVerticesGraph();

        for (String line : corpus) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                String word1 = words[i].toLowerCase();
                String word2 = words[i + 1].toLowerCase();
                int weight = newGraph.targets(word1).getOrDefault(word2, 0);
                newGraph.set(word1, word2, weight + 1);
            }
        }

        return newGraph;
    }

    private String findBridgeWord(String word1, String word2) {
        Map<String, Integer> sourceWeights = graph.sources(word2);

        int maxWeight = 0;
        String bridgeWord = null;

        for (Map.Entry<String, Integer> entry : sourceWeights.entrySet()) {
            String potentialBridgeWord = entry.getKey();
            int weight = entry.getValue() + graph.targets(potentialBridgeWord).getOrDefault(word2, 0);

            if (weight > maxWeight) {
                maxWeight = weight;
                bridgeWord = potentialBridgeWord;
            }
        }

        return bridgeWord;
    }

    public static void main(String[] args) {
        // Example usage:
        // Replace this with your actual corpus file
        File corpusFile = new File("file.txt");

        try {
            GraphPoet poet = new GraphPoet(corpusFile);

            // Replace this with your input string
            String input = "This is a test sentence.";

            String poem = poet.poem(input);
            System.out.println(poem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
