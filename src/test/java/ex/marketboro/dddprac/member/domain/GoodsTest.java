package ex.marketboro.dddprac.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class GoodsTest {
    Goods goods1;
    Goods goods2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        goods1 = new Goods("goods-one", "category1");
        goods2 = new Goods("goods-two", "category2");
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void testCreate() {
        new Goods("b", "c");
    }

    @Test
    @DisplayName("상품 동등성 테스트")
    public void testEquality() {
        Goods goods1 = new Goods("b", "c");
        Goods goods2 = new Goods("b", "c");
        assertThat(goods1, is(goods2));
    }


}