package ex.marketboro.dddprac.orders.application;

import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.domain.Member;
import ex.marketboro.dddprac.member.domain.MemberRepository;
import ex.marketboro.dddprac.orders.domain.OrderRepository;
import ex.marketboro.dddprac.orders.domain.Orders;
import ex.marketboro.dddprac.orders.dto.OrderDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class OrderService {
    // TODO : 이벤트를 발생 시켜서 memberRepository를 의존하지 않게 해보자.
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public String order(String memberLoginId, String sellerId, OrderDTO orderDTOs) {
        Member buyer = memberRepository.findMemberByLoginId(memberLoginId);

        Orders order = buyer.order(sellerId, orderDTOs);
        orderRepository.save(order);
        return "generated order ok, wait seller's response";
    }

    public List<Orders> getOrdersOfMember(String memberLoginId) {
        return orderRepository.findOrdersByBuyerId(memberLoginId); // 봐라 이런것 땜에 멤버 안으로 들어가야한다... 판매자 조회를 깔끔하게 못하잖아
    }

    @Transactional
    public boolean approveOrder(String memberLoginId, Long orderId) {
        Member seller = memberRepository.findMemberByLoginId(memberLoginId);
        Orders order = orderRepository.findById(orderId).get();
        Map<String, Goods> goodsMapInOrder = filterGoodsMapInSeller(seller, order);

        boolean isSellerOk = seller.approveOrder(order, goodsMapInOrder);
        Member buyer = memberRepository.findMemberByLoginId(order.getBuyerId());

        boolean isBuyerOk = buyer.addOrderItems(goodsMapInOrder);

        return isSellerOk && isBuyerOk;
    }

    private Map<String, Goods> filterGoodsMapInSeller(Member seller, Orders order) {
        return seller.getGoodsMap().entrySet().stream()
                .filter((entry) -> order.getGoodsCodeList().stream().anyMatch(Predicate.isEqual(entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
