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
                "VALUES (:title, :manaCost, :attack, :health, :abilities)";



        Sql2o sql2o = new Sql2o(ConnectionPlayground.getCPDS());
        List<Minion> minionList = new CsvImporter().parseCsv();

        try (Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("TRUNCATE TABLE ability_minions").executeUpdate();
            for (Minion row : minionList) {
                connection.createQuery(insertQuery)
                        .bind(row)
                        .executeUpdate();
            }
        }
    }

}
