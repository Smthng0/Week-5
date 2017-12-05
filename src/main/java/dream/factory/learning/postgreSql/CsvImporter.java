package dream.factory.learning.postgreSql;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
            CsvImporter importer = new CsvImporter();
            List<Minion> minionList = importer.parseCsv();
            statement.executeUpdate("truncate table ability_minions");

            for (Minion minion : minionList) {
                String query = importer.insertRowToDb(minion);
                statement.executeUpdate(query);
            }


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public List<Minion> parseCsv() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        BeanListProcessor<Minion> processor = new BeanListProcessor<>(Minion.class);
        settings.setProcessor(processor);
        CsvParser parser = new CsvParser(settings);
        parser.parse(getReader("/file/AbilityMinions.csv"));
        return processor.getBeans();
    }

    public String insertRowToDb(Minion minion){
        try {
            return "insert into ability_minions " +
                    "(title, mana_cost, attack, health, abilities) " +
                    "values ('" + minion.getTitle()+
                    "', " + minion.getManaCost() +
                    ", " + minion.getAttack() +
                    ", " + minion.getHealth() +
                    ", '" + minion.getAbilities() + "')";

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
