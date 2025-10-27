package br.com.iidh.psycho.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TelefoneDTO {
    
    public Long idFone;
    
    @NotBlank(message = "Número do telefone é obrigatório")
    @Size(max = 17, message = "Número deve ter no máximo 17 caracteres")
    public String nrFone;
    
    @NotNull(message = "Tipo do telefone é obrigatório")
    public Character tpFone;
    
    public Long idPaciente;
    
    // Construtores
    public TelefoneDTO() {}
    
    public TelefoneDTO(String nrFone, Character tpFone) {
        this.nrFone = nrFone;
        this.tpFone = tpFone;
    }
    
    // Métodos auxiliares
    public String getTipoDescricao() {
        if (tpFone == null) return "";
        return switch (tpFone) {
            case 'C' -> "Celular";
            case 'R' -> "Residencial";
            case 'W' -> "Comercial";
            default -> "Desconhecido";
        };
    }
}
