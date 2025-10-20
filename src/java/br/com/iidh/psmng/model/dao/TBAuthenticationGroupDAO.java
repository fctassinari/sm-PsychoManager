package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TBAuthenticationGroup;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TBAuthenticationGroupDAO extends AbstractFacade<TBAuthenticationGroup> {

    public TBAuthenticationGroupDAO() {
        super(TBAuthenticationGroup.class);
    }
 
}
