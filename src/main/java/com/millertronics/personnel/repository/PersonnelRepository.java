package com.millertronics.personnel.repository;

import com.millertronics.personnel.domain.Personnel;
import com.millertronics.personnel.domain.PersonnelBuilder;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import javax.inject.Inject;

public class PersonnelRepository {

    @Inject
    PgPool client;

    public Multi<Personnel> findAll() {
        return client.query("SELECT * FROM personnel ORDER BY id ASC").execute()
            .onItem()
            .transformToMulti(set -> Multi.createFrom().iterable(set))
            .onItem().transform(this::convertRow);
    }

    private Personnel convertRow(Row row) {
        return PersonnelBuilder.aPersonnel()
            .id(row.getLong("id"))
            .alias(row.getString("alias"))
            .fullName(row.getString("fullName"))
            .build();
    }
}
