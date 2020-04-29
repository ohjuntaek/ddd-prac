package ex.marketboro.dddprac.goods.domain;

public class Goods {

    final private Long id;

    final private String name;

    final private String category;

    public Goods(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
