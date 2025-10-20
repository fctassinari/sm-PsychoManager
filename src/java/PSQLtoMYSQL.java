import br.com.iidh.psmng.control.business.AgendaBusiness;
import br.com.iidh.psmng.control.business.PacienteBusiness;
import br.com.iidh.psmng.control.business.ProdutoBusiness;
import br.com.iidh.psmng.control.business.SenhaBusiness;
import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.model.entities.TbFone;
import br.com.iidh.psmng.model.entities.TbPaciente;
import br.com.iidh.psmng.model.entities.TbProduto;
import br.com.iidh.psmng.model.entities.TbSenha;
import br.com.iidh.psmng.util.Padroes;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tassinari
 *
 */
public class PSQLtoMYSQL {

    ApplicationContext contextPostgres;
    ApplicationContext contextMySql;
    PacienteBusiness pacBus;
    AgendaBusiness ageBus;
    ProdutoBusiness prdBus;
    SenhaBusiness senBus;

    public static void main(String args[]) {
        try {
            
/***************************************/
// ATENCAO:
// Antes de executar este programa
// olhar a classe TbPaciente e fazer o ajuste
// na declaração da tbPacienteAgenda
// la esta descrito a alteração
/**************************************/

            new PSQLtoMYSQL().carregar();
//            new PSQLtoMYSQL().carregarDadosPacientes();
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PSQLtoMYSQL.class.getName()).error(ex);
        }
    }

    public void carregar() throws Exception {
        contextPostgres = new ClassPathXmlApplicationContext("./applicationContext.xml");
        carregarProdutos();
        // PARA CARREGAR OS DADOS DO PACIENTE, DEVE-SE ZERAR O BANCO PARA QUE O PACIENTE SEJA GRAVADO COM O MESMO ID
        carregarPacientes();
    }

    public void carregarProdutos() throws Exception {
        prdBus = contextPostgres.getBean(ProdutoBusiness.class);

        prdBus.setBanco(Padroes.POSTGRES);
        List<TbProduto> lstPrd = prdBus.findAllOrderID();

        System.out.println("\n =================== AGENDA POSTGRES=======================");
        for (TbProduto a : lstPrd) {
            System.out.println(a.getIdProduto() + " " + a.getDsNome());
        }

        prdBus.setBanco(Padroes.MYSQL);
        for (TbProduto a : lstPrd) {
            a.setIdProduto(null);
            prdBus.persistirProduto(a);
        }

        lstPrd = prdBus.findAllOrderID();

        System.out.println("\n =================== AGENDA MYSQL=======================");
        for (TbProduto a : lstPrd) {
            System.out.println(a.getIdProduto() + " " + a.getDsNome());
        }
    }

    public void carregarPacientes() throws Exception {

        pacBus = contextPostgres.getBean(PacienteBusiness.class);
        ageBus = contextPostgres.getBean(AgendaBusiness.class);
        senBus = contextPostgres.getBean(SenhaBusiness.class);

        GregorianCalendar gcDe = new GregorianCalendar();
        gcDe.set(GregorianCalendar.MONTH, 0); // janeiro
        gcDe.set(GregorianCalendar.YEAR, 2015);
        gcDe.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gcDe.set(GregorianCalendar.HOUR, 0);
        gcDe.set(GregorianCalendar.MINUTE, 0);
        gcDe.set(GregorianCalendar.SECOND, 0);

        GregorianCalendar gcAte = new GregorianCalendar();
        gcAte.set(GregorianCalendar.MONTH, 11); // dezembro
        gcAte.set(GregorianCalendar.YEAR, 2020);
        gcAte.set(GregorianCalendar.DAY_OF_MONTH, 31);
        gcAte.set(GregorianCalendar.HOUR, 11);
        gcAte.set(GregorianCalendar.MINUTE, 59);
        gcAte.set(GregorianCalendar.SECOND, 59);

//        ageBus.setBanco(Padroes.POSTGRES);
//        List<TbAgenda> lstAgenda = ageBus.findConsultasAgendadasPorPeriodoPorPaciente(1, gcDe.getTime(), gcAte.getTime());
//        Collection<TbFone> colFone = pac.getTbFoneCollection();
//
//        System.out.println("\n =================== FONE =========================");
//        for(TbFone f : colFone){
//            System.out.println(f.getIdFone() + " " + f.getNrFone());
//        }
//
//        int cont = 1;
//        System.out.println("\n =================== AGENDA =======================");
//        for (TbAgenda a : lstAgenda) {
//            System.out.println(cont++ + " " + a.getTbAgendaPK().getDtConsulta());
//        }
        pacBus.setBanco(Padroes.POSTGRES);
        List<TbPaciente> lstPac = pacBus.findAllOrderID();

        pacBus.setBanco(Padroes.MYSQL);

        for (TbPaciente pac : lstPac) {
            System.out.println(">>>> pac inicial " + pac.getIdPaciente());
            pac.setIdPaciente(null);

            Collection<TbFone> colFone = pac.getTbFoneCollection();
            for (TbFone f : colFone) {
                f.setIdFone(null);
            }

            Collection<TbAgenda> colAge = pac.getTbAgendaCollection();
            pac.setTbAgendaCollection(null);

            TbSenha senha = pac.getTbSenha();
            pac.setTbSenha(null);

            pacBus.persistirPaciente(pac);
            System.out.println(">>>> pac " + pac.getIdPaciente());

            ageBus.setBanco(Padroes.MYSQL);
            for (TbAgenda a : colAge) {
                a.getTbAgendaPK().setIdPaciente(pac.getIdPaciente());
                ageBus.persistirAgenda(a);
//                System.out.println("     >>>> age " + a.getTbAgendaPK().getDtConsulta());
            }

            if (senha != null) {
                senBus.setBanco(Padroes.MYSQL);
                senha.setIdPaciente(pac.getIdPaciente());
                senBus.persistirSenha(senha);
            }
        }

    }

}
