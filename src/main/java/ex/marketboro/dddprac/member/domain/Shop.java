package ex.marketboro.dddprac.member.domain;

import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Shop {

    @Getter
    private String name;

    @Getter
    private String address;

    protected Shop() {
    }

    public Shop(String name, String address) {
        validate(name);

        this.name = name;
        this.address = address;
    }

    private void validate(String name) {
        if (name.length() >= 10) {
            throw new IllegalArgumentException("shop name must less than ten character");
        }
    }

}
