package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import graph.Graph;
import graph.ConcreteVerticesGraph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = new ConcreteVerticesGraph();

	 // Abstraction function:
	 // Represents a GraphPoet that generates poems based on word affinity graphs derived from a corpus.
	 // The graph contains vertices representing words and edges representing adjacency counts.
	 // The poem generation attempts to insert bridge words between adjacent input words.
	 // If there are no valid bridge words, no insertion occurs.
	
	 // Representation invariant:
	 // - The graph should contain vertices representing case-insensitive words as non-empty strings of non-space non-newline characters.
	 // - Edges in the graph represent the adjacency count between words in the corpus.
	
	 // Safety from rep exposure:
	 // - The graph instance is private and only accessed through appropriate methods in the GraphPoet class.
	

    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	readCorpus(corpus);
    }
    
    /**
     * Read the corpus file and populate the graph based on word adjacencies.
     *
     * @param corpus text file containing the corpus
     * @throws IOException if the corpus file cannot be found or read
     */
    private void readCorpus(File corpus) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(corpus))) {
            String line;
            String prevWord = null;

            while ((line = reader.readLine()) != null) {
                List<String> words = Arrays.stream(line.split("\\s+"))
                        .filter(word -> !word.isEmpty())
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

                for (String word : words) {
                    if (!graph.vertices().contains(word)) {
                        graph.add(word);
                    }

                    if (prevWord != null) {
                        int weight = graph.targets(prevWord).getOrDefault(word, 0);
                        graph.set(prevWord, word, weight + 1);
                    }

                    prevWord = word;
                }
            }
        }
    }

    /**
     * Verifies that the representation invariant holds true for the current state of the GraphPoet.
     * @throws AssertionError if the representation invariant is violated.
     */
    private void checkRep() {
        Set<String> vertices = graph.vertices();
        for (String vertex : vertices) {
            // Validate that each vertex is a non-empty string of non-space non-newline characters
            assert vertex != null && !vertex.trim().isEmpty() && !vertex.contains(" ") && !vertex.contains("\n") :
                    "Invalid vertex representation: " + vertex;
        }
        // Check for the adjacency count between words in the corpus
        for (String source : vertices) {
            Map<String, Integer> edges = graph.targets(source);
            for (String target : edges.keySet()) {
                int weight = edges.get(target);
                assert weight >= 0 : "Negative weight between " + source + " and " + target;
            }
        }
    }

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        List<String> inputWords = Arrays.stream(input.split("\\s+"))
                .filter(word -> !word.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        StringBuilder poemBuilder = new StringBuilder();

        for (int i = 0; i < inputWords.size() - 1; i++) {
            String source = inputWords.get(i);
            String target = inputWords.get(i + 1);

            if (!graph.vertices().contains(source) || !graph.vertices().contains(target)) {
                poemBuilder.append(source).append(" ");
                continue;
            }

            List<String> bridgeWords = findBridgeWords(source, target);
            if (!bridgeWords.isEmpty()) {
                poemBuilder.append(source).append(" ");
                poemBuilder.append(bridgeWords.get(0)).append(" ");
            } else {
                poemBuilder.append(source).append(" ");
            }
        }

        poemBuilder.append(inputWords.get(inputWords.size() - 1));
        return poemBuilder.toString();
    }

    /**
     * Find bridge words between source and target in the graph.
     *
     * @param source source word
     * @param target target word
     * @return list of bridge words
     */
    private List<String> findBridgeWords(String source, String target) {
        List<String> bridgeWords = graph.targets(source).keySet().stream()
                .filter(bridge -> graph.targets(bridge).containsKey(target))
                .collect(Collectors.toList());

        bridgeWords.sort((b1, b2) -> graph.targets(source).get(b2) - graph.targets(source).get(b1));
        return bridgeWords;
    }
    
    /**
     * Provides a string representation of the current state of the GraphPoet.
     * @return a string representing the GraphPoet.
     */
    @Override
    public String toString() {
        int vertexCount = graph.vertices().size();
        int edgeCount = graph.vertices().stream().mapToInt(vertex -> graph.targets(vertex).size()).sum();
        return "GraphPoet with a word affinity graph containing " + vertexCount + " vertices and " + edgeCount + " edges.";
    }
}