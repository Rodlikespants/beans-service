package csv_importer;

import app.App;
import models.ParkingVisitTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

/**
 * Metropolis instructions:
 *
 * Here are some instructions for your interview:
 * We will provide you a CSV file consisting of parking data (example: entry/exit times, total price, user ID, site, ID, etc..)
 * We will ask you to read the data into memory and answer questions about the data using the programming language of your choice
 * You can use any IDE or editor of your choice via screen share, and are free to use online resources, libraries, etc.
 * Communicate what you’re building and be open to suggestions and have fun (most important)
 * We will expect you to have a Hello World setup in the environment of your choice ready to go to parse the CSV file. That means having your favorite library/package ready to go for reading CSV files, i.e. we expect that you will already know how to use the library for CSV parsing and will need very little time, if any, looking up how to use it.
 */
public class CsvParserUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvParserUtil.class);

    /**
     *
     * @param filename
     * @param headers an array of column names
     * @return
     */
    public static Iterable<CSVRecord> getRecords(String filename, String[] headers) throws IOException {
        Reader in = new FileReader(filename);
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .setSkipHeaderRecord(true)
                .build();

        return csvFormat.parse(in);
    }

    public static List<CSVRecord> getRecordList(String filename, String[] headers) throws IOException {
        Iterable<CSVRecord> recordIter = getRecords(filename, headers);
        return StreamSupport.stream(recordIter.spliterator(), false).toList();
    }

    public static Map<Long, BigDecimal> calculateRevenueBySite(List<ParkingVisitTransaction> txns) {
        return txns.stream()
                .filter(pt -> pt.getPaymentStatus() == ParkingVisitTransaction.PaymentStatus.paymentCompleted)
                .collect(groupingBy(ParkingVisitTransaction::getSiteId,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                ParkingVisitTransaction::getPrice,
                                BigDecimal::add
                        )
                ));
    }

    public static Map<Long, Integer> calculateMaxOccupancyBySite(List<ParkingVisitTransaction> txns) {
        Map<Long, List<ParkingVisitTransaction>> txnsBySite = txns.stream().collect(groupingBy(ParkingVisitTransaction::getSiteId));
        Map<Long, Integer> maxOccupancyBySite = new HashMap<>();
        for (Long siteId : txnsBySite.keySet()) {
            List<ParkingVisitTransaction> siteTxns = txnsBySite.get(siteId);
            // sort by entry time
            PriorityQueue<DateTime> minHeap = new PriorityQueue<>();
            for (ParkingVisitTransaction siteTxn: siteTxns) {
                if (!minHeap.isEmpty() && siteTxn.getEntryTime().compareTo(minHeap.peek()) > 0) {
                    minHeap.poll();
                }
                minHeap.offer(siteTxn.getExitTime());
            }
            maxOccupancyBySite.put(siteId, minHeap.size());
        }

        return maxOccupancyBySite;
    }

    public static List<ParkingVisitTransaction> transformRecordsToTxns(List<CSVRecord> recordList) {
        return recordList.stream()
                .map(record -> new ParkingVisitTransaction(
                        Long.parseLong(record.get("transaction_id")),
                        Long.parseLong(record.get("site_id")),
                        Long.parseLong(record.get("user_id")),
                        Long.parseLong(record.get("vehicle_id")),
                        ParkingVisitTransaction.PaymentStatus.convert(record.get("payment_status")),
                        new DateTime(record.get("entry_time")),
                        new DateTime(record.get("exit_time")),
                        new BigDecimal(record.get("price"))))
                .toList();
    }

    public static void main(String[] args) {
        String filename = "src/test/java/fixtures/metropolis_visit_data.csv";
        List<CSVRecord> recordList = new ArrayList<>();
        try {
            String[] headers = new String[] {"transaction_id", "site_id", "user_id","vehicle_id","payment_status","entry_time","exit_time","price"};
            recordList = CsvParserUtil.getRecordList(filename, headers);
        } catch (IOException e) {
            // ...
        }

        List<ParkingVisitTransaction> txns = transformRecordsToTxns(recordList);
//        for (CSVRecord record: records) {
//            ParkingVisitTransaction pt = new ParkingVisitTransaction(
//                    Long.parseLong(record.get("transaction_id")),
//                    Long.parseLong(record.get("site_id")),
//                    Long.parseLong(record.get("user_id")),
//                    Long.parseLong(record.get("vehicle_id")),
//                    ParkingVisitTransaction.PaymentStatus.convert(record.get("payment_status")),
//                    new DateTime(record.get("entry_time")),
//                    new DateTime(record.get("exit_time")),
//                    new BigDecimal(record.get("price"))
//                            //.replaceAll("[$]", "")),
//            );
//            txns.add(pt);
//        }

        // determine the total revenue for each site taking into account payment status

        Map<Long, BigDecimal> revenueBySite = calculateRevenueBySite(txns);
        LOGGER.info("Total revenue map: " + revenueBySite);

        Map<Long, Integer> maxOccupancyBySite = calculateMaxOccupancyBySite(txns);
        LOGGER.info("Max occupancy by site: " + maxOccupancyBySite);
    }

    // TODO use String constants instead
    public enum Columns {
        id("ID"),
        entryTime("entry time"),
        exitTime("exit time"),
        totalPrice("total price"),
        userId("user ID"),
        site("site");

        private final String name;

        Columns(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
