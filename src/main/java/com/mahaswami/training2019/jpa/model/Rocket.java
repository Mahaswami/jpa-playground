package com.mahaswami.training2019.jpa.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(query = "Select r from Rocket r", name = "all_rockets")
@NamedQuery(query = "Select r from Rocket r where r.id = :id", name = "find_rocket_by_id")
@NamedQuery(query = "Select r from Rocket r where r.name = :name", name = "find_rocket_by_name")
public class Rocket implements Serializable {
    @Id  @GeneratedValue
    private long id;
    private String name;
    private String title;
    private static final long serialVersionUID = 1L;

    public Rocket() {
        super();
    }

    public Rocket(String name, String title) {
        super();
        this.name = name;
        this.title = title;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheTitle() {
        return this.title;
    }

    public void setTheTitle(String title) {
        this.title = title;
    }

}
