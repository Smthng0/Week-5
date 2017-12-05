package dream.factory.learning.postgreSql;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class CsvImporter {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/minions";
        String username = "frane";
        String password = "";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            CsvImporter kifla = new CsvImporter();
            List<String[]> minionList = kifla.parseCsv();
            statement.executeUpdate("truncate table ability_minions");

            for (String [] row : minionList) {
                System.out.println(Arrays.toString(row));
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

        return parser.parseAll(getReader("/file/AbilityMinions.csv"));
    }

    public String insertRowToDb(String[] row){
        try {
            return "insert into ability_minions " +
                    "(title, mana_cost, attack, health, abilities) " +
                    "values ('" + row[1]+
                    "', " + row[0] +
                    ", " + row[2] +
                    ", " + row[3] +
                    ", '" + row[4] + "')";
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
