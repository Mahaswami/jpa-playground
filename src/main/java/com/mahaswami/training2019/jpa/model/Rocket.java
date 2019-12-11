package com.mahaswami.training2019.jpa.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(query = "Select r from Rocket r", name = "all_rockets")
@NamedQuery(query = "Select r from Rocket r where r.id = :id", name = "find_rocket_by_id")
@NamedQuery(query = "Select r from Rocket r where r.ssnNumber = :ssnNumber", name = "find_rocket_by_ssn_number")
public class Rocket implements Serializable {
    @Id  @GeneratedValue
    private long id;
    private String ssnNumber;
    private String title;
    private static final long serialVersionUID = 1L;

    public Rocket() {
        super();
    }

    public Rocket(String ssnNumber, String title) {
        super();
        this.ssnNumber = ssnNumber;
        this.title = title;
    }

    public long getId() {
        return this.id;
    }

    public String getSsnNumber() {
        return this.ssnNumber;
    }

    public void setSsnNumber(String ssnNumber) {
        this.ssnNumber = ssnNumber;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
