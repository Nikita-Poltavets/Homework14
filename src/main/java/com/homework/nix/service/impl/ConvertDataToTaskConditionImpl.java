package com.homework.nix.service.impl;

import com.homework.nix.service.ConvertDataToTaskCondition;
import com.homework.nix.service.GetDBData;
import com.homework.nix.util.GetProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConvertDataToTaskConditionImpl implements ConvertDataToTaskCondition {

    @Override
    public void convertData() {

        Properties props = GetProperties.loadProperties();

        String url = props.getProperty("url");

        GetDBData getDBData = new GetDBDataImpl();

        try(Connection connection = DriverManager.getConnection(url, props)) {
            
            int counterj = 0;

            int n = getDBData.getN();


            int[][] graph = new int[n][n];

            for (int i = 1; i <= n; i++) {

                int p = getDBData.getP(i);
                for (int j = 1; j <= p; j++) {
                    int nr = getDBData.getNR(i, j);
                    int cost = getDBData.getCost(i, j);
                    graph[nr - 1][counterj] = cost;
                }
                counterj++;
            }

            int r = getDBData.getR();
            for (int k = 1; k <= r; k++) {

                int start = getDBData.getIdNAME1(k);
                int end = getDBData.getIdNAME2(k);
                FindMinCostImpl findMinCost = new FindMinCostImpl();


                try(PreparedStatement insertSolution = connection.prepareStatement(
                        "INSERT INTO solutions (problem_id, cost) VALUES (?, ?)"
                )) {

                    int cheapestCost = findMinCost.dijkstra(graph, start - 1, end - 1);

                    insertSolution.setInt(1, getDBData.getProblemIdByFromIdAndToId(start, end));
                    insertSolution.setInt(2, cheapestCost);

                    insertSolution.executeUpdate();


                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
