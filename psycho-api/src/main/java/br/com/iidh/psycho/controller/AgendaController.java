package br.com.iidh.psycho.controller;

import br.com.iidh.psycho.dto.AgendaDTO;
import br.com.iidh.psycho.service.AgendaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/api/agenda")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Agenda", description = "Operações relacionadas ao agendamento")
public class AgendaController {
    
    @Inject
    AgendaService agendaService;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    @GET
    @Operation(summary = "Listar todos os agendamentos", description = "Retorna uma lista de todos os agendamentos")
    public List<AgendaDTO> listarTodos() {
        return agendaService.listarTodos();
    }
    
    @GET
    @Path("/paciente/{idPaciente}")
    @Operation(summary = "Buscar agendamentos por paciente", description = "Retorna agendamentos de um paciente específico")
    public List<AgendaDTO> buscarPorPaciente(@PathParam("idPaciente") Long idPaciente) {
        return agendaService.buscarPorPaciente(idPaciente);
    }
    
    @GET
    @Path("/periodo")
    @Operation(summary = "Buscar agendamentos por período", description = "Retorna agendamentos em um período específico")
    public List<AgendaDTO> buscarPorPeriodo(
            @QueryParam("de") String de,
            @QueryParam("ate") String ate) {
        LocalDateTime dataInicio = LocalDateTime.parse(de, FORMATTER);
        LocalDateTime dataFim = LocalDateTime.parse(ate, FORMATTER);
        return agendaService.buscarConsultasAgendadasPorPeriodo(dataInicio, dataFim);
    }
    
    @GET
    @Path("/periodo/aberto")
    @Operation(summary = "Buscar consultas em aberto por período", description = "Retorna consultas em aberto em um período específico")
    public List<AgendaDTO> buscarConsultasEmAbertoPorPeriodo(
            @QueryParam("de") String de,
            @QueryParam("ate") String ate) {
        LocalDateTime dataInicio = LocalDateTime.parse(de, FORMATTER);
        LocalDateTime dataFim = LocalDateTime.parse(ate, FORMATTER);
        return agendaService.buscarConsultasEmAbertoPorPeriodo(dataInicio, dataFim);
    }
    
    @GET
    @Path("/paciente/{idPaciente}/aberto")
    @Operation(summary = "Buscar consultas em aberto por paciente", description = "Retorna consultas em aberto de um paciente específico")
    public List<AgendaDTO> buscarConsultasEmAbertoPorPaciente(@PathParam("idPaciente") Long idPaciente) {
        return agendaService.buscarConsultasEmAbertoPorPaciente(idPaciente);
    }
    
    @GET
    @Path("/paciente/{idPaciente}/nao-confirmadas")
    @Operation(summary = "Buscar presenças não confirmadas", description = "Retorna presenças não confirmadas de um paciente")
    public List<AgendaDTO> buscarPresencasNaoConfirmadas(@PathParam("idPaciente") Long idPaciente) {
        return agendaService.buscarPresencasNaoConfirmadas(idPaciente);
    }
    
    @GET
    @Path("/realizadas/periodo")
    @Operation(summary = "Buscar consultas realizadas por período", description = "Retorna consultas realizadas em um período específico")
    public List<AgendaDTO> buscarConsultasRealizadasPorPeriodo(
            @QueryParam("de") String de,
            @QueryParam("ate") String ate) {
        LocalDateTime dataInicio = LocalDateTime.parse(de, FORMATTER);
        LocalDateTime dataFim = LocalDateTime.parse(ate, FORMATTER);
        return agendaService.buscarConsultasRealizadasPorPeriodo(dataInicio, dataFim);
    }
    
    @GET
    @Path("/realizadas/paciente/{idPaciente}")
    @Operation(summary = "Buscar consultas realizadas por paciente", description = "Retorna consultas realizadas de um paciente em um período")
    public List<AgendaDTO> buscarConsultasRealizadasPorPaciente(
            @PathParam("idPaciente") Long idPaciente,
            @QueryParam("de") String de,
            @QueryParam("ate") String ate) {
        LocalDateTime dataInicio = LocalDateTime.parse(de, FORMATTER);
        LocalDateTime dataFim = LocalDateTime.parse(ate, FORMATTER);
        return agendaService.buscarConsultasRealizadasPorPaciente(idPaciente, dataInicio, dataFim);
    }
    
    @POST
    @Operation(summary = "Criar novo agendamento", description = "Cria um novo agendamento no sistema")
    public Response criar(@Valid AgendaDTO agendaDTO) {
        try {
            AgendaDTO agendamentoCriado = agendaService.criar(agendaDTO);
            return Response.status(Response.Status.CREATED).entity(agendamentoCriado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @PUT
    @Path("/{idPaciente}/{dtConsulta}")
    @Operation(summary = "Atualizar agendamento", description = "Atualiza um agendamento existente")
    public Response atualizar(
            @PathParam("idPaciente") Long idPaciente,
            @PathParam("dtConsulta") String dtConsulta,
            @Valid AgendaDTO agendaDTO) {
        try {
            LocalDateTime dataConsulta = LocalDateTime.parse(dtConsulta, FORMATTER);
            AgendaDTO agendamentoAtualizado = agendaService.atualizar(idPaciente, dataConsulta, agendaDTO);
            return Response.ok(agendamentoAtualizado).build();
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
    @Path("/{idPaciente}/{dtConsulta}")
    @Operation(summary = "Excluir agendamento", description = "Remove um agendamento do sistema")
    public Response excluir(
            @PathParam("idPaciente") Long idPaciente,
            @PathParam("dtConsulta") String dtConsulta) {
        try {
            LocalDateTime dataConsulta = LocalDateTime.parse(dtConsulta, FORMATTER);
            agendaService.excluir(idPaciente, dataConsulta);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @PUT
    @Path("/{idPaciente}/{dtConsulta}/confirmar-presenca")
    @Operation(summary = "Confirmar presença", description = "Confirma a presença em um agendamento")
    public Response confirmarPresenca(
            @PathParam("idPaciente") Long idPaciente,
            @PathParam("dtConsulta") String dtConsulta) {
        try {
            LocalDateTime dataConsulta = LocalDateTime.parse(dtConsulta, FORMATTER);
            AgendaDTO agendamento = agendaService.confirmarPresenca(idPaciente, dataConsulta);
            return Response.ok(agendamento).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @PUT
    @Path("/{idPaciente}/{dtConsulta}/pagamento")
    @Operation(summary = "Atualizar status de pagamento", description = "Atualiza o status de pagamento de um agendamento")
    public Response atualizarPagamento(
            @PathParam("idPaciente") Long idPaciente,
            @PathParam("dtConsulta") String dtConsulta,
            @QueryParam("status") Integer statusPagamento) {
        try {
            LocalDateTime dataConsulta = LocalDateTime.parse(dtConsulta, FORMATTER);
            AgendaDTO agendamento = agendaService.atualizarPagamento(idPaciente, dataConsulta, statusPagamento);
            return Response.ok(agendamento).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
