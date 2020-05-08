package ex.marketboro.dddprac.orders.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findOrdersByBuyerId(String buyerId);

    List<Orders> findOrdersBySellerId(String buyerId);
}
