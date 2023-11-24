/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Testing Strategy for ConcreteVerticesGraph:
 * 
 * - Test adding vertices:
 *   - Add single vertices.
 *   - Attempt to add duplicate vertices.
 * 
 * - Test setting edges:
 *   - Add edges between vertices with various weights.
 *   - Update edge weights.
 *   - Remove edges by setting weight to 0.
 * 
 * - Test removing vertices:
 *   - Remove existing and non-existing vertices.
 * 
 * - Test getting vertices:
 *   - Add vertices and check if they are present.
 * 
 * - Test getting sources and targets:
 *   - Set various edges between vertices and check the sources and targets.
 *   - Check sources and targets for vertices without edges.
 */

/**
 * Testing Strategy for Vertex:
 * 
 * - Test getting and setting vertex label:
 *   - Initialize a vertex with a label and retrieve it.
 * 
 * - Test adding and removing edges:
 *   - Add edges to the vertex with various weights.
 *   - Remove edges and ensure they are no longer present.
 * 
 * - Test retrieving edges:
 *   - Add multiple edges and check if the retrieved edges match.
 */

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /**
     * Tests various graph operations in the ConcreteVerticesGraph implementation.
     * Verifies the functionalities of adding vertices, setting edges, removing vertices,
     * retrieving vertices, setting sources and targets, and related edge operations.
     */
    @Test
    public void testGraphOperations() {
        Graph<String> graph = emptyInstance();

        assertTrue(graph.add("A"));
        assertTrue(graph.add("B"));
        assertTrue(graph.add("C"));
        assertFalse(graph.add("A"));

        assertEquals(0, graph.set("A", "B", 5));
        assertEquals(0, graph.set("A", "C", 3));
        assertEquals(5, graph.set("A", "B", 8));
        assertEquals(3, graph.set("A", "C", 0));

        assertTrue(graph.remove("A"));
        assertFalse(graph.remove("A"));

        graph.add("D");
        graph.add("E");
        Set<String> vertices = graph.vertices();
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
        assertTrue(vertices.contains("D"));
        assertTrue(vertices.contains("E"));
        assertFalse(vertices.contains("A"));

        graph.set("B", "D", 7);
        graph.set("E", "B", 2);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(1, sources.size());
        assertTrue(sources.containsKey("E"));
        assertEquals(2, (int) sources.get("E"));

        Map<String, Integer> targets = graph.targets("B");
        assertEquals(1, targets.size());
        assertTrue(targets.containsKey("D"));
        assertEquals(7, (int) targets.get("D"));

        assertEquals(0, graph.targets("C").size());
    }

    /**
     * Tests various operations on the Vertex class.
     * Verifies functionalities of adding edges, retrieving edges, and removing edges.
     */
    @Test
    public void testVertexOperations() {
        Vertex vertex = new Vertex("X");

        assertEquals("X", vertex.getLabel());

        vertex.addEdge("Y", 5);
        vertex.addEdge("Z", 3);

        Map<String, Integer> edges = vertex.getEdges();
        assertEquals(2, edges.size());
        assertEquals(5, (int) edges.get("Y"));
        assertEquals(3, (int) edges.get("Z"));

        vertex.removeEdge("Y");
        assertEquals(1, vertex.getEdges().size());

        vertex.removeEdge("Z");
        assertEquals(0, vertex.getEdges().size());
    }
}
