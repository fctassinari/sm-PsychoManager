package br.com.iidh.psmng.control.business;

import br.com.iidh.psmng.model.dao.TBAuthenticationUserDAO;
import br.com.iidh.psmng.model.entities.TBAuthenticationUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserBusiness {

    @Autowired
    private TBAuthenticationUserDAO dao;

    public void setBanco(int param) {
        this.dao.setBanco(param);
    }

    public void persistirUser(TBAuthenticationUser entity) {
        this.dao.create(entity);

    }

    public TBAuthenticationUser find(String id) {
        return this.dao.find(id);
    }

    public void removerUser(TBAuthenticationUser entity) {
        this.dao.remove(entity.getUserid());
    }

    public void atualizarUser(TBAuthenticationUser entity) {
        this.dao.edit(entity);
    }

    public List<TBAuthenticationUser> findLikeNome(Object pname) {
        return this.dao.findLikeNome(pname);
    }

    public List<TBAuthenticationUser> findAll() {
        return this.dao.findAll();
    }
}
