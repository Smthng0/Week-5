package dream.factory.learning.postgreSql;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oImporter {
    public static void main(String[] args) {
        new Sql2oImporter().insertMinion();
    }

    private void insertMinion(){
        final String insertQuery =
                "INSERT INTO ability_minions (title, mana_cost, attack, health, abilities) " +
                "VALUES (:title, :mana_cost, :attack, :health, :abilities)";

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/minions", "frane", "");
        List<String[]> minionList = new CsvImporter().parseCsv();

        try (Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("TRUNCATE TABLE ability_minions").executeUpdate();
            for (String[] row : minionList) {
                connection.createQuery(insertQuery)
                        .addParameter("title", row[1])
                        .addParameter("mana_cost", Integer.parseInt(row[0]))
                        .addParameter("attack", Integer.parseInt(row[2]))
                        .addParameter("health", Integer.parseInt(row[3]))
                        .addParameter("abilities", row[4])
                        .executeUpdate();
            }
        }

    }
}
