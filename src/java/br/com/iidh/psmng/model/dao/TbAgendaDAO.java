package br.com.iidh.psmng.model.dao;

import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.model.entities.TbAgendaPK;
import br.com.iidh.psmng.model.entities.TbProduto;
import br.com.iidh.psmng.util.Padroes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tassinari
 */
@Repository
public class TbAgendaDAO extends AbstractFacade<TbAgenda> {

    private final SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TbAgendaDAO() {
        super(TbAgenda.class);
    }

    public TbAgenda findByIdPacienteDTConsulta(TbAgendaPK agendapk) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findByIdDtConsulta")
                .setParameter("idPaciente", agendapk.getIdPaciente())
                .setParameter("dtConsulta", agendapk.getDtConsulta())
                .getResultList();
        if (lst == null || lst.isEmpty()) {
            return null;
        }
        return lst.get(0);
    }

    public List<TbAgenda> findConsultasEmAbertoPorPeriodo(Date de, Date ate) {

        Query query = getEntityManager().createNamedQuery("TbAgenda.findConsultasEmAbertoPorPeriodo")
                .setParameter("dtDe", de)
                .setParameter("dtAte", ate);

        List<TbAgenda> lst;
        lst = query.getResultList();

        return lst;
    }

    public List<TbAgenda> findConsultasEmAbertoPorPaciente(Integer idPaciente) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findByIdPacienteConsultaAberta")
                .setParameter("idPaciente", idPaciente)
                .getResultList();
        return lst;
    }

    public List<TbAgenda> findPresencasNaoConfirmadas(Integer idPaciente) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findByIdPacientePresencasNaoConfirmadas")
                .setParameter("idPaciente", idPaciente)
                .getResultList();
        return lst;
    }

    public List<TbAgenda> findConsultasRealizadasPorPaciente(Integer idPaciente, Date de, Date ate) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findConsultasRealizadasPorPaciente")
                .setParameter("idPaciente", idPaciente)
                .setParameter("dtDe", de)
                .setParameter("dtAte", ate)
                .getResultList();
        return lst;
    }

    public List<TbAgenda> findConsultasRealizadasPorPeriodo(Date de, Date ate) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findConsultasRealizadasPorPeriodo")
                .setParameter("dtDe", de)
                .setParameter("dtAte", ate)
                .getResultList();
        return lst;
    }

    public List<TbAgenda> findConsultasAgendadasPorPeriodo(Date de, Date ate) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findConsultasAgendadasPorPeriodo")
                .setParameter("dtDe", de)
                .setParameter("dtAte", ate)
                .getResultList();
        return lst;
    }

    public List<TbAgenda> findConsultasAgendadasPorPeriodoPorPaciente(Integer idPaciente, Date de, Date ate) {
        List<TbAgenda> lst;
        lst = getEntityManager().createNamedQuery("TbAgenda.findConsultasAgendadasPorPeriodoPorPaciente")
                .setParameter("idPaciente", idPaciente)
                .setParameter("dtDe", de)
                .setParameter("dtAte", ate)
                .getResultList();
        return lst;
    }
    

    public List<TbAgenda> findConsultaGenerica(Integer idPaciente, Date de, Date ate, String idProduto, int presenca, int pago, int ordenarPor) {
        List<TbAgenda> lst;
        lst = getEntityManager().createQuery("SELECT ag FROM " + TbAgenda.class.getName() + " ag WHERE "
                + "ag.tbAgendaPK.idPaciente " + (idPaciente == 0 ? "not in(" + idPaciente + ")" : "in(" + idPaciente + ")") + "  AND "
                + "ag.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "' AND "
                + "ag.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "' AND "
                + "(ag.stPresenca in( :pres ) OR "
                + "ag.stPresenca in( :pres2 )) AND "
                + (pago == 99 ? "ag.stPagamento not in(" + pago + ")" : "ag.stPagamento in(" + pago + ")") + " AND "
                + "ag.tbProduto.idProduto in (" + idProduto + ") ORDER BY "
                + (ordenarPor == 1 ? "ag.tbPacienteAgenda.dsNome,ag.tbAgendaPK.dtConsulta" : "ag.tbAgendaPK.dtConsulta,ag.tbPacienteAgenda.dsNome")
        ).setParameter("pres", (presenca == 1 ? Boolean.TRUE : presenca == 2 ? Boolean.FALSE : Boolean.TRUE))
                .setParameter("pres2", (presenca == 1 ? Boolean.TRUE : presenca == 2 ? Boolean.FALSE : Boolean.FALSE))
                .getResultList();
//        lst = getEntityManager().createQuery("SELECT ag FROM " + TbAgenda.class.getName() + " ag WHERE "
//                                                     + "ag.tbAgendaPK.idPaciente "      + (idPaciente == 0?"not in(" + idPaciente + ")":"in(" + idPaciente + ")") + "  AND " 
//                                                     + "ag.tbAgendaPK.dtConsulta >= '"  + formatoData.format(de)                                                  + "' AND " 
//                                                     + "ag.tbAgendaPK.dtConsulta <= '"  + formatoData.format(ate)                                                 + "' AND " 
//                                                     + "(ag.stPresenca in( :pres ) OR " 
//                                                     + "ag.stPresenca in( :pres2 )) AND " 
//                                                     + "(ag.stPg in( :pag ) OR "
//                                                     + "ag.stPg in( :pag2 )) AND "
//                                                     + "ag.tbProduto.idProduto in ("   + idProduto                                                               + ") ORDER BY "
//                                                     + (ordenarPor == 1?"ag.tbPacienteAgenda.dsNome,ag.tbAgendaPK.dtConsulta":"ag.tbAgendaPK.dtConsulta,ag.tbPacienteAgenda.dsNome")
//                                                     ).setParameter("pres", (presenca==1?Boolean.TRUE:presenca==2?Boolean.FALSE:Boolean.TRUE))
//                                                      .setParameter("pres2", (presenca==1?Boolean.TRUE:presenca==2?Boolean.FALSE:Boolean.FALSE))
//                                                      .setParameter("pag", (pago==1?Boolean.TRUE:pago==2?Boolean.FALSE:Boolean.TRUE))
//                                                      .setParameter("pag2", (pago==1?Boolean.TRUE:pago==2?Boolean.FALSE:Boolean.FALSE))
//                                                      .getResultList();

        return lst;

    }

    public Double findConsultaGenericaSomatoria(Integer idPaciente, Date de, Date ate, String idProduto, int presenca, int pago, int ordenarPor) {

        Double preco = (Double) getEntityManager().createQuery("SELECT sum(ag.vlPreco) FROM " + TbAgenda.class.getName() + " ag WHERE "
                + "ag.tbAgendaPK.idPaciente " + (idPaciente == 0 ? "not in(" + idPaciente + ")" : "in(" + idPaciente + ")") + "  AND "
                + "ag.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "' AND "
                + "ag.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "' AND "
                + "(ag.stPresenca in( :pres ) OR "
                + "ag.stPresenca in( :pres2 )) AND "
                + (pago == 99 ? "ag.stPagamento not in(" + pago + ")" : "ag.stPagamento in(" + pago + ")") + " AND "
                + "ag.tbProduto.idProduto in (" + idProduto + ") "
        ).setParameter("pres", (presenca == 1 ? Boolean.TRUE : presenca == 2 ? Boolean.FALSE : Boolean.TRUE))
                .setParameter("pres2", (presenca == 1 ? Boolean.TRUE : presenca == 2 ? Boolean.FALSE : Boolean.FALSE))
                .getSingleResult();
//        Double preco = (Double)getEntityManager().createQuery("SELECT sum(ag.vlPreco) FROM " + TbAgenda.class.getName() + " ag WHERE "
//                                                     + "ag.tbAgendaPK.idPaciente "      + (idPaciente == 0?"not in(" + idPaciente + ")":"in(" + idPaciente + ")") + "  AND " 
//                                                     + "ag.tbAgendaPK.dtConsulta >= '"  + formatoData.format(de)                                                  + "' AND " 
//                                                     + "ag.tbAgendaPK.dtConsulta <= '"  + formatoData.format(ate)                                                 + "' AND " 
//                                                     + "(ag.stPresenca in( :pres ) OR " 
//                                                     + "ag.stPresenca in( :pres2 )) AND " 
//                                                     + "(ag.stPg in( :pag ) OR "
//                                                     + "ag.stPg in( :pag2 )) AND "
//                                                     + "ag.tbProduto.idProduto in ("   + idProduto                                                               + ") "
//                                                     ).setParameter("pres", (presenca==1?Boolean.TRUE:presenca==2?Boolean.FALSE:Boolean.TRUE))
//                                                      .setParameter("pres2", (presenca==1?Boolean.TRUE:presenca==2?Boolean.FALSE:Boolean.FALSE))
//                                                      .setParameter("pag", (pago==1?Boolean.TRUE:pago==2?Boolean.FALSE:Boolean.TRUE))
//                                                      .setParameter("pag2", (pago==1?Boolean.TRUE:pago==2?Boolean.FALSE:Boolean.FALSE))
//                                                      .getSingleResult();

        return preco;
    }

    public List<Object[]> findQtdConsutasProdutoPeriodo(Date de, Date ate) {

        List<Object[]> lst;
        lst = getEntityManager().createQuery("SELECT "
                + "prod.idProduto, "
                + "prod.dsNome, "
                + "(SELECT COUNT(age.tbAgendaPK.idPaciente) as qtd "
                + "FROM " + TbAgenda.class.getName() + " age "
                + "   WHERE "
                + "age.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "'    AND "
                + "age.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "'   AND "
                + "age.tbProduto.idProduto = prod.idProduto AND "
                + "age.stPagamento = 1"
                + "   ) AS QtdConsultas, "
                + "(SELECT sum(age.vlPreco) as vl "
                + "   FROM " + TbAgenda.class.getName() + " age "
                + "   WHERE "
                + "age.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "'    AND "
                + "age.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "'   AND "
                + "age.tbProduto.idProduto = prod.idProduto AND "
                + "age.stPagamento = 1"
                + "   ) AS Valor "
                + "FROM " + TbProduto.class.getName() + " prod "
                + "ORDER BY prod.dsNome").getResultList();

        return lst;

    }

    public List<Object[]> findQtdFormaPagamentoProduto(Date de, Date ate) {
        List<Object[]> lst;
        lst = getEntityManager().createQuery("SELECT "
                + "case when age.stPagamento = 0 then '" + getLabelMessages("AReceber") + "' else "
                + "case when age.stPagamento = 1 then '" + getLabelMessages("Pago") + "' else "
                + "case when age.stPagamento = 2 then '" + getLabelMessages("Abonado") + "' else "
                + "case when age.stPagamento = 3 then '" + getLabelMessages("Calote") + "' else ''"
                + "end end end end as Pagamento, "
                + "prod.idProduto, "
                + "prod.dsNome, "
                + "COUNT(age.tbAgendaPK.idPaciente) AS QtdConsultas, "
                + "sum(age.vlPreco) as Valor "
                + "FROM "
                + TbAgenda.class.getName() + " age, "
                + TbProduto.class.getName() + " prod "
                + "WHERE "
                + "age.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "'    AND "
                + "age.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "'   AND "
                + "age.tbProduto.idProduto = prod.idProduto AND "
                + "age.stPagamento = 1"
                + "GROUP BY age.stPagamento,prod.idProduto,prod.dsNome "
                + "ORDER BY prod.idProduto").getResultList();
        return lst;
    }

    public List<Object[]> findQtdConsutasProdutoPorMesNoPeriodo(GregorianCalendar gcDe, GregorianCalendar gcAte, Integer idProduto) {

        Object nomeProduto = null;
        List<Object[]> lst = null;
        List<Object[]> result = new LinkedList();
        GregorianCalendar de = new GregorianCalendar(gcDe.get(GregorianCalendar.YEAR), gcDe.get(GregorianCalendar.MONTH), 1, 0, 0, 0);
        GregorianCalendar ate = new GregorianCalendar(gcAte.get(GregorianCalendar.YEAR), gcAte.get(GregorianCalendar.MONTH) + 1, 1, 0, 0, 0); // é somado um mes para que o while abaixo nao termine um mes antes

        while (!de.equals(ate)) {
            int ano = de.get(GregorianCalendar.YEAR);
            int mes = de.get(GregorianCalendar.MONTH) + 1;

            lst = getEntityManager().createQuery("select "
                    + "prd.idProduto, "
                    + "prd.dsNome, "
                    + "count(age.tbAgendaPK.idPaciente), "
                    + "sum(age.vlPreco) as vl, "
                    + mes + ", "
                    + "case when " + mes + " = 1      then 'Jan' else "
                    + "case when " + mes + " = 2      then 'Fev' else "
                    + "case when " + mes + " = 3      then 'Mar' else "
                    + "case when " + mes + " = 4      then 'Abr' else "
                    + "case when " + mes + " = 5      then 'Mai' else "
                    + "case when " + mes + " = 6      then 'Jun' else "
                    + "case when " + mes + " = 7      then 'Jul' else "
                    + "case when " + mes + " = 8      then 'Ago' else "
                    + "case when " + mes + " = 9      then 'Set' else "
                    + "case when " + mes + " = 10     then 'Out' else "
                    + "case when " + mes + " = 11     then 'Nov' else "
                    + "case when " + mes + " = 12     then 'Dez' else ''"
                    + "end end end end end end end end end end end end "
                    + "from " + TbAgenda.class.getName() + " age, " + TbProduto.class.getName() + " prd "
                    + "where "
                    + "age.idProduto = " + idProduto + " and "
                    + "extract(month from age.tbAgendaPK.dtConsulta) = " + mes + " and "
                    + "extract(year from age.tbAgendaPK.dtConsulta)= " + ano + " and "
                    + "prd.idProduto = age.idProduto and "
                    + "age.stPagamento = 1"
                    + "group by  prd.idProduto").getResultList();
            if (!lst.isEmpty()) {
                if (nomeProduto == null) {
                    nomeProduto = lst.get(0)[1];
                }
                result.add(lst.get(0));
            } else {
                Object[] linha = {idProduto, nomeProduto, 0, 0, mes, getNomeMes(Integer.valueOf(mes))};
                result.add(linha);
            }
            de.add(GregorianCalendar.MONTH, 1); //soma um mes
        }

        return result;

        // Esta query faz o que precisa mas o JPA+Hibernate não monta corretamente o group by
        // na query o alias "mes" é compilado como col_2_0_ mas só o group by fica como mes
        /*lst = getEntityManager().createQuery("SELECT "
                + "prd.idProduto, prd.dsNome, extract(month from age.tbAgendaPK.dtConsulta) as mes, "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 1  then 'Janeiro'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 2  then 'Fevereiro'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 3  then 'Março'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 4  then 'Abril'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 5  then 'Maio'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 6  then 'Junho'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 7  then 'Julho'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 8  then 'Agosto'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 9  then 'Setembro'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 10  then 'Outubro'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 11  then 'Novembro'  else  "
                + "case when extract(month from age.tbAgendaPK.dtConsulta) = 12  then 'Dezembro'  else ''  "
                + "end end end end end end end end end end end end as descr, "
                + "COUNT(age.tbAgendaPK.idPaciente) as qtd, "
                + "sum(age.vlPreco) as vl  "
                + "FROM " + TbAgenda.class.getName() + " age, " + TbProduto.class.getName() + " prd "
                + "WHERE " 
                + "age.idProduto = prd.idProduto and "
                + "prd.idProduto = " + idProduto + " and "
                + "age.tbAgendaPK.dtConsulta >= '" + formatoData.format(de) + "'    AND "
                + "age.tbAgendaPK.dtConsulta <= '" + formatoData.format(ate) + "'"
                + "GROUP BY prd.idProduto,mes "
                + "ORDER BY mes").getResultList();*/
    }

    private String getNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
        }
        return null;
    }

}
