package com.homework.nix.service;

import java.io.IOException;

public interface FindMinCost {

    int dijkstra(int[][] graph, int sourceVertex, int to) throws IOException;

    int findMinDistance(int[] distance, boolean[] visitedVertex);
}
