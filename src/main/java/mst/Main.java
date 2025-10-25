package mst;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import mst.algorithms.*;
import mst.model.*;
import mst.util.GraphUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = "graphs.json";
        if (args.length > 0) inputPath = args[0];

        Gson gson = new Gson();
        String json = Files.readString(Path.of(inputPath));

        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray graphs = root.getAsJsonArray("graphs");
        Type listType = new TypeToken<List<GraphInput>>(){}.getType();
        List<GraphInput> inputGraphs = gson.fromJson(graphs, listType);

        List<ResultGraph> results = new ArrayList<>();

        for (GraphInput ig : inputGraphs) {
            Map<String, Integer> indexMap = new LinkedHashMap<>();
            for (int i = 0; i < ig.nodes.size(); i++)
                indexMap.put(ig.nodes.get(i), i);

            List<Edge> edges = new ArrayList<>();
            for (JsonEdge je : ig.edges) {
                edges.add(new Edge(indexMap.get(je.from), indexMap.get(je.to), je.weight));
            }

            var adj = GraphUtils.buildAdjacency(ig.nodes.size(), edges);

            // Run Prim and Kruskal
            var primRes = Prim.run(adj, ig.nodes.size());
            var kruskalRes = Kruskal.run(new ArrayList<>(edges), ig.nodes.size());

            ResultGraph rg = new ResultGraph();
            rg.graph_id = ig.id;
            rg.input_stats = new InputStats();
            rg.input_stats.vertices = ig.nodes.size();
            rg.input_stats.edges = ig.edges.size();

            rg.prim = new AlgoResult();
            rg.prim.total_cost = primRes.totalCost;
            rg.prim.operations_count = primRes.operations;
            rg.prim.execution_time_ms = primRes.timeMs;
            rg.prim.mst_edges = new ArrayList<>();
            for (Edge e : primRes.mstEdges) {
                JsonEdge je = new JsonEdge();
                je.from = ig.nodes.get(e.u);
                je.to = ig.nodes.get(e.v);
                je.weight = e.w;
                rg.prim.mst_edges.add(je);
            }

            rg.kruskal = new AlgoResult();
            rg.kruskal.total_cost = kruskalRes.totalCost;
            rg.kruskal.operations_count = kruskalRes.operations;
            rg.kruskal.execution_time_ms = kruskalRes.timeMs;
            rg.kruskal.mst_edges = new ArrayList<>();
            for (Edge e : kruskalRes.mstEdges) {
                JsonEdge je = new JsonEdge();
                je.from = ig.nodes.get(e.u);
                je.to = ig.nodes.get(e.v);
                je.weight = e.w;
                rg.kruskal.mst_edges.add(je);
            }

            results.add(rg);
        }

        JsonObject output = new JsonObject();
        output.add("results", new GsonBuilder().setPrettyPrinting().create().toJsonTree(results));
        String outJson = new GsonBuilder().setPrettyPrinting().create().toJson(output);

        Files.writeString(Path.of("results.json"), outJson);
        System.out.println(outJson);
        System.out.println("Results written to results.json");
    }
}
