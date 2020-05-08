package ex.marketboro.dddprac.member.domain;

import ex.marketboro.dddprac.member.dto.GoodsDTO;
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
    private final Map<String, Goods> goodsMap = new HashMap<>();

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

    public void addOrderItems(Map<String, Goods> goodsMapInOrder) {
        goodsMapInOrder.forEach(goodsMap::put);
    }

    // ==== in terms of seller

    public void approveOrder(Map<String, Goods> goodsMapInOrder) {
        if (!hasAllGoods(goodsMapInOrder)) return;

        goodsMap.entrySet()
                .removeIf((entry) -> goodsMapInOrder.containsKey(entry.getKey()));

    }

    private boolean hasAllGoods(Map<String, Goods> orderedGoodsMap) {
        return orderedGoodsMap.entrySet().stream()
                .allMatch((entry) -> this.goodsMap.containsKey(entry.getKey()));
    }
}
