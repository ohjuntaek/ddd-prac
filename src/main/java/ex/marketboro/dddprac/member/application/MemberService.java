package ex.marketboro.dddprac.member.application;

import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.domain.Member;
import ex.marketboro.dddprac.member.domain.MemberRepository;
import ex.marketboro.dddprac.member.dto.GoodsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

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


//    public void declineOrder(Member buyer, Member seller, Orders order) {
//        orderRepository.delete(order);
//    }
}



