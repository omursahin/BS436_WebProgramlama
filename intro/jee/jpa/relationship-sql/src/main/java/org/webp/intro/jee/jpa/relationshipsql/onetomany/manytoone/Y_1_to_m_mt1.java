package org.webp.intro.jee.jpa.relationshipsql.onetomany.manytoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Y_1_to_m_mt1 {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private X_1_to_m_mt1 x;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public X_1_to_m_mt1 getX() {
        return x;
    }

    public void setX(X_1_to_m_mt1 x) {
        this.x = x;
    }
}
