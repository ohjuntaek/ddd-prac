package ex.marketboro.dddprac.orders.application;

import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.domain.Member;
import ex.marketboro.dddprac.member.domain.MemberRepository;
import ex.marketboro.dddprac.orders.domain.OrderRepository;
import ex.marketboro.dddprac.orders.domain.Orders;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void order() {
        Member buyer = memberRepository.findMemberByLoginId("buyer");
        Member seller = memberRepository.findMemberByLoginId("seller");
        Map<String, Goods> goodsMap = seller.getGoodsMap();

        Orders order = new Orders(1L, "order_desc_1", null, goodsMap);
        orderRepository.save(order);
        buyer.order(seller, order);
    }

}
