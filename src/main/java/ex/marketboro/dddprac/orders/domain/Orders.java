package ex.marketboro.dddprac.orders.domain;

import ex.marketboro.dddprac.member.domain.Goods;
import lombok.Getter;
import org.springframework.data.annotation.AccessType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Map;

@Entity
@AccessType(AccessType.Type.FIELD)
public class Orders {
    // TODO : Member -(buyer)-< Order >-(seller)- 해주는 다대다 연관매핑을 적용해서 order 관련 메소드 다 바꾸기

    @Id
    private Long id;

    private String description;

    private Boolean isSellerApproved;

    @Transient
    @Getter
    private Map<String, Goods> goodsMap;

    public Orders(final Long id) {
        this(id, "", null, null);
    }

    public Orders(final Long id, final String description, Boolean isSellerApproved, Map<String, Goods> goodsMap) {
        this.id = id;
        this.description = description;
        this.isSellerApproved = isSellerApproved;
        this.goodsMap = goodsMap;
    }


    protected Orders() {
    }

}
