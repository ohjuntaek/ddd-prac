package ex.marketboro.dddprac.member.domain;

import ex.marketboro.dddprac.member.dto.GoodsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

class MemberTest {
    // first Member
    private Member firstMember;
    private Shop shopOfFirstMember;
    private String goodsOneCodeOfFirstMember;
    private String goodsTwoCodeOfFirstMember;

    // second Member
    private Member secondMember;
    private Shop shopOfSecondMember;
    private String goodsOneCodeOfSecondMember;
    private String goodsTwoCodeOfSecondMember;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        setFirstMember();

        setSecondMember();
    }


    private void setFirstMember() {
        shopOfFirstMember = new Shop("shop1", "shopAdd1");
        firstMember = new Member("member1", "pw1", shopOfFirstMember);
        goodsOneCodeOfFirstMember = "goods_code_1_a";
        goodsTwoCodeOfFirstMember = "goods_code_1_b";
        makeGoods("goods_name_1_1", "goods_category_1", firstMember, goodsOneCodeOfFirstMember);
        makeGoods("goods_name_1_2", "goods_category_1", firstMember, goodsTwoCodeOfFirstMember);
    }

    private void setSecondMember() {
        shopOfSecondMember = new Shop("shop2", "shopAdd2");
        secondMember = new Member("member2", "pw2", shopOfFirstMember);
        goodsOneCodeOfSecondMember = "goods_code_2_a";
        goodsTwoCodeOfSecondMember = "goods_code_2_b";
        makeGoods("goods_name_2_1", "goods_category_2", secondMember, goodsOneCodeOfSecondMember);
        makeGoods("goods_name_2_2", "goods_category_2", secondMember, goodsTwoCodeOfSecondMember);
    }


    private void makeGoods(String name, String category, Member member, String goodsCode) {
        member.makeOwnGoods(new GoodsDTO(goodsCode, name, category));
    }


    @Test
    @DisplayName("회원 생성 테스트")
    public void testCreateMember() {
        assertThat(firstMember, not(nullValue()));
    }

    @Test
    @DisplayName("회원은 점포 정보를 가진다")
    public void testMemberField() {
        assertThat(firstMember.getShop(), not(nullValue()));
    }

    @Test
    @DisplayName("회원이 상품을 생성한다.")
    public void testGoodsListOfMember() {
        String goodsCode = "goods_name_2";
        Goods generatedGoods = firstMember.makeOwnGoods(new GoodsDTO(goodsCode, "goods_name_b", "goods_category_2"));

        assertThat(generatedGoods, is(firstMember.getGoodsMap().get(goodsCode)));
    }

    @Test
    @DisplayName("회원이 소유한 상품을 코드별로 조회한다.")
    public void testGetGoods() {
        assertThat(firstMember.getGoodsMap().get(goodsOneCodeOfFirstMember).getName(), is("goods_name_1_1"));
    }

    @Test
    @DisplayName("회원이 소유한 상품을 코드별로 수정한다.")
    public void testUpdateGoods() {
        String goodsNameUpdated = "goods_name_updated";
        String categoryUpdated = "goods_category_updated";
        Goods updatedGoods = firstMember.updateGoods(new GoodsDTO(goodsOneCodeOfFirstMember, goodsNameUpdated, categoryUpdated));
        Goods goodsInMaps = firstMember.getGoodsMap().get(goodsOneCodeOfFirstMember);
        assertThat(updatedGoods, not(nullValue()));
        assertThat(goodsInMaps.getName(), is(goodsNameUpdated));
        assertThat(goodsInMaps.getCategory(), is(categoryUpdated));
    }

    @Test
    @DisplayName("회원이 소유한 상품을 코드별로 삭제한다.")
    public void testDeleteGoods() {
        boolean isExist = firstMember.removeOwnGoods(goodsOneCodeOfFirstMember);
        assertThat(isExist, is(true));
        assertThat(firstMember.getGoodsMap().getOrDefault(goodsOneCodeOfFirstMember, null), is(nullValue()));
    }

    @Test
    @DisplayName("회원이 소유하지 않은 상품을 조회할 수 있다.")
    public void testGetOtherMemberOwnGoods() {
        Map<String, Goods> goodsListOfSecondMember = firstMember.getOtherMemberGoodsMap(secondMember);
        Goods goodsOfSecondMember = goodsListOfSecondMember.get(goodsOneCodeOfSecondMember);

        System.out.println(goodsOfSecondMember.getName());
        assertThat(goodsOfSecondMember, is(secondMember.getGoodsMap().get(goodsOneCodeOfSecondMember)));
    }

    @Test
    @DisplayName("회원이 소유하지 않은 상품은 수정할 수 없다.")
    public void testNotUpdateOtherMemberOwnGoods() {
//        Goods firstGoods = secondMember.getGoodsMap().get(goodsCodeOfSecondMember); // 조회는 가능해야 함
//        firstGoods.updateName("abc"); // 수정은 불가해야 함 => 가변 객체로 했을 시 수정 가능하게 됨
//        assertThat(firstGoods.getName(), not("abc"));

        Goods updatedGoods = secondMember.updateGoods(new GoodsDTO(goodsOneCodeOfFirstMember, "abc", "abcd"));
        assertThat(updatedGoods, is(nullValue()));
    }

    @Test
    @DisplayName("회원이 소유하지 않은 상품은 삭제할 수 없다.")
    public void testNotDeletedOtherMemberOwnGoods() {
        assertThat(secondMember.removeOwnGoods(goodsOneCodeOfFirstMember), is(false));
        assertThat(firstMember.getGoodsMap().get(goodsOneCodeOfFirstMember), not(nullValue()));
    }

}








