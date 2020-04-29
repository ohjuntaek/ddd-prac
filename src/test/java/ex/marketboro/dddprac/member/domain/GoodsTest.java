package ex.marketboro.dddprac.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class GoodsTest {
    Goods goods1;
    Goods goods2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        goods1 = new Goods("goods-code-a", "goods-one", "category1");
        goods2 = new Goods("goods-code-a", "goods-one", "category1");
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void testCreate() {

    }


}