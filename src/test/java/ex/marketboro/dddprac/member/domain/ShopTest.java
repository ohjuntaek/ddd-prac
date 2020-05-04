package ex.marketboro.dddprac.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShopTest {

    Shop shop;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shop = new Shop("shop1", "address1");

    }

    @Test
    @DisplayName("shop_name은 10자가 넘으면 안된다.")
    public void testShopNameLessThanTen() {
        assertThrows(IllegalArgumentException.class, () -> {
            Shop shopMoreThanTen = new Shop("aaaaabbbbbc", "address2");
        });
    }


    @Test
    @DisplayName("shop 동등성 테스트")
    public void testEqual() {
        Shop newShop = new Shop("shop1", "address1");

        assertThat(shop, is(newShop));

        Shop otherShop = new Shop("shop2", "address1");

        assertThat(shop, not(otherShop));
    }
}