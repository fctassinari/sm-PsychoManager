package br.com.iidh.psycho.service;

import br.com.iidh.psycho.dto.AgendaDTO;
import br.com.iidh.psycho.entity.Agenda;
import br.com.iidh.psycho.entity.AgendaId;
import br.com.iidh.psycho.entity.Paciente;
import br.com.iidh.psycho.entity.Produto;
import br.com.iidh.psycho.mapper.AgendaMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AgendaService {
    
    @Inject
    AgendaMapper agendaMapper;

    public List<AgendaDTO> listarTodos() {
        return Agenda.<Agenda>findAll()
                .list()
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
//    public List<AgendaDTO> listarTodos() {
//        return Agenda.findAll()
//                .stream()
//                .map(agendaMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    public List<AgendaDTO> buscarPorPaciente(Long idPaciente) {
        return Agenda.<Agenda>find("id.idPaciente = ?1 order by id.dtConsulta", idPaciente)
                .list()
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }

//    public List<AgendaDTO> buscarPorPaciente(Long idPaciente) {
//        return Agenda.find("id.idPaciente = ?1 order by id.dtConsulta", idPaciente)
//                .stream()
//                .map(agendaMapper::toDTO)
//                .collect(Collectors.toList());
//    }
    
    public List<AgendaDTO> buscarConsultasEmAbertoPorPeriodo(LocalDateTime de, LocalDateTime ate) {
        return Agenda.findConsultasEmAbertoPorPeriodo(de, ate)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarConsultasEmAbertoPorPaciente(Long idPaciente) {
        return Agenda.findConsultasEmAbertoPorPaciente(idPaciente)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarPresencasNaoConfirmadas(Long idPaciente) {
        return Agenda.findPresencasNaoConfirmadas(idPaciente)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarConsultasRealizadasPorPaciente(Long idPaciente, LocalDateTime de, LocalDateTime ate) {
        return Agenda.findConsultasRealizadasPorPaciente(idPaciente, de, ate)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarConsultasRealizadasPorPeriodo(LocalDateTime de, LocalDateTime ate) {
        return Agenda.findConsultasRealizadasPorPeriodo(de, ate)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarConsultasAgendadasPorPeriodo(LocalDateTime de, LocalDateTime ate) {
        return Agenda.findConsultasAgendadasPorPeriodo(de, ate)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarConsultasAgendadasPorPeriodoPorPaciente(Long idPaciente, LocalDateTime de, LocalDateTime ate) {
        return Agenda.findConsultasAgendadasPorPeriodoPorPaciente(idPaciente, de, ate)
                .stream()
                .map(agendaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AgendaDTO criar(@Valid AgendaDTO agendaDTO) {
        // Verificar se paciente existe
        Paciente paciente = Paciente.findById(agendaDTO.idPaciente);
        if (paciente == null) {
            throw new RuntimeException("Paciente não encontrado com ID: " + agendaDTO.idPaciente);
        }
        
        // Verificar se produto existe
        Produto produto = Produto.findById(agendaDTO.idProduto);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado com ID: " + agendaDTO.idProduto);
        }
        
        // Verificar se já existe agendamento para o mesmo paciente e horário
        AgendaId agendaId = new AgendaId(agendaDTO.idPaciente, agendaDTO.dtConsulta);
        List<Agenda> existentes = Agenda.findByPacienteAndData(agendaDTO.idPaciente, agendaDTO.dtConsulta);
        if (!existentes.isEmpty()) {
            throw new RuntimeException("Já existe agendamento para este paciente neste horário");
        }
        
        Agenda agenda = agendaMapper.toEntity(agendaDTO);
        agenda.id = agendaId;
        agenda.paciente = paciente;
        agenda.produto = produto;
        
        // Definir preço baseado na categoria do paciente
        if (agendaDTO.vlPreco == null && paciente.stCatValor != null) {
            int categoria = Character.getNumericValue(paciente.stCatValor);
            agenda.vlPreco = produto.getPrecoByCategoria(categoria);
        }
        
        agenda.persist();
        return agendaMapper.toDTO(agenda);
    }
    
    @Transactional
    public AgendaDTO atualizar(Long idPaciente, LocalDateTime dtConsulta, @Valid AgendaDTO agendaDTO) {
        AgendaId agendaId = new AgendaId(idPaciente, dtConsulta);
        Agenda agenda = Agenda.findById(agendaId);
        if (agenda == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        
        agendaMapper.updateEntity(agenda, agendaDTO);
        
        // Atualizar produto se necessário
        if (agendaDTO.idProduto != null && !agendaDTO.idProduto.equals(agenda.idProduto)) {
            Produto produto = Produto.findById(agendaDTO.idProduto);
            if (produto == null) {
                throw new RuntimeException("Produto não encontrado com ID: " + agendaDTO.idProduto);
            }
            agenda.produto = produto;
            agenda.idProduto = agendaDTO.idProduto;
        }
        
        return agendaMapper.toDTO(agenda);
    }
    
    @Transactional
    public void excluir(Long idPaciente, LocalDateTime dtConsulta) {
        AgendaId agendaId = new AgendaId(idPaciente, dtConsulta);
        Agenda agenda = Agenda.findById(agendaId);
        if (agenda == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        
        agenda.delete();
    }
    
    @Transactional
    public AgendaDTO confirmarPresenca(Long idPaciente, LocalDateTime dtConsulta) {
        AgendaId agendaId = new AgendaId(idPaciente, dtConsulta);
        Agenda agenda = Agenda.findById(agendaId);
        if (agenda == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        
        agenda.stPresenca = true;
        return agendaMapper.toDTO(agenda);
    }
    
    @Transactional
    public AgendaDTO atualizarPagamento(Long idPaciente, LocalDateTime dtConsulta, Integer statusPagamento) {
        AgendaId agendaId = new AgendaId(idPaciente, dtConsulta);
        Agenda agenda = Agenda.findById(agendaId);
        if (agenda == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        
        agenda.stPagamento = statusPagamento;
        return agendaMapper.toDTO(agenda);
    }
}
