package br.com.iidh.psmng.pacientes.repository;

import br.com.iidh.psmng.pacientes.entity.TbPaciente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TbPacienteRepository implements PanacheRepository<TbPaciente> {

    public List<TbPaciente> findByNamePrefix(String prefix) {
        return find("upper(dsNome) like ?1", (prefix == null ? "" : prefix.toUpperCase()) + "%").list();
    }
}
