package csv_importer.processors;

import db.daos.BeansTransactionDAO;
import db.daos.categories.CategoryDAO;
import db.entities.transactions.BeansTransactionEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CsvProcessorTest {
    private BeansTransactionDAO mockBeansTxnDao;
    private CategoryDAO mockCategoryDao;
    private static final String EMAIL = "test@gmail.com";

    final String[] headers = {
            "Details",
            "Posting Date",
            "Description",
            "Amount",
            "Type",
            "Balance",
            "Check or Slip #"
    };

    @Before
    public void setUp() {
        mockBeansTxnDao = mock(BeansTransactionDAO.class);
        mockCategoryDao = mock(CategoryDAO.class);
    }

    @Test
    public void givenCSVFile_whenRead_thenContentsAsExpected() throws IOException {
        String filename = "src/test/java/fixtures/csv_importer/chase_example1.csv";
        Reader in = new FileReader(filename);

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);

        List<Map<String, String>> actualCsvResults = new ArrayList<>();
        for (CSVRecord record : records) {

            Map<String, String> actualCsvResult = new HashMap<>();
            for (String header: headers) {
                String value = record.get(header);
                actualCsvResult.put(header, value);
            }
            actualCsvResults.add(actualCsvResult);
        }

        Map<String, String> result1 = actualCsvResults.get(0);
        Map<String, String> expectedResult1 = Stream.of(new String[][] {
                { "Details", "DEBIT" },
                { "Posting Date", "12/26/2023" },
                { "Description", "ORIG CO NAME:AMERICAN EXPRESS CO ENTRY DESCR:ACH PMT    SEC:WEB IND ID:M0723           ORIG ID:3116583444" },
                { "Amount", "-62.36" },
                { "Type", "ACH_DEBIT" },
                { "Balance", " " },
                { "Check or Slip #", "" }
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        assertEquals(expectedResult1, result1);
    }

    @Test
    public void testChaseTxns() {
        String filename = "src/test/java/fixtures/csv_importer/chase_example1.csv";
        ChaseCsvProcessor chase = new ChaseCsvProcessor(mockBeansTxnDao, mockCategoryDao);
        List<BeansTransactionEntity> beansTxns = chase.parseTransactions(filename, EMAIL);
        BeansTransactionEntity bean1 = beansTxns.get(0);
        assertEquals(BeansTransactionEntity.Direction.DEBIT, bean1.getDirection());
        assertThat(bean1.getAmount(), CoreMatchers.equalTo(BigDecimal.valueOf(-62.36)));
        assertEquals(
                LocalDate.of(2023, 12, 26)
                        .atStartOfDay(ZoneId.of("America/New_York"))
                        .toInstant(),
                bean1.getEffectiveDate().toInstant()
        );
        assertEquals(
                "ORIG CO NAME:AMERICAN EXPRESS CO ENTRY DESCR:ACH PMT    SEC:WEB IND ID:M0723           ORIG ID:3116583444",
                bean1.getDescription()
        );
        assertEquals(
                "ACH_DEBIT",
                bean1.getCategory()
        );
    }

    @Test
    public void testAmexTxns() {
        String filename = "src/test/java/fixtures/csv_importer/amex_example1.csv";
        AmexCsvProcessor amex = new AmexCsvProcessor(mockBeansTxnDao, mockCategoryDao);
        List<BeansTransactionEntity> beansTxns = amex.parseTransactions(filename, EMAIL);
        BeansTransactionEntity bean1 = beansTxns.get(0);
        assertEquals(BeansTransactionEntity.Direction.DEBIT, bean1.getDirection());
        assertThat(bean1.getAmount(), CoreMatchers.equalTo(BigDecimal.valueOf(3.26)));
        assertEquals(
                LocalDate.of(2023, 12, 24)
                        .atStartOfDay(ZoneId.of("America/New_York"))
                        .toInstant(),
                bean1.getEffectiveDate().toInstant()
        );
        assertEquals(
                "AplPay APPLE.COM/BILINTERNET CHARGE     CA",
                bean1.getDescription()
        );
        assertEquals(
                "Merchandise & Supplies-Internet Purchase",
                bean1.getCategory()
        );
    }

    @Test
    public void testAmexTxnTotals() {
        String filename = "src/test/java/fixtures/csv_importer/amex_example2.csv";
        AmexCsvProcessor amex = new AmexCsvProcessor(mockBeansTxnDao, mockCategoryDao);
        amex.processFile(filename, EMAIL);

        // TODO finish
    }

//    @Test
//    public void csvParserUtilCanParseContents() throws IOException {
//        String filename = "src/test/java/fixtures/example2.csv";
//        String[] headers = Arrays.stream(CsvParserUtil.Columns.values()).map(it -> it.name()).toArray(String[]::new);
//        List<CSVRecord> recordList = CsvParserUtil.getRecordList(filename, headers);
//        assertEquals(1, recordList.size());
//        CSVRecord record = recordList.stream().findFirst().orElse(null);
//        assertEquals("Brooklyn", record.get(CsvParserUtil.Columns.site.name()).trim());
//    }
}
