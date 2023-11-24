/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 *
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

//     Abstraction function:
//     Represents a directed graph with labeled vertices and weighted edges.
//     Representation invariant:
//     - vertices is a set of non-null strings representing unique vertex labels.
//     - edges is a list of non-null Edge objects.
//     - The source and target vertices of each edge are in the vertices set.
//     Safety from rep exposure:
//     - All fields are private and final.
//     - Return defensive copies of mutable collections.

    /**
     * Checks the representation invariant of the ConcreteEdgesGraph.
     */
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
        }
    }

    @Override
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false; // Vertex already exists
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        Edge newEdge = new Edge(source, target, weight);
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int previousWeight = edge.getWeight();
                if (weight != 0) {
                    edge.setWeight(weight);
                } else {
                    edges.remove(edge);
                }
                checkRep();
                return previousWeight;
            }
        }
        edges.add(newEdge);
        checkRep();
        return 0; // No previous edge existed
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false; // Vertex doesn't exist
        }

        // Remove edges pointing to the vertex
        edges.removeIf(edge -> edge.getTarget().equals(vertex));

        // Remove edges going out from the vertex
        edges.removeIf(edge -> edge.getSource().equals(vertex));

        vertices.remove(vertex);
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sourcesMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sourcesMap.put(edge.getSource(), edge.getWeight());
            }
        }
        return sourcesMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targetsMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targetsMap.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targetsMap;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Edge edge : edges) {
            result.append(edge.toString()).append("\n");
        }
        return result.toString();
    }
}

/**
 * An edge in the ConcreteEdgesGraph.
 */
class Edge {

    private String source;
    private String target;
    private int weight;

    // Abstraction function:
    // Represents a directed edge from the source vertex to the target vertex with a
    // specified weight.
    // Representation invariant:
    // - source and target are non-null strings.
    // - weight is a non-negative integer.
    // Safety from rep exposure:
    // - All fields are private

    /**
     * Constructs an Edge.
     *
     * @param source the source vertex label
     * @param target the target vertex label
     * @param weight the weight of the edge
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    /**
     * Checks the representation invariant of the Edge.
     */
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight >= 0;
    }

    /**
     * Gets the source vertex label of the edge.
     *
     * @return the source vertex label
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the target vertex label of the edge.
     *
     * @return the target vertex label
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the weight of the edge.
     *
     * @param weight the new weight of the edge
     */
    public void setWeight(int weight) {
        this.weight = weight;
        checkRep();
    }

    /**
     * Gets the weight of the edge.
     *
     * @return the weight of the edge
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("(%s -> %s, weight: %d)", source, target, weight);
    }

}
