package csv_importer;

import csv_importer.processors.AmexCsvProcessor;
import csv_importer.processors.ChaseCsvProcessor;
import csv_importer.processors.CsvProcessor;
import db.daos.BeansTransactionDAO;
import db.daos.CategoriesDAO;

public class CsvProcessorFactory {
    public static CsvProcessor createCsvProcessor(BeansTransactionDAO beansTransactionDao, CategoriesDAO categoriesDao, String source) {
        if (source.equalsIgnoreCase("CHASE")) {
            return new ChaseCsvProcessor(beansTransactionDao);
        } else if (source.equalsIgnoreCase("AMEX")) {
            return new AmexCsvProcessor(beansTransactionDao, categoriesDao);
        } else {
            throw new IllegalArgumentException("Invalid source provided (i.e. must be CHASE, AMEX, etc.");
        }
    }
}
