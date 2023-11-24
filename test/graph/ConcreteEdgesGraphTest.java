/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString()
    // - Test when the graph is empty
    // - Test when the graph has vertices and edges

    @Test
    public void testConcreteEdgesGraphToString() {
        Graph<String> graph = emptyInstance();
        assertEquals("", graph.toString()); // Empty graph

        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);
        graph.set("B", "A", 2);

        assertEquals("(A -> B, weight: 3)\n(B -> A, weight: 2)\n", graph.toString());
    }

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    // - Test the constructor
    // - Test getSource()
    // - Test getTarget()
    // - Test getWeight()
    // - Test setWeight()

    @Test
    public void testEdge() {
        Edge edge = new Edge("A", "B", 3);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(3, edge.getWeight());

        edge.setWeight(5);
        assertEquals(5, edge.getWeight());
    }

    // Add more tests for various scenarios and edge cases

    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("A", "B", 3);
        assertEquals("(A -> B, weight: 3)", edge.toString());
    }
}