package ex.marketboro.dddprac.member.application;

import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.domain.GoodsOnly;
import ex.marketboro.dddprac.member.domain.Member;
import ex.marketboro.dddprac.member.domain.MemberRepository;
import ex.marketboro.dddprac.member.dto.GoodsDTO;
import ex.marketboro.dddprac.orders.domain.OrderRepository;
import ex.marketboro.dddprac.orders.domain.Orders;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public MemberService(MemberRepository memberRepository, OrderRepository orderRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void approveOrder(Member buyer, Member seller, Orders order) {
        boolean isOrderSucceed = seller.approveOrder(order);
        if (isOrderSucceed) buyer.proceedApprovedOrder(order);
        // 이벤트써서 order의 상태 변경
    }

    public void declineOrder(Member buyer, Member seller, Orders order) {
        orderRepository.delete(order);
    }

    @Transactional
    public Goods createGoodsOfMember(String memberLoginId, GoodsDTO goodsDTO) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.makeOwnGoods(goodsDTO);
    }

    public Map<String, Goods> getGoodsMapOfMember(String memberLoginId) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.getGoodsMap();
    }

    public Goods getGoodsOfMemberByCode(String memberLoginId, String code) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        /*
        아니 생각해보니 Goods는 따로 관리해줘야 할려나?
        이유 1. 조회할 때 상품코드만으로 조회해줘야 하는데?
        => Goods는 테이블 따로 나오니깐 쿼리 입력하면 되긴 됨 => 아니 이거 너무 이상한데?
        이유 2. 생명주기가 같은거 같은데...
        => Member가 사라지면 그에 딸려있는 Goods도 사라지긴 함
         */
        Collection<GoodsOnly> goodsOnlyCollection = memberRepository.findGoodsByCode(code);
        List<GoodsOnly> goodsOnlyList = new ArrayList<>(goodsOnlyCollection);

        GoodsOnly goodsOnly = goodsOnlyList.get(0);

        return new Goods(goodsOnly.getName(), goodsOnly.getCategory());
    }

    @Transactional
    public Goods updateGoods(String memberLoginId, GoodsDTO goodsDTO) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.updateGoods(goodsDTO);
    }

    @Transactional
    public boolean deleteGoodsOfMemberByCode(String memberLoginId, String code) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.removeOwnGoods(code);
    }
}



