package csv_importer;

import csv_importer.processors.CsvProcessor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CsvImporterService {

    public CsvImporterService() {

    }

    public void importFile(String filename, String source) throws IOException {
        CsvProcessor csvProcessor = CsvProcessorFactory.createCsvProcessor(source);
        csvProcessor.processFile(filename);
    }
}
