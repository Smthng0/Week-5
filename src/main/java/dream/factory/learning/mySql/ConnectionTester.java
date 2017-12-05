package dream.factory.learning.mySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTester {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/minions";
        String username = "root";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            Statement statement = connection.createStatement();

            List<String> minionList = new ArrayList<>();
            String query = "select * from plain_minions;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " "
                        + resultSet.getInt(2) + " "
                        + resultSet.getInt(3) + " "
                        + resultSet.getInt(4));
                minionList.add(resultSet.getString(1));
            }


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }


}
