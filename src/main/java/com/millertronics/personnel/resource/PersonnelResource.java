package com.millertronics.personnel.resource;

import com.millertronics.personnel.domain.Personnel;
import com.millertronics.personnel.repository.PersonnelRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("personnel")
public class PersonnelResource {

    @Inject
    private PersonnelRepository personnelRepository;

    @GET
    public Multi<Personnel> fetchAll() {
        return personnelRepository.findAll();
    }

    @POST
    public Uni<Long> create(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> delete(Long id) {
        personnelRepository.delete(id);
        return Uni.createFrom().voidItem();
    }
}
