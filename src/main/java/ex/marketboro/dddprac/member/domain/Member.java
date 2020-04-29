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
    private Map<String, Goods> goodsMap = new HashMap();


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

    public void updateGoods(String goods_code_a, String goods_name_11, String goods_category_11) {
    }
}
