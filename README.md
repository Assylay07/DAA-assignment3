## Project Overview

This project demonstrates and compares two classical algorithms for finding a Minimum Spanning Tree (MST) - Prim’s and Kruskal’s.
The algorithms were implemented in Java and tested using data from `graphs.json`, representing weighted undirected graphs.
The goal was to evaluate their performance, efficiency, and accuracy when determining the minimal total connection cost between nodes (e.g., in a city transportation or communication network).
The program outputs the MST structure and detailed statistics in `results.json`.

---

## Analytical Report – Prim’s and Kruskal’s Algorithms

### 1. Summary of Input Data and Algorithm Results

Two test graphs were used to analyze algorithmic performance.

| Graph ID | Vertices | Edges | Algorithm | MST Cost | Operations | Time (ms) |
| -------- | -------- | ----- | --------- | -------- | ---------- | --------- |
| 1        | 5        | 7     | Prim      | 16       | 35         | 0.9076    |
| 1        | 5        | 7     | Kruskal   | 16       | 44         | 0.051     |
| 2        | 4        | 5     | Prim      | 6        | 26         | 0.0203    |
| 2        | 4        | 5     | Kruskal   | 6        | 30         | 0.0206    |

Both algorithms produced identical total MST costs, confirming correctness.
The specific sets of selected edges differ slightly due to algorithmic strategies.

---

### 2. Comparison Between Prim’s and Kruskal’s Algorithms

Prim’s Algorithm

* Grows the MST from a starting vertex, selecting the smallest edge connecting the tree to an unvisited vertex.
* Performs better on dense graphs using adjacency lists and priority queues.
* Time complexity: O(E log V).

Kruskal’s Algorithm

* Sorts all edges and adds the smallest ones that do not form a cycle, using a Union-Find data structure.
* Performs better on sparse graphs.
* Time complexity: O(E log E).

Performance Analysis:

* On Graph 1 (dense), Kruskal was faster (0.051 ms vs. 0.9076 ms).
* On Graph 2 (sparser), both algorithms performed almost equally.
* Operation counts show Kruskal performing more comparisons but completing faster due to simpler structure.

---

### 3. Conclusions

Both algorithms correctly identified the MST with identical total costs across all graphs.
Key insights:

* Kruskal’s Algorithm is generally more efficient for sparse graphs and simpler to implement.
* Prim’s Algorithm is more suitable for dense graphs or when using optimized data structures (heaps).
* Minor runtime differences depend on implementation details and input graph representation.
* Both algorithms are essential for understanding network optimization, graph connectivity, and cost minimization problems.


