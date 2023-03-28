package com.millertronics.personnel.repository;

import com.millertronics.personnel.domain.Personnel;
import com.millertronics.personnel.domain.PersonnelBuilder;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
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
            .fullName(row.getString("full_name"))
            .alias(row.getString("alias"))
            .build();
    }
}
