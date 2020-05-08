package ex.marketboro.dddprac.orders.application;

import ex.marketboro.dddprac.orders.domain.ApprovedOrderEvent;
import ex.marketboro.dddprac.orders.domain.OrderRepository;
import ex.marketboro.dddprac.orders.domain.Orders;
import ex.marketboro.dddprac.orders.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public String order(String memberLoginId, String sellerId, OrderDTO orderDTO) {
        Orders order = Orders.order(sellerId, memberLoginId, orderDTO);
        orderRepository.save(order);
        return "generated order ok, wait seller's response";
    }

    public List<Orders> getOrdersOfMember(String memberLoginId) {
        return orderRepository.findOrdersByBuyerId(memberLoginId); // 봐라 이런것 땜에 멤버 안으로 들어가야한다... 판매자 조회를 깔끔하게 못하잖아
    }

    @Transactional
    public boolean approveOrder(String memberLoginId, Long orderId) {

        // 자기 주문이 아니면 안되게 하는 검사 로직 or 뭔가 아예 접근 못하게 캡슐화..?? 추후

        Orders order = orderRepository.findById(orderId).get();

        applicationEventPublisher.publishEvent(new ApprovedOrderEvent(memberLoginId, order.getGoodsCodeList(), order.getBuyerId()));

        return true; // approve 하면 조건 없이 무조건 주문 승인 되므로 일단 true로
    }

}
