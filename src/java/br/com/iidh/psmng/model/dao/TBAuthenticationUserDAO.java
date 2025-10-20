package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TBAuthenticationUser;
import br.com.iidh.psmng.util.Padroes;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TBAuthenticationUserDAO extends AbstractFacade<TBAuthenticationUser> {

    public TBAuthenticationUserDAO() {
        super(TBAuthenticationUser.class);
    }

    public List<TBAuthenticationUser> findAll() {
        return getEntityManager().createNamedQuery("TBAuthenticationUser.findAll").getResultList();
    }

    public List<TBAuthenticationUser> findLikeNome(Object pname) {
        List<TBAuthenticationUser> lst;
        lst = getEntityManager().createQuery("FROM " + TBAuthenticationUser.class.getName() + " where ds_nome like '" + pname.toString() + "' ORDER BY ds_nome").getResultList();
        return lst;
    }
    

}
