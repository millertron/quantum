package com.millertronics.personnel.resource;

import com.millertronics.personnel.domain.Personnel;
import com.millertronics.personnel.repository.PersonnelRepository;
import io.smallrye.mutiny.Multi;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("personnel")
public class PersonnelResource {

    @Inject
    private PersonnelRepository personnelRepository;

    @GET()
    public Multi<Personnel> fetchAll() {
        return personnelRepository.findAll();
    }
}
