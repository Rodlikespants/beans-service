package csv_importer.processors;

import db.daos.BeansTransactionDAO;
import db.daos.CategoriesDAO;
import db.entities.categories.CategoryEntity;
import db.entities.transactions.BeansTransactionEntity;
import db.entities.transactions.third_party.AmexTransactionEntity;
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
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

public class AmexCsvProcessor implements CsvProcessor {
    private final BeansTransactionDAO beansTxnDao;
    private final CategoriesDAO categoriesDao;
    String[] headers = {
            "Date",
            "Description",
            "Amount",
            "Extended Details",
            "Appears On Your Statement As",
            "Address",
            "City/State",
            "Zip Code",
            "Country",
            "Reference",
            "Category"
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(AmexCsvProcessor.class);

//    @Inject
    public AmexCsvProcessor(BeansTransactionDAO beansTxnDao, CategoriesDAO categoriesDao) {
        this.beansTxnDao = beansTxnDao;
        this.categoriesDao = categoriesDao;
    }

    @Override
    public void processFile(String filename, String userEmail) {
        // TODO maybe don't need to take email, just create the user if it doesn't exist and pass the id

        // TODO group by a new id based on effective month (join table?) or just effective month and erase/make previous transactions inactive
        List<BeansTransactionEntity> beansTransactions = parseTransactions(filename, userEmail);
        for (BeansTransactionEntity beansTxn: beansTransactions) {
            beansTxnDao.saveUnique(beansTxn);
        }

        Map<String, List<BeansTransactionEntity>> txnsByCategory = beansTransactions.stream()
                .collect(groupingBy(BeansTransactionEntity::getCategory));

        for (String category: txnsByCategory.keySet()) {
            categoriesDao.addCategory(category);
            List<BeansTransactionEntity> txns = txnsByCategory.get(category);
            BigDecimal total = BigDecimal.ZERO;
            for (BeansTransactionEntity txn: txns) {
                if (txn.getDirection() == BeansTransactionEntity.Direction.CREDIT) {
                    total = total.add(txn.getAmount().multiply(BigDecimal.valueOf(-1)));
                } else {
                    total = total.add(txn.getAmount());
                }
            }
            LOGGER.info("${} dollars spent in Category={} with {} transactions", total, category, txns.size());
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
            LOGGER.warn("Unable to parse American Express CSV file={}, e={}", filename, e.getMessage());
            return Collections.emptyList();
        }

        List<CSVRecord> records = StreamSupport.stream(csvParserResults.spliterator(), false).toList();

        List<AmexTransactionEntity> amexTxns = parseAmexTransactions(records);

        return amexTxns.stream().map(AmexCsvProcessor::amexToBeanTxn).toList();
    }

    public static List<AmexTransactionEntity> parseAmexTransactions(List<CSVRecord> records) {
        return records.stream().map(AmexCsvProcessor::recordToAmexTxn).toList();
    }

    public static AmexTransactionEntity recordToAmexTxn(CSVRecord csvRecord) {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        String effectiveDateStr = csvRecord.get("Date");
        Date effectiveDate = null;
        try {
            effectiveDate = formatter.parse(effectiveDateStr);
        } catch (ParseException e) {
            LOGGER.warn("American Express Transaction Date={} was not able to be parsed, e={}", effectiveDateStr, e.getMessage());
            return null;
        }

        String amountStr = csvRecord.get("Amount");
        BigDecimal amount = amountStr.isBlank() ? null : new BigDecimal(amountStr);

        return new AmexTransactionEntity(
                null,
                csvRecord.get("Extended Details"),
                effectiveDate,
                csvRecord.get("Description"),
                amount,
                csvRecord.get("Category"),
                csvRecord.get("Appears On Your Statement As"),
                csvRecord.get("Address"),
                csvRecord.get("City/State"),
                csvRecord.get("Zip Code"),
                csvRecord.get("Country"),
                csvRecord.get("Reference")
        );
    }

    public static BeansTransactionEntity amexToBeanTxn(AmexTransactionEntity amexTxn) {
        return new BeansTransactionEntity(
                null,
                1L, // TODO fix
                BeansTransactionEntity.Direction.DEBIT,
                amexTxn.getAmount(),
                amexTxn.getDate(),
                amexTxn.getDescription(),
                amexTxn.getCategory(), // TODO change this to real category processing later,
                BeansTransactionEntity.Source.AMEX,
                true
        );
    }
}
