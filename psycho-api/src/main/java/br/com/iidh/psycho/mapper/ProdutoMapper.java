package br.com.iidh.psycho.mapper;

import br.com.iidh.psycho.dto.ProdutoDTO;
import br.com.iidh.psycho.entity.Produto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoMapper {
    
    public ProdutoDTO toDTO(Produto produto) {
        if (produto == null) return null;
        
        ProdutoDTO dto = new ProdutoDTO();
        dto.idProduto = produto.idProduto;
        dto.dsNome = produto.dsNome;
        dto.vlPreco1 = produto.vlPreco1;
        dto.vlPreco2 = produto.vlPreco2;
        dto.vlPreco3 = produto.vlPreco3;
        dto.vlPreco4 = produto.vlPreco4;
        
        return dto;
    }
    
    public Produto toEntity(ProdutoDTO dto) {
        if (dto == null) return null;
        
        Produto produto = new Produto();
        produto.idProduto = dto.idProduto;
        produto.dsNome = dto.dsNome;
        produto.vlPreco1 = dto.vlPreco1;
        produto.vlPreco2 = dto.vlPreco2;
        produto.vlPreco3 = dto.vlPreco3;
        produto.vlPreco4 = dto.vlPreco4;
        
        return produto;
    }
    
    public void updateEntity(Produto produto, ProdutoDTO dto) {
        if (produto == null || dto == null) return;
        
        produto.dsNome = dto.dsNome;
        produto.vlPreco1 = dto.vlPreco1;
        produto.vlPreco2 = dto.vlPreco2;
        produto.vlPreco3 = dto.vlPreco3;
        produto.vlPreco4 = dto.vlPreco4;
    }
}
