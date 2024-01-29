package csv_importer;

import csv_importer.processors.CsvProcessor;
import db.daos.BeansTransactionDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

//@Singleton
public class CsvImporterService {
    private final BeansTransactionDAO beansTransactionDAO;

    @Inject
    public CsvImporterService(BeansTransactionDAO beansTransactionDAO) {
        this.beansTransactionDAO = beansTransactionDAO;
    }

    public void importFile(String filename, String source, String userEmail) throws IOException {
        CsvProcessor csvProcessor = CsvProcessorFactory.createCsvProcessor(beansTransactionDAO, source);
        csvProcessor.processFile(filename, userEmail);
    }
}
