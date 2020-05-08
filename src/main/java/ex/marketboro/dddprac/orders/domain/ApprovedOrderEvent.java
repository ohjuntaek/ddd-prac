package ex.marketboro.dddprac.orders.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApprovedOrderEvent {
    @Getter
    final private String memberLoginId;
    @Getter
    final private List<String> goodsCodeList;
    @Getter
    final private String buyerId;
}
