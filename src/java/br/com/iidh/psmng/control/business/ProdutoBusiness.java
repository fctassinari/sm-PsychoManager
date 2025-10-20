package br.com.iidh.psmng.control.business;

import br.com.iidh.psmng.model.dao.TbProdutoDAO;
import br.com.iidh.psmng.model.entities.TbProduto;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProdutoBusiness {

    @Autowired
    private TbProdutoDAO dao;

    public void setBanco(int param) {
        this.dao.setBanco(param);
    }

    public void persistirProduto(TbProduto entity) {
        this.dao.create(entity);
    }

    public TbProduto buscarProduto(int id) {
        return this.dao.find(id);
    }

    public void removerProduto(TbProduto entity) {
        this.dao.remove(entity.getIdProduto());
    }

    public void atualizarProduto(TbProduto entity) {
        this.dao.edit(entity);
    }

    public List<TbProduto> findPartNome(String param) {
        return this.dao.findPartNome(param);
    }

    public List<TbProduto> findLikeNome(Object pname) {
        return this.dao.findLikeNome(pname);
    }

    public List<TbProduto> findAll() {
        return this.dao.findAll();
    }

    public List<TbProduto> findAllOrderID() {
        return this.dao.findAllOrderID();
    }

    
    public Double findPreco(int idProduto, int preco) {
        List<TbProduto> lst = this.dao.findPreco(idProduto);
        Iterator it = lst.iterator();
        TbProduto prod = (TbProduto) it.next();
        switch (preco) {
            case 1:
                return prod.getVlPreco1();
            case 2:
                return prod.getVlPreco2();
            case 3:
                return prod.getVlPreco3();
            case 4:
                return prod.getVlPreco4();
        }
        return 0.00;
    }
}
