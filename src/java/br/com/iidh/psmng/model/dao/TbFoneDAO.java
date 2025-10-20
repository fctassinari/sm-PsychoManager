    package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TbFone;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TbFoneDAO extends AbstractFacade<TbFone> {

    public TbFoneDAO() {
        super(TbFone.class);
    }
    
}
