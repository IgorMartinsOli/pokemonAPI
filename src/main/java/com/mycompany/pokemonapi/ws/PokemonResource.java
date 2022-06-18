/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokemonapi.ws;
import com.mycompany.pokemonapi.model.Pokemon;
import com.mycompany.pokemonapi.service.PokemonService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Path("cadastrar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonResource {
    
    @Inject
    private PokemonService pokemonService;

    @GET
    @Path("listar")
    public List<Pokemon> listarPessoas() {
        return pokemonService.findAll();
    }

    @GET
    @Path("buscar/{codigo}")
    public Pokemon listarPessoas(@PathParam("codigo") Long codigo) {
        return pokemonService.findById(codigo);
    }

    @POST
    @Path("cadastrar")
    public Response salvar(Pokemon pessoa) {
        try {
            pokemonService.save(pessoa);
            return Response.ok().build();
        } catch (ConstraintViolationException cvex) {
            final Set<String> erros = cvex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
            return Response.status(Response.Status.BAD_REQUEST).entity(erros).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Instabilidade no serviço").build();
        }
    }
  }