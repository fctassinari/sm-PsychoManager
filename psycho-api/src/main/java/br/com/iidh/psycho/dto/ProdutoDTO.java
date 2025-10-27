package br.com.iidh.psycho.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProdutoDTO {
    
    public Long idProduto;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    public String dsNome;
    
    public Double vlPreco1;
    
    public Double vlPreco2;
    
    public Double vlPreco3;
    
    public Double vlPreco4;
    
    // Construtores
    public ProdutoDTO() {}
    
    public ProdutoDTO(String dsNome) {
        this.dsNome = dsNome;
    }
    
    // Métodos auxiliares
    public Double getPrecoByCategoria(int categoria) {
        return switch (categoria) {
            case 1 -> vlPreco1;
            case 2 -> vlPreco2;
            case 3 -> vlPreco3;
            case 4 -> vlPreco4;
            default -> 0.0;
        };
    }
    
    public void setPrecoByCategoria(int categoria, Double valor) {
        switch (categoria) {
            case 1 -> this.vlPreco1 = valor;
            case 2 -> this.vlPreco2 = valor;
            case 3 -> this.vlPreco3 = valor;
            case 4 -> this.vlPreco4 = valor;
        }
    }
}
