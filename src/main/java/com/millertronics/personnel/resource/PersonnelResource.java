package com.millertronics.personnel.resource;

import com.millertronics.personnel.domain.Personnel;
import com.millertronics.personnel.repository.PersonnelRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("personnel")
public class PersonnelResource {

    @Inject
    private PersonnelRepository personnelRepository;

    @GET
    public Multi<Personnel> fetchAll() {
        return personnelRepository.findAll();
    }

    @GET
    @Path("{id}")
    public Uni<Response> findById(Long id) {
        return personnelRepository.findById(id)
            .map(personnel -> Response.ok().entity(personnel).build())
            .onFailure().recoverWithItem(__ -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Uni<Response> create(Personnel personnel) {
        return personnelRepository.save(personnel)
            .map(id -> URI.create("/personnel/" + id))
            .map(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return personnelRepository.delete(id)
            .map(success -> success ? true : null)
            .onItem().ifNotNull().transform(__ -> Response.noContent().build())
            .onItem().ifNull().continueWith(Response.serverError().build());
    }
}
