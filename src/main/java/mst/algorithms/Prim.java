package mst.algorithms;

import mst.model.Edge;

import java.util.*;

public class Prim {
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public long totalCost = 0;
        public long operations = 0;
        public double timeMs = 0;
    }

    public static Result run(List<List<Edge>> adj, int n) {
        Result res = new Result();
        long start = System.nanoTime();

        boolean[] used = new boolean[n];
        long[] key = new long[n];
        int[] parent = new int[n];
        Arrays.fill(key, Long.MAX_VALUE);
        Arrays.fill(parent, -1);

        class Node implements Comparable<Node> {
            long w; int v; int from;
            Node(long w, int v, int from) { this.w = w; this.v = v; this.from = from; }
            public int compareTo(Node o) { return Long.compare(this.w, o.w); }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        key[0] = 0;
        pq.add(new Node(0, 0, -1));

        long edgeChecks = 0, pqOps = 1, vertexAdded = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            pqOps++;
            int v = cur.v;
            if (used[v]) continue;
            used[v] = true;
            vertexAdded++;
            if (cur.from != -1) {
                res.mstEdges.add(new Edge(cur.from, v, cur.w));
                res.totalCost += cur.w;
            }

            for (Edge e : adj.get(v)) {
                edgeChecks++;
                if (!used[e.v] && e.w < key[e.v]) {
                    key[e.v] = e.w;
                    parent[e.v] = v;
                    pq.add(new Node(e.w, e.v, v));
                    pqOps++;
                }
            }
        }

        long end = System.nanoTime();
        res.operations = edgeChecks + pqOps + vertexAdded;
        res.timeMs = (end - start) / 1_000_000.0;
        return res;
    }
}
