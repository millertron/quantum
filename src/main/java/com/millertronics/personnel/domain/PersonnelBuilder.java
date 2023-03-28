package com.millertronics.personnel.domain;

import java.util.List;

public final class PersonnelBuilder {
    private Long id;
    private String fullName;
    private String alias;
    //private List<String> affiliations;

    private PersonnelBuilder() {
    }

    public static PersonnelBuilder aPersonnel() {
        return new PersonnelBuilder();
    }

    public PersonnelBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PersonnelBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PersonnelBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    /*
    public PersonnelBuilder affiliations(List<String> affiliations) {
        this.affiliations = affiliations;
        return this;
    }
    */

    public Personnel build() {
        return new Personnel(id,
            fullName,
            alias
            //affiliations
        );
    }

}
