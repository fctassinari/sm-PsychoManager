package br.com.iidh.psycho.service;

import br.com.iidh.psycho.entity.Senha;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SenhaService {
    
    @Transactional
    public void criarSenha(Long idPaciente, String senha) {
        Senha senhaEntity = new Senha();
        senhaEntity.idPaciente = idPaciente;
        senhaEntity.dsSenha = senha;
        senhaEntity.persist();
    }
    
    @Transactional
    public void atualizarSenha(Long idPaciente, String novaSenha) {
        Senha senha = Senha.findByPaciente(idPaciente);
        if (senha == null) {
            criarSenha(idPaciente, novaSenha);
        } else {
            senha.dsSenha = novaSenha;
        }
    }
    
    public String buscarSenhaPorPaciente(Long idPaciente) {
        Senha senha = Senha.findByPaciente(idPaciente);
        return senha != null ? senha.dsSenha : null;
    }
    
    @Transactional
    public void excluirSenha(Long idPaciente) {
        Senha.delete("idPaciente = ?1", idPaciente);
    }
}
