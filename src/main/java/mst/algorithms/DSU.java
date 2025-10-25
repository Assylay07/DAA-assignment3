package mst.algorithms;

public class DSU {
    private final int[] parent;
    private final int[] rank;
    public long operations = 0;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        operations++;
        if (parent[x] == x) return x;
        parent[x] = find(parent[x]);
        return parent[x];
    }

    public boolean union(int a, int b) {
        operations++;
        a = find(a);
        b = find(b);
        if (a == b) return false;
        if (rank[a] < rank[b]) parent[a] = b;
        else if (rank[b] < rank[a]) parent[b] = a;
        else {
            parent[b] = a;
            rank[a]++;
        }
        return true;
    }
}
