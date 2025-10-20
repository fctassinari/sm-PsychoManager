package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TbPaciente;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TbPacienteDAO extends AbstractFacade<TbPaciente> {

    public TbPacienteDAO() {
        super(TbPaciente.class);
    }

    public List<TbPaciente> findPartNome(Object pname) {
        List<TbPaciente> lst;
        lst = getEntityManager().createQuery("FROM " + TbPaciente.class.getName() + " where ds_nome like '" + pname.toString() + "%' ORDER BY ds_nome").getResultList();
        return lst;
    }

    public List<TbPaciente> findLikeNome(Object pname) {
        List<TbPaciente> lst;
        Query q = getEntityManager().createQuery("FROM " + TbPaciente.class.getName() + " where ds_nome like '" + pname.toString() + "' ORDER BY ds_nome");
        lst = q.getResultList();
        return lst;
    }

    public List<TbPaciente> findById(int id) {
        List<TbPaciente> lst = getEntityManager().createNamedQuery("TbPaciente.findByIdPaciente")
                .setParameter("idPaciente", id)
                .getResultList();
        return lst;
    }

    public List<TbPaciente> findAllOrderID() {
        return getEntityManager().createNamedQuery("TbPaciente.findAllOrderID").getResultList();
    }
}
