package csv_importer;

import csv_importer.processors.AmexCsvProcessor;
import csv_importer.processors.ChaseCsvProcessor;
import csv_importer.processors.CsvProcessor;

public class CsvProcessorFactory {
    public static CsvProcessor createCsvProcessor(String source) {
        if (source.equalsIgnoreCase("CHASE")) {
            return new ChaseCsvProcessor();
        } else if (source.equalsIgnoreCase("AMEX")) {
            return new AmexCsvProcessor();
        } else {
            throw new IllegalArgumentException("Invalid source provided (i.e. must be CHASE, AMEX, etc.");
        }
    }
}
