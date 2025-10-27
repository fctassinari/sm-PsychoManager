package br.com.iidh.psycho.mapper;

import br.com.iidh.psycho.dto.PacienteDTO;
import br.com.iidh.psycho.dto.TelefoneDTO;
import br.com.iidh.psycho.entity.Paciente;
import br.com.iidh.psycho.entity.Telefone;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PacienteMapper {
    
    public PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null) return null;
        
        PacienteDTO dto = new PacienteDTO();
        dto.idPaciente = paciente.idPaciente;
        dto.dsNome = paciente.dsNome;
        dto.dsEmail = paciente.dsEmail;
        dto.dsProfissao = paciente.dsProfissao;
        dto.dsEscolaridade = paciente.dsEscolaridade;
        dto.stEstadocivil = paciente.stEstadocivil;
        dto.dtNascimento = paciente.dtNascimento;
        dto.dsFilhos = paciente.dsFilhos;
        dto.dsEndereco = paciente.dsEndereco;
        dto.dsBairro = paciente.dsBairro;
        dto.nrCep = paciente.nrCep;
        dto.dsQueixas = paciente.dsQueixas;
        dto.dsProbsaude = paciente.dsProbsaude;
        dto.dsAcompmedico = paciente.dsAcompmedico;
        dto.dsRemedios = paciente.dsRemedios;
        dto.stBebe = paciente.stBebe;
        dto.stFuma = paciente.stFuma;
        dto.stDrogas = paciente.stDrogas;
        dto.stInsonia = paciente.stInsonia;
        dto.dsCalmante = paciente.dsCalmante;
        dto.stTratpsic = paciente.stTratpsic;
        dto.dsResultado = paciente.dsResultado;
        dto.dsObservacao = paciente.dsObservacao;
        dto.dsFicha = paciente.dsFicha;
        dto.stCatValor = paciente.stCatValor;
        
        // Mapear telefones
        if (paciente.telefones != null) {
            dto.telefones = paciente.telefones.stream()
                    .map(this::telefoneToDTO)
                    .collect(Collectors.toList());
        }
        
        return dto;
    }
    
    public Paciente toEntity(PacienteDTO dto) {
        if (dto == null) return null;
        
        Paciente paciente = new Paciente();
        paciente.idPaciente = dto.idPaciente;
        paciente.dsNome = dto.dsNome;
        paciente.dsEmail = dto.dsEmail;
        paciente.dsProfissao = dto.dsProfissao;
        paciente.dsEscolaridade = dto.dsEscolaridade;
        paciente.stEstadocivil = dto.stEstadocivil;
        paciente.dtNascimento = dto.dtNascimento;
        paciente.dsFilhos = dto.dsFilhos;
        paciente.dsEndereco = dto.dsEndereco;
        paciente.dsBairro = dto.dsBairro;
        paciente.nrCep = dto.nrCep;
        paciente.dsQueixas = dto.dsQueixas;
        paciente.dsProbsaude = dto.dsProbsaude;
        paciente.dsAcompmedico = dto.dsAcompmedico;
        paciente.dsRemedios = dto.dsRemedios;
        paciente.stBebe = dto.stBebe;
        paciente.stFuma = dto.stFuma;
        paciente.stDrogas = dto.stDrogas;
        paciente.stInsonia = dto.stInsonia;
        paciente.dsCalmante = dto.dsCalmante;
        paciente.stTratpsic = dto.stTratpsic;
        paciente.dsResultado = dto.dsResultado;
        paciente.dsObservacao = dto.dsObservacao;
        paciente.dsFicha = dto.dsFicha;
        paciente.stCatValor = dto.stCatValor;
        
        return paciente;
    }
    
    public void updateEntity(Paciente paciente, PacienteDTO dto) {
        if (paciente == null || dto == null) return;
        
        paciente.dsNome = dto.dsNome;
        paciente.dsEmail = dto.dsEmail;
        paciente.dsProfissao = dto.dsProfissao;
        paciente.dsEscolaridade = dto.dsEscolaridade;
        paciente.stEstadocivil = dto.stEstadocivil;
        paciente.dtNascimento = dto.dtNascimento;
        paciente.dsFilhos = dto.dsFilhos;
        paciente.dsEndereco = dto.dsEndereco;
        paciente.dsBairro = dto.dsBairro;
        paciente.nrCep = dto.nrCep;
        paciente.dsQueixas = dto.dsQueixas;
        paciente.dsProbsaude = dto.dsProbsaude;
        paciente.dsAcompmedico = dto.dsAcompmedico;
        paciente.dsRemedios = dto.dsRemedios;
        paciente.stBebe = dto.stBebe;
        paciente.stFuma = dto.stFuma;
        paciente.stDrogas = dto.stDrogas;
        paciente.stInsonia = dto.stInsonia;
        paciente.dsCalmante = dto.dsCalmante;
        paciente.stTratpsic = dto.stTratpsic;
        paciente.dsResultado = dto.dsResultado;
        paciente.dsObservacao = dto.dsObservacao;
        paciente.dsFicha = dto.dsFicha;
        paciente.stCatValor = dto.stCatValor;
    }
    
    private TelefoneDTO telefoneToDTO(Telefone telefone) {
        if (telefone == null) return null;
        
        TelefoneDTO dto = new TelefoneDTO();
        dto.idFone = telefone.idFone;
        dto.nrFone = telefone.nrFone;
        dto.tpFone = telefone.tpFone;
        dto.idPaciente = telefone.paciente != null ? telefone.paciente.idPaciente : null;
        
        return dto;
    }
}
