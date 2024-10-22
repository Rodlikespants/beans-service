package csv_importer;

import csv_importer.processors.CsvProcessor;
import db.daos.BeansTransactionDAO;
import db.daos.CategoriesDAO;

import javax.inject.Inject;
import java.io.IOException;

//@Singleton
public class CsvImporterService {
    private final BeansTransactionDAO beansTransactionDao;
    private final CategoriesDAO categoriesDao;

    @Inject
    public CsvImporterService(BeansTransactionDAO beansTransactionDao, CategoriesDAO categoriesDao) {
        this.beansTransactionDao = beansTransactionDao;
        this.categoriesDao = categoriesDao;
    }

    public void importFile(String filename, String source, String userEmail) throws IOException {
        CsvProcessor csvProcessor = CsvProcessorFactory.createCsvProcessor(beansTransactionDao, categoriesDao, source);
        csvProcessor.processFile(filename, userEmail);
    }
}
