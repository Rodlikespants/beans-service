package services;

import db.OrderDAO;
import db.entities.OrderEntity;
import io.dropwizard.jersey.params.LongParam;
import models.Order;
import models.Source;

import javax.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDAO orderDAO;

    @Inject
    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Optional<Order> findById(long id) {
        Optional<OrderEntity> orderEntity = orderDAO.findById(id);
        return orderEntity.flatMap(it -> Optional.of(toOrder(it)));
    }

    public List<Order> findBySource(Optional<String> source) {
        if (source != null && source.isPresent()) {
            return orderDAO.findBySource(source.get()).stream().map(OrderService::toOrder).toList();
        } else {
            return orderDAO.findAll().stream().map(OrderService::toOrder).toList();
        }
    }

    private static Order toOrder(OrderEntity entity) {
        Source source = Source.valueOf(entity.getSource());
        return new Order(entity.getId(), source);
    }
}
