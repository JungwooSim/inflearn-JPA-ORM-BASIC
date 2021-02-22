package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 상속관계 매핑 전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계 매핑 전략
@DiscriminatorColumn
public class Movie extends Item {
    private String director;
    private String actor;
}
