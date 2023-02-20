package com.nttdata.bc.resources;

import java.util.List;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

import com.nttdata.bc.models.Account;
import com.nttdata.bc.services.IAccountService;

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

@Path("/accounts")
public class AccountResource {
    
    @Inject
    Logger logger;

    @Inject
    private IAccountService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 4)
    @Transactional
    public Response insert(@Valid Account obj) {
        Account client = this.service.insert(obj);
        return Response.status(Status.CREATED).entity(client).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Integer id, @Valid Account obj) {
        logger.info("Inicio ::: update ::: " + obj);
        obj.setAccountId(id);
        Account account = this.service.update(obj);
        return Response.status(Status.CREATED).entity(account).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fintAll() {
        List<Account> accounts = this.service.findAll();
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Integer id) {
        Account account = this.service.findById(id);
        return Response.ok(account).build();
    }

    @GET
    @Path("/debit-card/{debitCardId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDebitCardId(@PathParam("debitCardId") Integer debitCardId) {
        logger.info("entro ::: debitCardId ::: " + debitCardId);
        List<Account> accounts = this.service.findDebitCardId(debitCardId);
        return Response.ok(accounts).build();
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
