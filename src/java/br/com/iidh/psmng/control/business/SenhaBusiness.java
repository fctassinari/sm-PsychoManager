package br.com.iidh.psmng.control.business;

import br.com.iidh.psmng.model.dao.TbSenhaDAO;
import br.com.iidh.psmng.model.entities.TbSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SenhaBusiness {

    @Autowired
    private TbSenhaDAO dao;

    public void setBanco(int param) {
        this.dao.setBanco(param);
    }

    public void persistirSenha(TbSenha entity) {
        this.dao.create(entity);
    }

    public TbSenha buscarSenha(int id) {
        return this.dao.find(id);
    }

    public void removerSenha(TbSenha entity) {
        this.dao.remove(entity.getIdPaciente());
    }

    public void atualizarSenha(TbSenha entity) {
        this.dao.edit(entity);
    }
}
