package ex.marketboro.dddprac.orders.domain;

import ex.marketboro.dddprac.orders.dto.OrderDTO;
import lombok.Getter;
import org.springframework.data.annotation.AccessType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@AccessType(AccessType.Type.FIELD)
public class Orders {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    private String description;

    @Getter
    private String buyerId;

    @Getter
    @ElementCollection
    private List<String> goodsCodeList;

    @Getter
    private String sellerId;

    protected Orders() {
    }

    public Orders(final String description, final String buyerId, final String sellerId, final List<String> goodsCodeList) {
        this.description = description;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.goodsCodeList = goodsCodeList;
    }

    public static Orders order(String sellerId, String buyerId, OrderDTO orderDTO) {
        return new Orders(orderDTO.getDescription(), buyerId, sellerId, orderDTO.getGoodsCodes());
    }
}

// TODO : Member -(buyer)-< Order >-(seller)- 해주는 다대다 연관매핑을 적용해서 멤버 안으로 넣고 order 관련 메소드 다 바꾸기
/*
    생각해보면 Order는 buyer와 seller에 종속된 엔터티인거 같은데..
    왜냐하면 PK를 따로 가질 필요 없이 buyer,seller를 외래키로 해서 가져오면 되잖아.

    근데 지금 다대다 할 줄 모르니깐 그냥 따로 애그리거트로 두자.
 */

/*
    궁금한게, 그럼 order가 member와 따로 나오는 경우가 있나?
    order가 buyer, seller가 삭제되도 그대로 유지된다고 하면, 따로 나올 수 있을 것 같긴 한데...
    그럼 자기꺼 조회할 때, 남의꺼 조회 못하게 할 때 검증 로직을 따로 둬야하는거 같은데 id 참조밖에 안되니깐... 별론데
 */