package ex.marketboro.dddprac.member.dto;

import ex.marketboro.dddprac.member.domain.Goods;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDTO {
    private String code;
    private String name;
    private String category;

    public Goods makeGoods() {
        return new Goods(this.name, this.category);
    }
}
