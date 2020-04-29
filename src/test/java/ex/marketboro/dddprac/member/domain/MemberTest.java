package ex.marketboro.dddprac.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

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

}