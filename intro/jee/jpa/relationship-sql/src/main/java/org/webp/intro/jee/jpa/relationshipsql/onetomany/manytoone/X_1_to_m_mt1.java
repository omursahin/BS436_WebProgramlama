package org.webp.intro.jee.jpa.relationshipsql.onetomany.manytoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class X_1_to_m_mt1 {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
