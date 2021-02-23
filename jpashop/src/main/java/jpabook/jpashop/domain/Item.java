package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 전략이 바뀔때는 이 부분 수정해주면 된다.
@DiscriminatorColumn
public abstract class Item extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long Id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
