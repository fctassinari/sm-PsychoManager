package br.com.iidh.psycho.service;

import br.com.iidh.psycho.dto.PacienteDTO;
import br.com.iidh.psycho.dto.TelefoneDTO;
import br.com.iidh.psycho.entity.Paciente;
import br.com.iidh.psycho.entity.Telefone;
import br.com.iidh.psycho.entity.Senha;
import br.com.iidh.psycho.mapper.PacienteMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PacienteService {
    
    @Inject
    PacienteMapper pacienteMapper;
    
    @Inject
    SenhaService senhaService;
    
    public List<PacienteDTO> listarTodos() {
        return Paciente.findAllOrderedById()
                .stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PacienteDTO> buscarPorNome(String nome) {
        return Paciente.findByNomeContaining(nome)
                .stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public PacienteDTO buscarPorId(Long id) {
        Paciente paciente = Paciente.findById(id);
        if (paciente == null) {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
        return pacienteMapper.toDTO(paciente);
    }
    
    @Transactional
    public PacienteDTO criar(@Valid PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        
        // Salvar paciente
        paciente.persist();
        
        // Salvar telefones
        if (pacienteDTO.telefones != null && !pacienteDTO.telefones.isEmpty()) {
            for (TelefoneDTO telefoneDTO : pacienteDTO.telefones) {
                Telefone telefone = new Telefone();
                telefone.nrFone = telefoneDTO.nrFone;
                telefone.tpFone = telefoneDTO.tpFone;
                telefone.paciente = paciente;
                telefone.persist();
            }
        }
        
        // Criar senha se fornecida
        if (pacienteDTO.dsSenha != null && !pacienteDTO.dsSenha.trim().isEmpty()) {
            senhaService.criarSenha(paciente.idPaciente, pacienteDTO.dsSenha);
        }
        
        return pacienteMapper.toDTO(paciente);
    }
    
    @Transactional
    public PacienteDTO atualizar(Long id, @Valid PacienteDTO pacienteDTO) {
        Paciente paciente = Paciente.findById(id);
        if (paciente == null) {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
        
        // Atualizar dados do paciente
        pacienteMapper.updateEntity(paciente, pacienteDTO);
        
        // Atualizar telefones
        if (pacienteDTO.telefones != null) {
            // Remover telefones existentes
            Telefone.delete("paciente.idPaciente = ?1", id);
            
            // Adicionar novos telefones
            for (TelefoneDTO telefoneDTO : pacienteDTO.telefones) {
                Telefone telefone = new Telefone();
                telefone.nrFone = telefoneDTO.nrFone;
                telefone.tpFone = telefoneDTO.tpFone;
                telefone.paciente = paciente;
                telefone.persist();
            }
        }
        
        // Atualizar senha se fornecida
        if (pacienteDTO.dsSenha != null && !pacienteDTO.dsSenha.trim().isEmpty()) {
            senhaService.atualizarSenha(id, pacienteDTO.dsSenha);
        }
        
        return pacienteMapper.toDTO(paciente);
    }
    
    @Transactional
    public void excluir(Long id) {
        Paciente paciente = Paciente.findById(id);
        if (paciente == null) {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
        
        // Excluir telefones
        Telefone.delete("paciente.idPaciente = ?1", id);
        
        // Excluir senha
        Senha.delete("idPaciente = ?1", id);
        
        // Excluir paciente
        paciente.delete();
    }
    
    public List<PacienteDTO> buscarPorEmail(String email) {
        return Paciente.findByEmail(email)
                .stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }
}
