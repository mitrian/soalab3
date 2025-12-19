package com.soa.webvehicle.resource;

import com.soa.ejbvehicle.dto.*;
import com.soa.ejbvehicle.exception.ValidationException;
import com.soa.webvehicle.remoteClient.RemoteVehicleService;

import javax.naming.NamingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Path("/v1/vehicles")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class VehicleResource {
    
    private final RemoteVehicleService remoteVehicleService = new RemoteVehicleService("payara-ejb");

    private com.soa.ejbvehicle.service.VehicleService getVehicleService() {
        try {
            return remoteVehicleService.getVehicleService();
        } catch (NamingException e) {
            System.err.println("=== NamingException ===");
            e.printStackTrace();
            System.err.println("======================");
            throw new RuntimeException("Failed to lookup VehicleService: " + e.getMessage(), e);
        }
    }

    @POST
    @Path("/filter")
    public Response getVehiclesWithFilter(@Valid VehicleFilterDTO filterDTO) {
        VehicleListResponseDTO response = getVehicleService().getVehiclesWithFilter(filterDTO);
        return Response.ok(response).build();
    }

    @POST
    public Response createVehicle(@Valid VehicleRequestDTO requestDTO) {
        VehicleResponseDTO response = getVehicleService().createVehicle(requestDTO);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getVehicleById(@PathParam("id") String id) {
        try {
            long idd = Long.parseLong(id);
            VehicleResponseDTO response = getVehicleService().getVehicleById(idd);
            return Response.ok(response).build();
        } catch (NumberFormatException e) {
            throw new ValidationException("id is not a Long number");
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateVehicle(@PathParam("id") String id, @Valid VehicleRequestDTO requestDTO) {
        try {
            long idd = Long.parseLong(id);
            VehicleResponseDTO response = getVehicleService().updateVehicle(idd, requestDTO);
            return Response.ok(response).build();
        } catch (NumberFormatException e) {
            throw new ValidationException("id is not a Long number");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteVehicle(@PathParam("id") String id) {
        try {
            long idd = Long.parseLong(id);
            getVehicleService().deleteVehicle(idd);
        } catch (NumberFormatException e) {
            throw new ValidationException("id is not a Long number");
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/delete-by-engine-power/{enginePower}")
    public Response deleteByEnginePower(@PathParam("enginePower") String enginePower) {
        try {
            long enginePowerr = Long.parseLong(enginePower);
            CountResponseDTO response = getVehicleService().deleteByEnginePower(enginePowerr);
            return Response.ok(response).build();
        } catch (NumberFormatException e) {
            throw new ValidationException("enginePower is not a Long number");
        }
    }

    @POST
    @Path("/count-less-than-engine-power/{enginePower}")
    public Response countLessThanEnginePower(@PathParam("enginePower") String enginePower) {
        try {
            long enginePowerr = Long.parseLong(enginePower);
            Long count = getVehicleService().countLessThanEnginePower(enginePowerr);
            return Response.ok(new CountResponseDTO(count)).build();
        } catch (NumberFormatException e) {
            throw new ValidationException("enginePower is not a Long number");
        }
    }

    @POST
    @Path("/find-by-name-prefix/{prefix:.+}")
    public Response findByNamePrefix(@PathParam("prefix") String encodedPrefix) {
        String namePrefix;
        try {
            namePrefix = URLDecoder.decode(encodedPrefix, StandardCharsets.UTF_8);
        } catch (Exception e) {
            namePrefix = encodedPrefix;
        }

        List<VehicleResponseDTO> vehicles = getVehicleService().findByNamePrefix(namePrefix);
        com.soa.ejbvehicle.dto.VehicleListWrapper wrapper = 
                new com.soa.ejbvehicle.dto.VehicleListWrapper(vehicles);
        return Response.ok(wrapper).build();
    }
}

