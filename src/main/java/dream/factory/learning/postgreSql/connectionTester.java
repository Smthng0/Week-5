package dream.factory.learning.postgreSql;

import java.sql.*;

public class connectionTester {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/minions";
        String username = "frane";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            String update = "insert into ability_minions (title, mana_cost, " +
                    "attack, health, abilities) " +
                    "values ('faca', 0, 1, 2, '')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

            String query = "select * from ability_minions;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " "
                        + resultSet.getInt(2) + " "
                        + resultSet.getInt(3) + " "
                        + resultSet.getInt(4));
            }


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
