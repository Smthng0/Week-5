package dream.factory.learning.postgreSql;

import java.sql.*;

public class ConnectionTester {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/minions";
        String username = "frane";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            Statement statement = connection.createStatement();

            String query = "select * from ability_minions;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " "
                        + resultSet.getInt(2) + " "
                        + resultSet.getInt(3) + " "
                        + resultSet.getInt(4) + " "
                        + resultSet.getString(5));
            }


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
