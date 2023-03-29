package com.millertronics.personnel.repository;

import com.millertronics.personnel.domain.Personnel;
import com.millertronics.personnel.domain.PersonnelBuilder;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowIterator;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonnelRepository {

    @Inject
    PgPool client;

    public Multi<Personnel> findAll() {
        return client.query("SELECT * FROM personnel ORDER BY id ASC").execute()
            .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
            .map(this::convertRow);
    }

    public Uni<Personnel> findById(Long id) {
        return client.preparedQuery("SELECT * FROM personnel WHERE id = $1").execute(Tuple.of(id))
            .map(RowSet::iterator)
            .map(RowIterator::next)
            .map(this::convertRow);
    }

    public Uni<Long> save(Personnel personnel) {
        return client.preparedQuery("INSERT INTO personnel (full_name, alias) VALUES ($1, $2) RETURNING id")
            .execute(Tuple.of(personnel.getFullName(), personnel.getAlias()))
            .map(RowSet::iterator)
            .map(RowIterator::next)
            .map(row -> row.getLong("id"));
    }

    public Uni<Boolean> delete(Long id) {
        return client.preparedQuery("DELETE FROM personnel WHERE id = $1")
            .execute(Tuple.of(id))
            .map(rowSet -> rowSet.rowCount() == 1);
    }

    private Personnel convertRow(Row row) {
        return PersonnelBuilder.aPersonnel()
            .id(row.getLong("id"))
            .fullName(row.getString("full_name"))
            .alias(row.getString("alias"))
            .build();
    }
}
