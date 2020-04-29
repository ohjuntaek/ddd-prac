package ex.marketboro.dddprac.member.domain;

public class Member {

    final private Long id;

    final private String loginId;

    final private String password;

    public Member(Long id, String loginId, String password) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
    }
}
