package com.millertronics.config;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PostgresConfig {

    @Inject
    PgPool client;

    @ConfigProperty(name = "quantum.schema.create", defaultValue = "true")
    public boolean schemaCreate;

    public void config(@Observes StartupEvent event) {
        if(schemaCreate) {
            Log.info("PostgresConfig -> config -> initializing database.");
            initDb();
        }
        Log.info("PostgresConfig -> config -> config completed.");
    }

    private void initDb() {
        client.query("DROP TABLE IF EXISTS personnel").execute()
            .flatMap(r -> client.query("CREATE TABLE personnel (id SERIAL PRIMARY KEY, full_name VARCHAR(255) NOT NULL, alias VARCHAR(255) NOT NULL)").execute())
            .flatMap(r -> client.query("INSERT INTO personnel (full_name, alias) VALUES ('Charles Xavier', 'Professor X')").execute())
            .flatMap(r -> client.query("INSERT INTO personnel (full_name, alias) VALUES ('Erik Lehnsherr', 'Magneto')").execute())
            .flatMap(r -> client.query("INSERT INTO personnel (full_name, alias) VALUES ('Bruce Wayne', 'Batman')").execute())
            .flatMap(r -> client.query("INSERT INTO personnel (full_name, alias) VALUES ('Dick Grayson', 'Nightwing')").execute())
            .await().indefinitely();
    }
}
