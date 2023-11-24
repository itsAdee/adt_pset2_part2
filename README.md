# Graph ADT Java Implementation

This Java project provides a flexible implementation of a mutable weighted directed graph with labeled vertices, offering two distinct implementations: `ConcreteVerticesGraph` and `ConcreteEdgesGraph`.

## Components

### Graph Interface

The `Graph` interface defines the operations for a weighted directed graph with labeled vertices. Key functionalities include:

- Adding and removing vertices
- Setting edges with weights
- Retrieving vertices and their connections

Each method in the interface is thoroughly documented with specifications and strategies in the JavaDoc comments.

### ConcreteVerticesGraph Class

This class implements the `Graph` interface using a list of vertices (`Vertex` class) and their connections. Highlights include:

- Operations for managing vertices and their connections
- Robust JavaDoc comments detailing method specifications and strategies

### ConcreteEdgesGraph Class

An alternative implementation of the `Graph` interface utilizing an `Edge` class to represent connections. Key features include:

- Different underlying data structure compared to `ConcreteVerticesGraph`
- Similar functionalities with a different approach

### GraphInstanceTest Abstract Test Class

An abstract JUnit test class containing test cases for the `Graph` interface. Features include:

- Comprehensive tests covering various functionalities of the graph ADT
- In-depth JavaDoc comments specifying testing strategies for each test case

### ConcreteVerticesGraphTest and ConcreteEdgesGraphTest JUnit Test Classes

These test classes provide specific test cases for the concrete implementations. Highlights include:

- Detailed test methods with JavaDoc comments outlining each test's purpose and functionalities being tested

## Usage

To utilize this graph implementation in your Java project:

1. Clone or download the repository.
2. Import the relevant Java files into your project.
3. Instantiate the `ConcreteVerticesGraph` or `ConcreteEdgesGraph` classes to work with directed graphs.

## Running Tests

Execute the provided JUnit tests in the test package to ensure the functionalities and correctness of the graph implementation.

## Authors

- [Muhammad Ashhub Ali](https://github.com/NightWalker7558)
- [Abdul Arham](https://github.com/a-arham-x)
- [Adeel Ahmed](https://github.com/itsAdee)
- [Ahmad Zafar](https://github.com/Arch-Frost)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for detailed licensing information.

