package mst.algorithms;

import mst.model.Edge;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Kruskal {
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public long totalCost = 0;
        public long operations = 0;
        public double timeMs = 0;
    }

    public static Result run(List<Edge> edges, int n) {
        Result res = new Result();
        DSU dsu = new DSU(n);
        AtomicLong comparisons = new AtomicLong(0);

        Comparator<Edge> cmp = (a, b) -> {
            comparisons.incrementAndGet();
            return Long.compare(a.w, b.w);
        };

        long start = System.nanoTime();
        edges.sort(cmp);

        long edgeChecks = 0;
        for (Edge e : edges) {
            edgeChecks++;
            if (dsu.find(e.u) != dsu.find(e.v)) {
                if (dsu.union(e.u, e.v)) {
                    res.mstEdges.add(e);
                    res.totalCost += e.w;
                }
            }
            if (res.mstEdges.size() == n - 1) break;
        }

        long end = System.nanoTime();
        res.operations = comparisons.get() + dsu.operations + edgeChecks;
        res.timeMs = (end - start) / 1_000_000.0;
        return res;
    }
}
