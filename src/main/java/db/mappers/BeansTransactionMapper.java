package db.mappers;

import db.entities.transactions.BeansTransactionEntity;
import models.transactions.BeansTransaction;

public class BeansTransactionMapper {
    public static BeansTransaction toBeansTransaction(BeansTransactionEntity entity) {
        return new BeansTransaction(
                entity.getId(),
                entity.getUserId(),
                BeansTransaction.Direction.valueOf(entity.getDirection().name()),
                entity.getAmount(),
                entity.getEffectiveDate(),
                entity.getDescription(),
                entity.getCategory(),
                BeansTransaction.Source.valueOf(entity.getSource().name()),
                entity.isActive()
        );
    }
}
