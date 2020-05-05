package ex.marketboro.dddprac.member.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
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


    protected Member() {
    }

    public Member(String loginId, String password, Shop shop) {
        this.loginId = loginId;
        this.password = password;
        this.shop = shop;
    }


    public Goods makeOwnGoods(String code, String name, String category) {
        if (goodsMap.containsKey(code)) return goodsMap.get(code);

        Goods newGoods = new Goods(name, category);
        goodsMap.put(code, newGoods);
        return newGoods;
    }

    public Goods updateGoods(String goodsCode, String goodsName, String goodsCategory) {

        if (!goodsMap.containsKey(goodsCode)) return null;

        Goods beforeGoods = goodsMap.get(goodsCode);
        if (isSameGoods(goodsName, goodsCategory, beforeGoods)) return null;

        Goods updateGoods = new Goods(goodsName, goodsCategory);
        goodsMap.put(goodsCode, new Goods(goodsName, goodsCategory));
        // goods는 불변 객체이므로 새로 생성해서 사용
        // 이렇게 해서 회원은 자기 자신 이외의 상품에 대해 조회는 가능하게, 수정은 불가능하게 할 수 있음.
        return updateGoods;
    }

    private boolean isSameGoods(String goodsName, String goodsCategory, Goods beforeGoods) {
        return beforeGoods.getName().equals(goodsName) && beforeGoods.getCategory().equals(goodsCategory);
    }

    public boolean deleteGoods(String goodsCodeOfFirstMember) {
        Goods deletedGoods = goodsMap.remove(goodsCodeOfFirstMember);
        return deletedGoods != null;
    }

    protected Map<String, Goods> getOtherMemberGoodsList(Member otherMember) {
        return otherMember.getGoodsMap();
    }
}
