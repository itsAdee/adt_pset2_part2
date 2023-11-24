/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph using ConcreteVerticesGraph.
 * @param <String> The type of the graph elements.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   Represents a graph with a list of vertices and their connections
    // Representation invariant:
    //   No two vertices have the same label
    // Safety from rep exposure:
    //   Vertices list is private and final, no direct exposure

    /**
     * Constructs an empty ConcreteVerticesGraph.
     */
    public ConcreteVerticesGraph() {
        // Empty constructor, as the graph starts with no vertices initially
    }

    /**
     * Checks the representation invariant of the graph.
     * Verifies that no two vertices have the same label.
     * @throws RuntimeException if a duplicate vertex label is found.
     */
    private void checkRep() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            if (labels.contains(vertex.getLabel())) {
                throw new RuntimeException("Duplicate vertex label found: " + vertex.getLabel());
            }
            labels.add(vertex.getLabel());
        }
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex The vertex to be added.
     * @return true if the vertex was added, false if it already exists in the graph.
     */
    @Override
    public boolean add(String vertex) {
        if (!containsVertex(vertex)) {
            vertices.add(new Vertex(vertex));
            checkRep();
            return true;
        }
        return false;
    }

    /**
     * Sets or updates an edge between source and target vertices with a weight.
     * @param source The source vertex.
     * @param target The target vertex.
     * @param weight The weight of the edge.
     * @return the previous weight of the edge, or zero if there was no such edge.
     */
    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = findVertex(source);
        Vertex targetVertex = findVertex(target);

        if (sourceVertex == null || targetVertex == null) {
            throw new IllegalArgumentException("Source or target vertex not found");
        }

        int previousWeight = sourceVertex.getEdges().getOrDefault(target, 0);
        if (weight != 0) {
            sourceVertex.addEdge(target, weight);
        } else {
            sourceVertex.removeEdge(target);
        }
        checkRep();
        return previousWeight;
    }

    /**
     * Removes a vertex from the graph.
     * @param vertex The vertex to be removed.
     * @return true if the vertex was removed, false if it doesn't exist in the graph.
     */
    @Override
    public boolean remove(String vertex) {
        Vertex toRemove = findVertex(vertex);
        if (toRemove != null) {
            vertices.remove(toRemove);
            for (Vertex v : vertices) {
                v.removeEdge(vertex);
            }
            checkRep();
            return true;
        }
        return false;
    }

    /**
     * Retrieves all vertices in the graph.
     * @return the set of vertices in the graph.
     */
    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            labels.add(vertex.getLabel());
        }
        return labels;
    }

    /**
     * Retrieves sources with directed edges to a target vertex and their weights.
     * @param target The target vertex.
     * @return a map of source vertices and their edge weights to the target vertex.
     */
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex vertex : vertices) {
            if (vertex.getEdges().containsKey(target)) {
                sources.put(vertex.getLabel(), vertex.getEdges().get(target));
            }
        }
        return sources;
    }

    /**
     * Retrieves targets with directed edges from a source vertex and their weights.
     * @param source The source vertex.
     * @return a map of target vertices and their edge weights from the source vertex.
     */
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex sourceVertex = findVertex(source);
        if (sourceVertex != null) {
            return sourceVertex.getEdges();
        }
        return Collections.emptyMap();
    }

    /**
     * Checks if a vertex with a given label exists in the graph.
     * @param label The label of the vertex to check.
     * @return true if the vertex exists, false otherwise.
     */
    private boolean containsVertex(String label) {
        return findVertex(label) != null;
    }

    /**
     * Finds and retrieves a vertex with the given label from the graph.
     * @param label The label of the vertex to find.
     * @return the found vertex or null if not found.
     */
    private Vertex findVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * Provides a string representation of the graph.
     * @return a string representing the graph.
     */
    @Override
    public String toString() {
        return "ConcreteVerticesGraph{" +
                "vertices=" + vertices +
                '}';
    }
}


/**
 * Represents a vertex in the graph.
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 */
class Vertex {
    private final String label;
    private final Map<String, Integer> edges; // Mapping from target vertex label to edge weight

    // Abstraction function:
    //   Represents a vertex with a unique label and outgoing edges to other vertices with their weights
    // Representation invariant:
    //   label != null, edges != null
    // Safety from rep exposure:
    //   Fields are private and immutable
    
    /**
     * Constructs a Vertex with the specified label.
     * @param label The label for the vertex.
     */
    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
    }
    
    /**
     * Checks the representation invariant of the Vertex class.
     * Verifies that the label and edges map of the vertex are not null.
     * @throws AssertionError if the label or edges map is null.
     */
    private void checkRep() {
        assert label != null : "Vertex label cannot be null";
        assert edges != null : "Edges map cannot be null";
    }

    /**
     * Adds an edge from this vertex to another with a specified weight.
     * @param target The target vertex.
     * @param weight The weight of the edge.
     */
    public void addEdge(String target, int weight) {
        edges.put(target, weight);
        checkRep();
    }

    /**
     * Removes an edge from this vertex to another.
     * @param target The target vertex.
     */
    public void removeEdge(String target) {
        edges.remove(target);
        checkRep();
    }

    /**
     * Retrieves all outgoing edges from this vertex.
     * @return a map of target vertices and their edge weights.
     */
    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges); // Return a copy to avoid exposing internal representation
    }

    /**
     * Retrieves the label of this vertex.
     * @return the label of the vertex.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Provides a string representation of the vertex.
     * @return a string representing the vertex.
     */
    @Override
    public String toString() {
        return "Vertex{" +
                "label='" + label + '\'' +
                ", edges=" + edges +
                '}';
    }
}

