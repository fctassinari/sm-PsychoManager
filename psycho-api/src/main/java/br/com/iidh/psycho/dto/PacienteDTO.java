package br.com.iidh.psycho.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDTO {
    
    public Long idPaciente;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    public String dsNome;
    
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    public String dsEmail;
    
    @Size(max = 100, message = "Profissão deve ter no máximo 100 caracteres")
    public String dsProfissao;
    
    @Size(max = 100, message = "Escolaridade deve ter no máximo 100 caracteres")
    public String dsEscolaridade;
    
    public Character stEstadocivil;
    
    public LocalDate dtNascimento;
    
    @Size(max = 100, message = "Filhos deve ter no máximo 100 caracteres")
    public String dsFilhos;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 100, message = "Endereço deve ter no máximo 100 caracteres")
    public String dsEndereco;
    
    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    public String dsBairro;
    
    @NotBlank(message = "CEP é obrigatório")
    @Size(max = 9, message = "CEP deve ter no máximo 9 caracteres")
    public String nrCep;
    
    @Size(max = 1000, message = "Queixas deve ter no máximo 1000 caracteres")
    public String dsQueixas;
    
    @Size(max = 200, message = "Problemas de saúde deve ter no máximo 200 caracteres")
    public String dsProbsaude;
    
    @Size(max = 100, message = "Acompanhamento médico deve ter no máximo 100 caracteres")
    public String dsAcompmedico;
    
    @Size(max = 100, message = "Remédios deve ter no máximo 100 caracteres")
    public String dsRemedios;
    
    public Character stBebe;
    
    public Character stFuma;
    
    public Character stDrogas;
    
    public Character stInsonia;
    
    @Size(max = 100, message = "Calmante deve ter no máximo 100 caracteres")
    public String dsCalmante;
    
    public Character stTratpsic;
    
    @Size(max = 1000, message = "Resultado deve ter no máximo 1000 caracteres")
    public String dsResultado;
    
    @Size(max = 1000, message = "Observação deve ter no máximo 1000 caracteres")
    public String dsObservacao;
    
    @Size(max = 200, message = "Ficha deve ter no máximo 200 caracteres")
    public String dsFicha;
    
    public Character stCatValor;
    
    public List<TelefoneDTO> telefones = new ArrayList<>();
    
    public String dsSenha;
    
    // Construtores
    public PacienteDTO() {}
    
    // Métodos auxiliares
    public String getEstadocivilDescricao() {
        if (stEstadocivil == null) return "";
        return switch (stEstadocivil) {
            case 'S' -> "Solteiro";
            case 'C' -> "Casado";
            case 'E' -> "Separado";
            case 'V' -> "Viúvo";
            case 'D' -> "Divorciado";
            default -> "Desconhecido";
        };
    }
    
    public String getCategoriaValorDescricao() {
        if (stCatValor == null) return "";
        return "Categoria " + stCatValor;
    }
    
    public String getBebeDescricao() {
        if (stBebe == null) return "";
        return stBebe == 'S' ? "Sim" : "Não";
    }
    
    public String getFumaDescricao() {
        if (stFuma == null) return "";
        return stFuma == 'S' ? "Sim" : "Não";
    }
    
    public String getDrogasDescricao() {
        if (stDrogas == null) return "";
        return stDrogas == 'S' ? "Sim" : "Não";
    }
    
    public String getInsoniaDescricao() {
        if (stInsonia == null) return "";
        return stInsonia == 'S' ? "Sim" : "Não";
    }
    
    public String getTratpsicDescricao() {
        if (stTratpsic == null) return "";
        return stTratpsic == 'S' ? "Sim" : "Não";
    }
}
