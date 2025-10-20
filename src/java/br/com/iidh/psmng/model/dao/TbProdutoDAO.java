package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TbProduto;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TbProdutoDAO extends AbstractFacade<TbProduto> {

    public TbProdutoDAO() {
        super(TbProduto.class);
    }

    public List<TbProduto> findPartNome(Object pname) {
        List<TbProduto> lst;
        lst = getEntityManager().createQuery("FROM " + TbProduto.class.getName() + " where ds_nome like '" + pname.toString() + "%' ORDER BY ds_nome").getResultList();
        return lst;
    }

    public List<TbProduto> findLikeNome(Object pname) {
        List<TbProduto> lst;
        lst = getEntityManager().createQuery("FROM " + TbProduto.class.getName() + " where ds_nome like '" + pname.toString() + "' ORDER BY ds_nome").getResultList();
        return lst;
    }

    public List<TbProduto> findAll() {
        return getEntityManager().createNamedQuery("TbProduto.findAll").getResultList();
    }

    public List<TbProduto> findAllOrderID() {
        return getEntityManager().createNamedQuery("TbProduto.findAllOrderID").getResultList();
    }

    public List<TbProduto> findPreco(int id) {
        return getEntityManager().createNamedQuery("TbProduto.findByIdProduto")
                .setParameter("idProduto", id)
                .getResultList();
    }
}
