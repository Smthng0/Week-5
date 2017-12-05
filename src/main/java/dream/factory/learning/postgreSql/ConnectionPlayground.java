package dream.factory.learning.postgreSql;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPlayground implements Runnable{
    static String url = "jdbc:postgresql://localhost:5432/minions";
    static String username = "frane";
    static String password = "";
    private static ComboPooledDataSource cpds;

    public static void main(String[] args) throws SQLException {
        System.out.println("Connecting database...");
        List<Thread> threads = new ArrayList<>();
        cpds = getCPDS();

        for (int i = 0; i < 10_000; i++) {
            threads.add(new Thread(new ConnectionPlayground()));
        }

        for (Thread thread : threads) {
            thread.start();
        }

    }

    @Override
    public void run() {
        System.out.println("Trying to get a connection");
        try (Connection connection = cpds.getConnection()) {
            System.out.println("Database connected: "+  connection.isClosed());
            System.out.println("Size of pool: " + cpds.getNumConnections());
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static ComboPooledDataSource getCPDS() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(url);
        cpds.setUser(username);
        cpds.setPassword(password);
        try {
            cpds.setDriverClass( "org.postgresql.Driver" );
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(50);
        cpds.setCheckoutTimeout(60_000);

        return cpds;
    }

}
