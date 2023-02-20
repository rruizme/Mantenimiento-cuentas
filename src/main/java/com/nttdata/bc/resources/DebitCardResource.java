package com.nttdata.bc.resources;

import java.util.List;

import org.jboss.logging.Logger;

import com.nttdata.bc.models.DebitCard;
import com.nttdata.bc.services.IDebitCardService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/debit-cards")
public class DebitCardResource {
    @Inject
    Logger logger;

    @Inject
    private IDebitCardService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response insert(@Valid DebitCard obj) {
        DebitCard debitCard = this.service.insert(obj);
        return Response.status(Status.CREATED).entity(debitCard).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Integer id, @Valid DebitCard obj) {
        logger.info("Inicio ::: update ::: " + obj);
        obj.setDebitCardId(id);
        DebitCard account = this.service.update(obj);
        return Response.status(Status.CREATED).entity(account).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fintAll() {
        List<DebitCard> debitCards = this.service.findAll();
        return Response.ok(debitCards).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Integer id) {
        DebitCard debitCard = this.service.findById(id);
        return Response.ok(debitCard).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON) // [RRM] - ADICIÓN
    @Consumes(MediaType.APPLICATION_JSON) // [RRM] - ADICIÓN
    @Transactional // [RRM] - ADICIÓN
    public Response delete(@PathParam("id") Integer id) {
        this.service.delete(id);
        return Response.noContent().build();
    }
}
