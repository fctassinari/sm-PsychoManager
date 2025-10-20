package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.util.AbstractCommonFacade;
import br.com.iidh.psmng.util.Padroes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tassinari
 */
public abstract class AbstractFacade<T> extends AbstractCommonFacade {

//    @PersistenceContext(unitName = "postgre")
//    private EntityManager emPostgres;

    @PersistenceContext(unitName = "mysql")
    private EntityManager emMySql;

    private Class<T> entityClass;

    private int banco = Padroes.BANCO_DEFAULT;

    public AbstractFacade(Class<T> entityClass) {
        super(entityClass);
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
//        if (banco == Padroes.POSTGRES) {
//            return emPostgres;
//        } else if (banco == Padroes.MYSQL) {
            return emMySql;
//        }
//        return null;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(Object id) {
        getEntityManager().remove(emMySql.getReference(entityClass, id));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
}
