package dream.factory.learning.mySql;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import dream.factory.learning.postgreSql.Minion;

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
            List<Minion> minionList = kifla.parseCsv();
            statement.executeUpdate("truncate table plain_minions");

            for (Minion minion : minionList) {
                String query = kifla.insertRowToDb(minion);
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
        parser.parse(getReader("/file/PlainMinions.csv"));
        return processor.getBeans();
    }

    public String insertRowToDb(Minion minion){
        try {
            return "insert into plain_minions " +
                    "(title, mana_cost, attack, health) " +
                    "values ('" + minion.getTitle()+
                    "', " + minion.getManaCost() +
                    ", " + minion.getAttack() +
                    ", " + minion.getHealth() + ")";
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
