package com.homework.nix.service.impl;

import com.homework.nix.service.GetDBData;
import com.homework.nix.util.GetProperties;

import java.sql.*;
import java.util.Properties;

public class GetDBDataImpl implements GetDBData {


    static Properties props = GetProperties.loadProperties();

    static String url = props.getProperty("url");

    @Override
    public int getN() {
        try(Connection connection = DriverManager.getConnection(url, props)) {
            try (Statement getLocationAmount = connection.createStatement()) {
                ResultSet resultSet = getLocationAmount.executeQuery("SELECT count(*) FROM locations;");
                while (resultSet.next()) {

                    int n = resultSet.getInt(1);

                    return n;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getNAMEById(int id) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement getId = connection.prepareStatement(
                    "SELECT id, name FROM locations WHERE id = ?")) {

                getId.setInt(1, id);

                ResultSet resultSet = getId.executeQuery();

                while (resultSet.next()) {

                    String locationName = resultSet.getString("name");

                    return locationName;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getP(int locationId) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement p = connection.prepareStatement(
                    "SELECT count(id) FROM routes WHERE from_id = ?")) {

                p.setInt(1, locationId);

                ResultSet resultSet = p.executeQuery();

                while (resultSet.next()) {

                    int neighborsAmount = resultSet.getInt("count");

                    return neighborsAmount;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getNR(int locationId, int neighbourIndex) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement nr = connection.prepareStatement(
                    "SELECT * FROM (SELECT ROW_NUMBER () OVER (PARTITION BY from_id) AS rnum, to_id, from_id FROM routes) AS T WHERE from_id = ? AND rnum = ?")) {

                nr.setInt(1, locationId);
                nr.setInt(2, neighbourIndex);

                ResultSet resultSet = nr.executeQuery();

                while (resultSet.next()) {

                    int neighbourId = resultSet.getInt("to_id");

                    return neighbourId;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getCost(int locationId, int neighborIndex) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement cost = connection.prepareStatement(
                    "SELECT * FROM (SELECT ROW_NUMBER () OVER (PARTITION BY from_id) AS rnum, to_id, from_id, cost FROM routes) AS T WHERE from_id = ? AND rnum = ?")) {

                cost.setInt(1, locationId);
                cost.setInt(2, neighborIndex);

                ResultSet resultSet = cost.executeQuery();

                while (resultSet.next()) {

                    int pathCost = resultSet.getInt("cost");

                    return pathCost;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getR() {
        try(Connection connection = DriverManager.getConnection(url, props)) {
            try (Statement getPathAmount = connection.createStatement()) {
                ResultSet resultSet = getPathAmount.executeQuery("SELECT count(*) FROM problems;");
                while (resultSet.next()) {

                    int n = resultSet.getInt(1);

                    return n;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getIdNAME1(int index) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement NAME1 = connection.prepareStatement(
                    "SELECT id, from_id FROM problems WHERE id = ?")) {

                NAME1.setInt(1, index);

                ResultSet resultSet = NAME1.executeQuery();

                while (resultSet.next()) {

                    int from_id = resultSet.getInt("from_id");

                    return from_id;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getIdNAME2(int index) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement NAME2 = connection.prepareStatement(
                    "SELECT id, to_id FROM problems WHERE id = ?")) {

                NAME2.setInt(1, index);

                ResultSet resultSet = NAME2.executeQuery();

                while (resultSet.next()) {

                    int to_id = resultSet.getInt("to_id");

                    return to_id;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getProblemIdByFromIdAndToId(int from_id, int to_id) {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            try (PreparedStatement getId = connection.prepareStatement(
                    "SELECT id, to_id, from_id FROM problems WHERE from_id = ? AND to_id = ?")) {

                getId.setInt(1, from_id);
                getId.setInt(2, to_id);

                ResultSet resultSet = getId.executeQuery();

                while (resultSet.next()) {

                    int problemId = resultSet.getInt("id");

                    return problemId;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
