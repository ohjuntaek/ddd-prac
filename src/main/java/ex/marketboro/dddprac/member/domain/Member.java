package ex.marketboro.dddprac.member.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "shop_name")),
            @AttributeOverride(name = "address", column = @Column(name = "shop_address"))
    })
    private Shop shop;

    protected Member() {
    }

    public Member(String loginId, String password, Shop shop) {
        this.loginId = loginId;
        this.password = password;
        this.shop = shop;
    }
}
