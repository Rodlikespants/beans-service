package csv_importer.processors;

import db.daos.BeansTransactionDAO;
import db.daos.categories.CategoryDAO;
import db.entities.transactions.BeansTransactionEntity;
import db.entities.transactions.third_party.ChaseTransactionEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.StreamSupport;

public class ChaseCsvProcessor implements CsvProcessor {
    private final BeansTransactionDAO beansTxnDao;
    private final CategoryDAO categoryDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChaseCsvProcessor.class);
    private final String[] headers = {
            "Details",
            "Posting Date",
            "Description",
            "Amount",
            "Type",
            "Balance",
            "Check or Slip #"
    };

//    @Inject
    public ChaseCsvProcessor(BeansTransactionDAO beansTxnDao, CategoryDAO categoryDao) {
        this.beansTxnDao = beansTxnDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public void processFile(String filename, String userEmail) {
        List<BeansTransactionEntity> beansTransactions = parseTransactions(filename, userEmail);
        for (BeansTransactionEntity beansTxn: beansTransactions) {
            beansTxnDao.saveUnique(beansTxn);
        }
    }

    @Override
    public List<BeansTransactionEntity> parseTransactions(String filename, String userEmail) {
        CSVParser csvParserResults = null;
        try {
            FileReader in = new FileReader(filename);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(headers)
                    .setSkipHeaderRecord(true)
                    .build();

            csvParserResults = csvFormat.parse(in);
        } catch (IOException e) {
            LOGGER.warn("Unable to parse Chase CSV file={}, e={}", filename, e.getMessage());
            return Collections.emptyList();
        }

        List<CSVRecord> records = StreamSupport.stream(csvParserResults.spliterator(), false).toList();

        List<ChaseTransactionEntity> chaseTxns = parseChaseTransactions(records);

        // TODO persist Chase transactions
        // TODO maybe save all the transactions in one bulk db transaction?
        // TODO save categories

        return chaseTxns.stream().map(ChaseCsvProcessor::chaseToBeanTxn).toList();
    }

    public static List<ChaseTransactionEntity> parseChaseTransactions(List<CSVRecord> records) {
        return records.stream().map(ChaseCsvProcessor::recordToChaseTxn).toList();
    }

    public static ChaseTransactionEntity recordToChaseTxn(CSVRecord csvRecord) {
        String postingDateStr = csvRecord.get("Posting Date");
        LocalDate postingDate = null;
        postingDate = LocalDate.parse(postingDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH));

        String amountStr = csvRecord.get("Amount");
        BigDecimal amount = amountStr.isBlank() ? null : new BigDecimal(amountStr);
        String balanceStr = csvRecord.get("Balance");
        BigDecimal balance = balanceStr.isBlank() ? null : new BigDecimal(balanceStr);

        String checkNumberStr = csvRecord.get("Check or Slip #");
        Integer checkNumber = checkNumberStr.matches("[0-9]+") ? Integer.valueOf(checkNumberStr) : null;
        return new ChaseTransactionEntity(
                null,
                csvRecord.get("Details"),
                postingDate,
                csvRecord.get("Description"),
                amount,
                csvRecord.get("Type"),
                balance,
                checkNumber
        );
    }

    public static BeansTransactionEntity chaseToBeanTxn(ChaseTransactionEntity chaseTxn) {
        BeansTransactionEntity.Direction direction = chaseTxn.getDetails().equalsIgnoreCase("CREDIT")
                ? BeansTransactionEntity.Direction.CREDIT
                : BeansTransactionEntity.Direction.DEBIT;
        return new BeansTransactionEntity(
                null,
                1L, // TODO fix
                direction,
                chaseTxn.getAmount(),
                chaseTxn.getPostingDate(),
                chaseTxn.getDescription(),
                chaseTxn.getType(), // TODO change this to real category processing later
                BeansTransactionEntity.Source.CHASE,
                true
        );
    }
}
