package csv_importer.processors;

import app.App;
import db.entities.transactions.BeansTransactionEntity;
import db.entities.transactions.third_party.AmexTransactionEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

public class AmexCsvProcessor implements CsvProcessor {
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

    public AmexCsvProcessor() {}

    @Override
    public void processFile(String filename) {
        List<BeansTransactionEntity> beansTransactions = parseTransactions(filename);

    }

    @Override
    public List<BeansTransactionEntity> parseTransactions(String filename) {
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

        // TODO persist Chase transactions

//        List<BeansTransactionEntity> beansTxns = chaseTxns.stream().map(it -> it)
        return null;
    }

    public static List<AmexTransactionEntity> parseAmexTransactions(List<CSVRecord> records) {
        return records.stream().map(AmexCsvProcessor::recordToAmexTxn).toList();
    }

    public static AmexTransactionEntity recordToAmexTxn(CSVRecord csvRecord) {
        Date postingDate = Date.from(
                LocalDate.parse(csvRecord.get("postingDate"))
                        .atStartOfDay(ZoneId.of("America/New_York"))
                        .toInstant()
        );

        String amountStr = csvRecord.get("amount");
        BigDecimal amount = !amountStr.isBlank() ? null : new BigDecimal(amountStr);
        String balanceStr = csvRecord.get("balance");
        BigDecimal balance = !balanceStr.isBlank() ? null : new BigDecimal(balanceStr);

        String checkNumberStr = csvRecord.get("checkOrSlipNumber");
        Integer checkNumber = checkNumberStr.matches("[0-9]+") ? Integer.valueOf(checkNumberStr) : null;
//        return new AmexTransactionEntity(
//                null,
//                csvRecord.get("details"),
//                postingDate,
//                csvRecord.get("description"),
//                amount,
//                csvRecord.get("type"),
//                balance,
//                checkNumber
//        );
        return null;
    }

    public static BeansTransactionEntity chaseToBeanTxn(AmexTransactionEntity amexTxn) {
//        BeansTransactionEntity.Direction direction = amexTxn.getDetails().equalsIgnoreCase("CREDIT")
//                ? BeansTransactionEntity.Direction.CREDIT
//                : BeansTransactionEntity.Direction.DEBIT;
//        return new BeansTransactionEntity(
//                null,
//                direction,
//                amexTxn.getAmount(),
//                amexTxn.getPostingDate(),
//                amexTxn.getDescription(),
//                "MISC" // TODO change this to real category processing later
//        );
        return null;
    }
}
