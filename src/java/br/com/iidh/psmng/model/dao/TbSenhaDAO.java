package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TbSenha;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TbSenhaDAO extends AbstractFacade<TbSenha> {

    public TbSenhaDAO() {
        super(TbSenha.class);
    }
    
}
