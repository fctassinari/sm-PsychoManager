package br.com.iidh.psmng.pacientes.resource;

import br.com.iidh.psmng.pacientes.entity.TbPaciente;
import br.com.iidh.psmng.pacientes.repository.TbPacienteRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/api/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PacienteResource {

    @Inject
    TbPacienteRepository repository;

    @GET
    public List<TbPaciente> list(@QueryParam("q") String q) {
        if (q != null && !q.isBlank()) {
            return repository.findByNamePrefix(q);
        }
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        TbPaciente entity = repository.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @POST
    @Transactional
    public Response create(@Valid TbPaciente paciente) {
        repository.persist(paciente);
        return Response.created(URI.create("/api/pacientes/" + paciente.idPaciente)).entity(paciente).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, @Valid TbPaciente update) {
        TbPaciente entity = repository.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // copy simple fields
        entity.dsNome = update.dsNome;
        entity.dsEmail = update.dsEmail;
        entity.dsProfissao = update.dsProfissao;
        entity.dsEscolaridade = update.dsEscolaridade;
        entity.stEstadocivil = update.stEstadocivil;
        entity.dtNascimento = update.dtNascimento;
        entity.dsFilhos = update.dsFilhos;
        entity.dsEndereco = update.dsEndereco;
        entity.dsBairro = update.dsBairro;
        entity.nrCep = update.nrCep;
        entity.dsQueixas = update.dsQueixas;
        entity.dsProbsaude = update.dsProbsaude;
        entity.dsAcompmedico = update.dsAcompmedico;
        entity.dsRemedios = update.dsRemedios;
        entity.stBebe = update.stBebe;
        entity.stFuma = update.stFuma;
        entity.stDrogas = update.stDrogas;
        entity.stInsonia = update.stInsonia;
        entity.dsCalmante = update.dsCalmante;
        entity.stTratpsic = update.stTratpsic;
        entity.dsResultado = update.dsResultado;
        entity.dsObservacao = update.dsObservacao;
        entity.dsFicha = update.dsFicha;
        entity.stCatValor = update.stCatValor;
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
