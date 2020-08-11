package com.homework.nix;

import com.homework.nix.service.ConvertDataToTaskCondition;
import com.homework.nix.service.impl.ConvertDataToTaskConditionImpl;
import com.homework.nix.util.GetProperties;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties props = GetProperties.loadProperties();

        String url = props.getProperty("url");

        try(Connection connection = DriverManager.getConnection(url, props)) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("src/main/resources/allTasksData.sql"));
            sr.runScript(reader);
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

        ConvertDataToTaskCondition convertDataToTaskCondition = new ConvertDataToTaskConditionImpl();

        convertDataToTaskCondition.convertData();
    }
}
