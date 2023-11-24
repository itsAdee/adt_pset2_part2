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

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGraphPoetCreation() throws IOException {
        File corpusFile = new File("test/poet/seven-words.txt");
        GraphPoet poet = new GraphPoet(corpusFile);
        // Add assertions to check if the graph is correctly constructed from the corpus
        // Check for specific vertices and edges based on the sample corpus
    }

    @Test
    public void testPoemGenerationWithBridgeWords() throws IOException {
        File corpusFile = new File("test/poet/seven-words.txt");
        GraphPoet poet = new GraphPoet(corpusFile);

        // Test with an input string requiring bridge words
        String input1 = "Seek to explore new and exciting synergies!";
        String poem1 = poet.poem(input1);
        assertEquals("seek to explore strange new life and exciting synergies!", poem1);
    }

    @Test
    public void testPoemGenerationWithoutBridgeWords() throws IOException {
        File corpusFile = new File("test/poet/seven-words.txt");
        GraphPoet poet = new GraphPoet(corpusFile);

        // Test with an input string where no bridge words are needed
        String input2 = "This is a test.";
        String poem2 = poet.poem(input2);
        assertEquals("this is a test.", poem2);
    }

    @Test
    public void testPoemGenerationWithUnknownWords() throws IOException {
        File corpusFile = new File("test/poet/seven-words.txt");
        GraphPoet poet = new GraphPoet(corpusFile);

        // Test with an input string containing words not present in the corpus
        String input3 = "Hello unknown world!";
        String poem3 = poet.poem(input3);
        // In this case, the unknown words should be preserved, and no bridge words
        // inserted
        assertEquals("hello unknown world!", poem3);
    }

}
