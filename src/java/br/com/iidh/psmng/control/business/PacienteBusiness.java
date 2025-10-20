package br.com.iidh.psmng.control.business;

import br.com.iidh.psmng.model.dao.TbPacienteDAO;
import br.com.iidh.psmng.model.entities.TbPaciente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PacienteBusiness {

    @Autowired
    private TbPacienteDAO dao;

    public void setBanco(int param) {
        this.dao.setBanco(param);
    }

    public void persistirPaciente(TbPaciente entity) {
        this.dao.create(entity);
    }

    public TbPaciente buscarPaciente(int id) {
        return this.dao.find(id);
    }

    public void removerPaciente(TbPaciente entity) {
        this.dao.remove(entity.getIdPaciente());
    }

    public void atualizarPaciente(TbPaciente entity) {
        this.dao.edit(entity);
    }

    public List<TbPaciente> findPartNome(String param) {
        return this.dao.findPartNome(param);
    }

    public List<TbPaciente> findLikeNome(Object pname) {
        return this.dao.findLikeNome(pname);
    }

    public List<TbPaciente> findById(int id) {
        return this.dao.findById(id);
    }
    
    public List<TbPaciente> findAllOrderID() {
        return this.dao.findAllOrderID();
    }
}
