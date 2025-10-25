package mst.util;

import mst.model.Edge;
import java.util.*;

public class GraphUtils {
    public static List<List<Edge>> buildAdjacency(int n, List<Edge> edges) {
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.u).add(new Edge(e.u, e.v, e.w));
            adj.get(e.v).add(new Edge(e.v, e.u, e.w));
        }
        return adj;
    }
}
