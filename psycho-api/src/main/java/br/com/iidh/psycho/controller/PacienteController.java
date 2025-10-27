package br.com.iidh.psycho.controller;

import br.com.iidh.psycho.dto.PacienteDTO;
import br.com.iidh.psycho.service.PacienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Pacientes", description = "Operações relacionadas aos pacientes")
public class PacienteController {
    
    @Inject
    PacienteService pacienteService;
    
    @GET
    @Operation(summary = "Listar todos os pacientes", description = "Retorna uma lista de todos os pacientes cadastrados")
    public List<PacienteDTO> listarTodos() {
        return pacienteService.listarTodos();
    }
    
    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar pacientes por nome", description = "Busca pacientes que contenham o nome especificado")
    public List<PacienteDTO> buscarPorNome(@QueryParam("nome") String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return pacienteService.listarTodos();
        }
        return pacienteService.buscarPorNome(nome);
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Retorna um paciente específico pelo seu ID")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            PacienteDTO paciente = pacienteService.buscarPorId(id);
            return Response.ok(paciente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @POST
    @Operation(summary = "Criar novo paciente", description = "Cria um novo paciente no sistema")
    public Response criar(@Valid PacienteDTO pacienteDTO) {
        try {
            PacienteDTO pacienteCriado = pacienteService.criar(pacienteDTO);
            return Response.status(Response.Status.CREATED).entity(pacienteCriado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar paciente", description = "Atualiza os dados de um paciente existente")
    public Response atualizar(@PathParam("id") Long id, @Valid PacienteDTO pacienteDTO) {
        try {
            PacienteDTO pacienteAtualizado = pacienteService.atualizar(id, pacienteDTO);
            return Response.ok(pacienteAtualizado).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir paciente", description = "Remove um paciente do sistema")
    public Response excluir(@PathParam("id") Long id) {
        try {
            pacienteService.excluir(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @GET
    @Path("/email/{email}")
    @Operation(summary = "Buscar paciente por email", description = "Busca pacientes pelo endereço de email")
    public List<PacienteDTO> buscarPorEmail(@PathParam("email") String email) {
        return pacienteService.buscarPorEmail(email);
    }
}
