package dream.factory.learning.mySql;

import org.junit.Test;

import static org.junit.Assert.*;

public class CsvImporterTest {
    @Test
    public void parseCsv() throws Exception {
        new CsvImporter().parseCsv();
    }

}