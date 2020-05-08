package ex.marketboro.dddprac.orders.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private String description;
    private List<String> goodsCodes;

}
