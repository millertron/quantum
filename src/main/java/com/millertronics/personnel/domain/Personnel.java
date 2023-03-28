package com.millertronics.personnel.domain;

import java.util.List;

public class Personnel {

    private final String fullName;
    private final String alias;
    private final List<String> affiliations;

    public Personnel(String fullName, String alias, List<String> affiliations) {
        this.fullName = fullName;
        this.alias = alias;
        this.affiliations = affiliations;
    }


}
