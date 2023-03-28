package com.millertronics.personnel.domain;

import java.util.List;

public class Personnel {

    private Long id;
    private String fullName;
    private String alias;
    //private List<String> affiliations;

    public Personnel(Long id,
                     String fullName,
                     String alias
                     //List<String> affiliations
    ) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
        //this. affiliations = affiliations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
