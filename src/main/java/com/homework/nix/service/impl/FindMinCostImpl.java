package com.homework.nix.service.impl;

import com.homework.nix.service.FindMinCost;

import java.io.IOException;

public class FindMinCostImpl implements FindMinCost {


    @Override
    public int dijkstra(int[][] graph, int sourceVertex, int to) throws IOException {
        int vertexCount = graph.length;
        boolean[] visitedVertex = new boolean[vertexCount];
        int[] distance = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++){
            visitedVertex[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }

        distance[sourceVertex] = 0;
        for (int i = 0; i < vertexCount; i++){

            int u = findMinDistance(distance, visitedVertex);

            visitedVertex[u] = true;

            for (int v =0 ; v < vertexCount; v++){

                if(!visitedVertex[v] && graph[u][v] != 0 && (distance[u] + graph[u][v] < distance[v])){
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }
    
        return distance[to];
    }

    @Override
    public int findMinDistance(int[] distance, boolean[] visitedVertex) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = -1;
        for (int i =0; i < distance.length; i++){

            if(!visitedVertex[i] && distance[i] < minDistance){
                minDistance = distance[i];
                minDistanceVertex = i;
            }
        }
        return minDistanceVertex;
    }


}
