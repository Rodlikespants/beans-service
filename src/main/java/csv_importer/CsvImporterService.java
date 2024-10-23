package csv_importer;

import csv_importer.processors.CsvProcessor;
import db.daos.BeansTransactionDAO;
import db.daos.CategoryDAO;

import javax.inject.Inject;
import java.io.IOException;

//@Singleton
public class CsvImporterService {
    private final BeansTransactionDAO beansTransactionDao;
    private final CategoryDAO categoryDao;

    @Inject
    public CsvImporterService(BeansTransactionDAO beansTransactionDao, CategoryDAO categoryDao) {
        this.beansTransactionDao = beansTransactionDao;
        this.categoryDao = categoryDao;
    }

    public void importFile(String filename, String source, String userEmail) throws IOException {
        CsvProcessor csvProcessor = CsvProcessorFactory.createCsvProcessor(beansTransactionDao, categoryDao, source);
        csvProcessor.processFile(filename, userEmail);
    }
}
