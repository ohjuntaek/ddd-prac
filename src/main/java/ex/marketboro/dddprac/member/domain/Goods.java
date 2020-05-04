package ex.marketboro.dddprac.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class Goods {

    @Getter
    private String name;

    @Getter
    private String category;

    public Goods(String name, String category) {
        this.name = name;
        this.category = category;
    }

    protected Goods() {
    }
}
