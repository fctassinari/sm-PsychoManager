package br.com.iidh.psycho.mapper;

import br.com.iidh.psycho.dto.AgendaDTO;
import br.com.iidh.psycho.entity.Agenda;
import br.com.iidh.psycho.entity.AgendaId;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgendaMapper {
    
    public AgendaDTO toDTO(Agenda agenda) {
        if (agenda == null) return null;
        
        AgendaDTO dto = new AgendaDTO();
        dto.idPaciente = agenda.id != null ? agenda.id.idPaciente : null;
        dto.dtConsulta = agenda.id != null ? agenda.id.dtConsulta : null;
        dto.dtConsultaAte = agenda.dtConsultaAte;
        dto.stPresenca = agenda.stPresenca;
        dto.idProduto = agenda.idProduto;
        dto.dsObs = agenda.dsObs;
        dto.vlPreco = agenda.vlPreco;
        dto.stPagamento = agenda.stPagamento;
        
        // Dados para exibição
        dto.nomePaciente = agenda.paciente != null ? agenda.paciente.dsNome : null;
        dto.nomeProduto = agenda.produto != null ? agenda.produto.dsNome : null;
        
        return dto;
    }
    
    public Agenda toEntity(AgendaDTO dto) {
        if (dto == null) return null;
        
        Agenda agenda = new Agenda();
        agenda.id = new AgendaId(dto.idPaciente, dto.dtConsulta);
        agenda.dtConsultaAte = dto.dtConsultaAte;
        agenda.stPresenca = dto.stPresenca;
        agenda.idProduto = dto.idProduto;
        agenda.dsObs = dto.dsObs;
        agenda.vlPreco = dto.vlPreco;
        agenda.stPagamento = dto.stPagamento;
        
        return agenda;
    }
    
    public void updateEntity(Agenda agenda, AgendaDTO dto) {
        if (agenda == null || dto == null) return;
        
        agenda.dtConsultaAte = dto.dtConsultaAte;
        agenda.stPresenca = dto.stPresenca;
        agenda.idProduto = dto.idProduto;
        agenda.dsObs = dto.dsObs;
        agenda.vlPreco = dto.vlPreco;
        agenda.stPagamento = dto.stPagamento;
    }
}
