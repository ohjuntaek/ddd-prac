package ex.marketboro.dddprac.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

class MemberTest {
    Member member;
    Shop shop;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shop = new Shop("shop1", "shopAdd1");
        member = new Member("pw1", "member1", shop);
        member.makeOwnGoods("goods_code_a", "goods_name_1", "goods_category_1");
    }

    @Test
    @DisplayName("회원 생성 테스트")
    public void testCreateMember() {
        assertThat(member, not(nullValue()));
    }

    @Test
    @DisplayName("회원은 점포 정보를 가진다")
    public void testMemberField() {
        assertThat(member.getShop(), not(nullValue()));
    }

    @Test
    @DisplayName("회원이 상품을 생성한다.")
    public void testGoodsListOfMember() {
        String goodsCode = "goods_name_2";
        Goods generatedGoods = member.makeOwnGoods(goodsCode, "goods_name_b", "goods_category_2");

        assertThat(generatedGoods, is(member.getGoodsMap().get(goodsCode)));
    }

    @Test
    @DisplayName("회원이 소유한 상품을 조회한다.")
    public void testGetGoods() {
        String goodsCode = "goods_code_a";
        assertThat(member.getGoodsMap().get(goodsCode), is(new Goods("goods_name_1", "goods_category_1")));
    }

    @Test
    @DisplayName("회원이 소유한 상품을 수정한다.")
    public void testUpdateGoods() {

    }

}








