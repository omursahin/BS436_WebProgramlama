package org.webp.intro.jee.jpa.outerjoin;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class B {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<C> listC = new ArrayList<>();

    public B() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<C> getListC() {
        return listC;
    }

    public void setListC(List<C> listC) {
        this.listC = listC;
    }
}
