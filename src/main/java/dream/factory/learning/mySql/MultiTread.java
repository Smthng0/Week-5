package dream.factory.learning.mySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultiTread implements Runnable {
    static String url = "jdbc:mysql://localhost:3306/minions";
    static String username = "root";
    static String password = "";


    public static void main(String[] args) throws SQLException {
        System.out.println("Connecting database...");
        List<Thread> threads = new ArrayList<>();


        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new MultiTread()));
        }

        for (Thread thread : threads) {
            thread.start();
        }

    }

    @Override
    public void run() {

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected: "+  connection.isClosed());


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
