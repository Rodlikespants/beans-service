package csv_importer.processors;

import db.entities.transactions.BeansTransactionEntity;

import java.io.IOException;
import java.util.List;

public interface CsvProcessor {
    void processFile(String filename);

    List<BeansTransactionEntity> parseTransactions(String filename);
}
