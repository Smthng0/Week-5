package dream.factory.learning.mySql;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.util.List;

public class CsvImporter {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/minions";
        String username = "root";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            CsvImporter kifla = new CsvImporter();
            List<String[]> minionList = kifla.parseCsv();
            statement.executeUpdate("truncate table plain_minions");

            for (String [] row : minionList) {
                String query = kifla.insertRowToDb(row);
                statement.executeUpdate(query);
            }


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public List<String[]> parseCsv() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        CsvParser parser = new CsvParser(settings);

        return parser.parseAll(getReader("/file/PlainMinions.csv"));
    }

    public String insertRowToDb(String[] row){
        try {
            return "insert into plain_minions " +
                    "(title, mana_cost, attack, health) " +
                    "values ('" + row[1]+
                    "', " + row[0] +
                    ", " + row[2] +
                    ", " + row[3] + ")";
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Reader getReader(String relativePath) {
		try {
            return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
        } catch (Exception ex) {
		    ex.printStackTrace();
        }
        return null;
    }


}
