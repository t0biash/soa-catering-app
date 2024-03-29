package pl.agh.kis.soa.catering.server.api;

import pl.agh.kis.soa.catering.server.model.MenuItem;
import pl.agh.kis.soa.catering.server.model.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IOrderRepository {
    void addOrder(Long clientId, Order order);
    List<Order> getAllClientOrders(Long clientId);
    List<Order> getClientOrdersBetweenDates(Long clientId, Date fromDate, Date toDate);
    List<Order> getAllUnconfirmedOrders();
    List<Order> getAllConfirmedAndNotShipPendingOrders();
    void confirmOrder(Long orderId);
    void enqueueOrderShipping(Long orderId);
    void shipOrder(Long orderId);
    Order getOrderById(Long orderId);

    void deleteOrder(Long id);

    List<Order> getAllOrders();
}
