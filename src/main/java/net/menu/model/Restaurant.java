package net.menu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by griv.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant extends Identifiable {

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<LunchMenu> lunchMenus;

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LunchMenu> getLunchMenus() {
        return lunchMenus;
    }
}
