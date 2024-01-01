package scratch;

import csv_importer.CsvParserUtil;
import models.ParkingVisitTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CsvParserTest {
    public Map<String, String> AUTHOR_BOOK_MAP = Map.of(
            "Dan Simmons", "Hyperion",
            "Douglas Adams", "The Hitchhiker's Guide to the Galaxy"
    );
    String[] HEADERS = { "author", "title"};

    /*
    * Boilerplate test -- I did not write
    * */
    @Test
    public void givenCSVFile_whenRead_thenContentsAsExpected() throws IOException {
        Reader in = new FileReader("src/test/java/fixtures/example1.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);

        for (CSVRecord record : records) {
            String author = record.get("author");
            String title = record.get("title");
            assertEquals(AUTHOR_BOOK_MAP.get(author), title);
        }
    }

    @Test
    public void csvParserUtilCanParseContents() throws IOException {
        String filename = "src/test/java/fixtures/example2.csv";
        String[] headers = Arrays.stream(CsvParserUtil.Columns.values()).map(it -> it.name()).toArray(String[]::new);
        List<CSVRecord> recordList = CsvParserUtil.getRecordList(filename, headers);
        assertEquals(1, recordList.size());
        CSVRecord record = recordList.stream().findFirst().orElse(null);
        assertEquals("Brooklyn", record.get(CsvParserUtil.Columns.site.name()).trim());
    }

    @Test
    public void testCollectRevenueBySite() throws IOException {
        String filename = "src/test/java/fixtures/metropolis_visit_data_small.csv";
        String[] headers = new String[] {"transaction_id", "site_id", "user_id","vehicle_id","payment_status","entry_time","exit_time","price"};
        List<CSVRecord> recordList = CsvParserUtil.getRecordList(filename, headers);
        List<ParkingVisitTransaction> txns = CsvParserUtil.transformRecordsToTxns(recordList);
        Map<Long, BigDecimal> revenueMap = CsvParserUtil.calculateRevenueBySite(txns);
        // foregone, no value
        assertNull(revenueMap.get(497L));
        assertEquals(0.00, revenueMap.get(472L).doubleValue(), 0);
        assertEquals(19.25, revenueMap.get(493L).doubleValue(), 0);
        assertEquals(30.1, revenueMap.get(474L).doubleValue(), 0);
    }
    @Test
    public void testCalcMaxOccupancyBySite() throws IOException {
        String filename = "src/test/java/fixtures/metropolis_visit_data_small.csv";
        String[] headers = new String[] {"transaction_id", "site_id", "user_id","vehicle_id","payment_status","entry_time","exit_time","price"};
        List<CSVRecord> recordList = CsvParserUtil.getRecordList(filename, headers);
        List<ParkingVisitTransaction> txns = CsvParserUtil.transformRecordsToTxns(recordList);
        Map<Long, Integer> maxOccupancyMap = CsvParserUtil.calculateMaxOccupancyBySite(txns);
        // foregone, no value
        assertEquals(1, maxOccupancyMap.get(497L).intValue());
        assertEquals(1, maxOccupancyMap.get(493L).intValue());
        assertEquals(4, maxOccupancyMap.get(474L).intValue());
        // 3 spaces are replaced during the time period with 1 more claimed
        assertEquals(4, maxOccupancyMap.get(472L).intValue());
    }
}
