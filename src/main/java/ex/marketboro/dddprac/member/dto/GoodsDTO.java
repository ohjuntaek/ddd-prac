package ex.marketboro.dddprac.member.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDTO {
    private String code;
    private String name;
    private String category;
}
