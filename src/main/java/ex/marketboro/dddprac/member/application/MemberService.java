package ex.marketboro.dddprac.member.application;

import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.domain.Member;
import ex.marketboro.dddprac.member.domain.MemberRepository;
import ex.marketboro.dddprac.member.dto.GoodsDTO;
import ex.marketboro.dddprac.orders.domain.ApprovedOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Goods createGoodsOfMember(String memberLoginId, GoodsDTO goodsDTO) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.makeOwnGoods(goodsDTO);
    }

    public Map<String, Goods> getGoodsMapOfMember(String memberLoginId) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.getGoodsMap();
    }

    public Goods getGoodsByCode(String memberLoginId, String code) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        return member.getGoodsMap().get(code);
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

    public Map<String, Goods> getGoodsMapOfOtherMember(String memberLoginId, String otherMemberLoginId) {
        Member member = memberRepository.findMemberByLoginId(memberLoginId);
        Member otherMember = memberRepository.findMemberByLoginId(otherMemberLoginId);
        return member.getOtherMemberGoodsMap(otherMember);
    }

    // in terms of seller

    @EventListener
    public void approveOrder(final ApprovedOrderEvent event) {
        final Member seller = memberRepository.findMemberByLoginId(event.getMemberLoginId());

        Map<String, Goods> goodsMapInOrder = seller.getGoodsMap().entrySet().stream()
                .filter((entry) -> {
                    List<String> goodsCodeList = event.getGoodsCodeList();
                    return goodsCodeList.stream()
                            .anyMatch(Predicate.isEqual(entry.getKey()));
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        seller.approveOrder(goodsMapInOrder);

        final Member buyer = memberRepository.findMemberByLoginId(event.getBuyerId());
        buyer.addOrderItems(goodsMapInOrder);
    }


//    public void declineOrder(Member buyer, Member seller, Orders order) {
//        orderRepository.delete(order);
//    }
}



