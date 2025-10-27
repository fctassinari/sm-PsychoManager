package br.com.iidh.psycho.service;

import br.com.iidh.psycho.dto.ProdutoDTO;
import br.com.iidh.psycho.entity.Produto;
import br.com.iidh.psycho.mapper.ProdutoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoService {
    
    @Inject
    ProdutoMapper produtoMapper;
    
    public List<ProdutoDTO> listarTodos() {
        return Produto.findAllOrderedByNome()
                .stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ProdutoDTO> buscarPorNome(String nome) {
        return Produto.findByNomeContaining(nome)
                .stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        return produtoMapper.toDTO(produto);
    }
    
    @Transactional
    public ProdutoDTO criar(@Valid ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto.persist();
        return produtoMapper.toDTO(produto);
    }
    
    @Transactional
    public ProdutoDTO atualizar(Long id, @Valid ProdutoDTO produtoDTO) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        
        produtoMapper.updateEntity(produto, produtoDTO);
        return produtoMapper.toDTO(produto);
    }
    
    @Transactional
    public void excluir(Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        
        // Verificar se há agendamentos vinculados
        long countAgendas = produto.agendas.size();
        if (countAgendas > 0) {
            throw new RuntimeException("Não é possível excluir produto com " + countAgendas + " agendamento(s) vinculado(s)");
        }
        
        produto.delete();
    }
    
    public Double buscarPrecoPorCategoria(Long idProduto, int categoria) {
        Produto produto = Produto.findById(idProduto);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado com ID: " + idProduto);
        }
        return produto.getPrecoByCategoria(categoria);
    }
}
