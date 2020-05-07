package ex.marketboro.dddprac.member.domain;

import ex.marketboro.dddprac.member.dto.GoodsDTO;
import ex.marketboro.dddprac.orders.domain.Orders;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@EqualsAndHashCode(of = {"id"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "shop_name")),
            @AttributeOverride(name = "address", column = @Column(name = "shop_address"))
    })
    private Shop shop;

    @Getter
    @ElementCollection
    private final Map<String, Goods> goodsMap = new HashMap();

//    private MemberService memberService;

    protected Member() {
    }

    public Member(String loginId, String password, Shop shop) {
        this.loginId = loginId;
        this.password = password;
        this.shop = shop;
    }


    public Goods makeOwnGoods(GoodsDTO goodsDTO) {
        if (goodsMap.containsKey(goodsDTO.getCode())) return goodsMap.get(goodsDTO.getCode());

        Goods newGoods = new Goods(goodsDTO.getName(), goodsDTO.getCategory());
        goodsMap.put(goodsDTO.getCode(), newGoods);
        return newGoods;
    }

    public Goods updateGoods(GoodsDTO goodsDTO) {

        if (!goodsMap.containsKey(goodsDTO.getCode())) return null;

        Goods beforeGoods = goodsMap.get(goodsDTO.getCode());
        if (isSameGoods(goodsDTO.getName(), goodsDTO.getCategory(), beforeGoods)) return null;

        Goods updateGoods = new Goods(goodsDTO.getName(), goodsDTO.getCategory());
        goodsMap.put(goodsDTO.getCode(), new Goods(goodsDTO.getName(), goodsDTO.getCategory()));
        // goods는 불변 객체이므로 새로 생성해서 사용
        // 이렇게 해서 회원은 자기 자신 이외의 상품에 대해 조회는 가능하게, 수정은 불가능하게 할 수 있음.
        return updateGoods;
    }

    private boolean isSameGoods(String goodsName, String goodsCategory, Goods beforeGoods) {
        return beforeGoods.getName().equals(goodsName) && beforeGoods.getCategory().equals(goodsCategory);
    }

    public boolean removeOwnGoods(String code) {
        Goods deletedGoods = goodsMap.remove(code);
        return deletedGoods != null;
    }

    public Map<String, Goods> getOtherMemberGoodsMap(Member otherMember) {
        return otherMember.getGoodsMap();
    }

    // ==== in terms of buyer
    public void order(Member seller, Orders order) {
        seller.ordered(this, order);
    }

    public void proceedApprovedOrder(Orders order) {
        order.getGoodsMap().forEach(this.goodsMap::put);
    }

    // ==== in terms of seller
    private boolean ordered(Member buyer, Orders order) {
        // TODO : 주문에 대해 승낙할 건지 거절할 건지 선택해라는 이벤트 발생시켜라
        // 승낙
//        memberService.approveOrder(buyer, this, order);
        return true;

//        // 거절
//        memberService.declineOrder(buyer, this, order);
//        return false;
    }

    public boolean approveOrder(Orders order) {
        Map<String, Goods> orderedGoodsMap = order.getGoodsMap();
        if (!hasAllGoods(orderedGoodsMap)) return false;

        goodsMap.entrySet()
                .removeIf((entry) -> orderedGoodsMap.containsKey(entry.getKey()));

        return true;
    }

    private boolean hasAllGoods(Map<String, Goods> orderedGoodsMap) {
        return orderedGoodsMap.entrySet().stream()
                .allMatch((entry) -> this.goodsMap.containsKey(entry.getKey()));
    }

}
