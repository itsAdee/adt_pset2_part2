/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    // Testing strategy
    // TODO

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGraphPoetCreation() throws IOException {
        File corpusFile = new File("test/poet/sample-corpus.txt");
        GraphPoet poet = new GraphPoet(corpusFile);
        // Add assertions to check if the graph is correctly constructed from the corpus
        // Check for specific vertices and edges based on the sample corpus
    }

}
