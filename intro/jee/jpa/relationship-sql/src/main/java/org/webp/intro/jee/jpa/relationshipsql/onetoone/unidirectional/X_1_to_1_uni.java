package org.webp.intro.jee.jpa.relationshipsql.onetoone.unidirectional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class X_1_to_1_uni {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Y_1_to_1_uni y;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Y_1_to_1_uni getY() {
        return y;
    }

    public void setY(Y_1_to_1_uni y) {
        this.y = y;
    }
}
