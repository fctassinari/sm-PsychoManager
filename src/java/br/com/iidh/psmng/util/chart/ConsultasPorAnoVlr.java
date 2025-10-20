package br.com.iidh.psmng.util.chart;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import br.com.iidh.psmng.control.business.ProdutoBusiness;
import br.com.iidh.psmng.model.entities.TbProduto;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Tassinari
 */
public final class ConsultasPorAnoVlr extends AbstractChartDataGeneration {

    private final int ano = 2016;
    private boolean criarNovoGrupo = false;

    private ProdutoBusiness prodBuss;
    private AgendaBusiness ageBuss;
    private Grupo grupo = null;

    public static void main(String[] args) {
        new ConsultasPorAnoVlr();
    }

    public ConsultasPorAnoVlr() {
        ageBuss = context.getBean(AgendaBusiness.class);
        prodBuss = context.getBean(ProdutoBusiness.class);
        generateData();

// as tabelas tipo_chart e tipo_dado devem ser escritas somente na primenira carga
//        write(grupo, ESCREVER_TIPO_CHART, ESCREVER_TIPO_DADO);
        write(grupo, NAO_ESCREVER_TIPO_CHART, NAO_ESCREVER_TIPO_DADO);

        System.out.println("FINITO !!!");
    }

    @Override
    public void generateData() {
        GregorianCalendar gcDe = new GregorianCalendar();
        gcDe.set(GregorianCalendar.MONTH, 0); // janeiro
        gcDe.set(GregorianCalendar.YEAR, ano);
        gcDe.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gcDe.set(GregorianCalendar.HOUR, 0);
        gcDe.set(GregorianCalendar.MINUTE, 0);
        gcDe.set(GregorianCalendar.SECOND, 0);

        GregorianCalendar gcAte = new GregorianCalendar();
        gcAte.set(GregorianCalendar.MONTH, 11); // dezembro
        gcAte.set(GregorianCalendar.YEAR, ano);
        gcAte.set(GregorianCalendar.DAY_OF_MONTH, 31);
        gcAte.set(GregorianCalendar.HOUR, 11);
        gcAte.set(GregorianCalendar.MINUTE, 59);
        gcAte.set(GregorianCalendar.SECOND, 59);

        FILENAME = "PsychoManagerDataChartAnualVlr- " + gcDe.get(GregorianCalendar.YEAR) + ".txt";

// -----------------------------------------------------------------------------
// Grafico contendo a quantidade atendimentos por produto        
// -----------------------------------------------------------------------------
        grupo = new Grupo();

        if (criarNovoGrupo) {
            // Exemplo para criar grupo novo
            // id_grupo;ds_titulo;st_status
            grupo.setTitulo("Consultas");
            grupo.setStatus(true);
            grupo.setIdRegistro(1);
        } else {
            // Exemplo para usar grupo já cadastrado
            // Informar o codigo do grupo já existente
            grupo.setIdGrupoExistente(1);
        }

        // id_informacao;id_grupo;ds_titulo;st_status;ds_tituloChart;ds_vAxis;ds_hAxis;id_ItemCombined;id_TipoChartCombined;id_tp_chart
        Informacao informacao = new Informacao();
        informacao.setTitulo("Receita por Produto - " + gcDe.get(GregorianCalendar.YEAR));
        informacao.setStatus(true);
        informacao.setTituloChart("Período " + (gcDe.get(GregorianCalendar.MONTH) + 1) + "/" + gcDe.get(GregorianCalendar.YEAR) + " a " + (gcAte.get(GregorianCalendar.MONTH) + 1) + "/" + gcAte.get(GregorianCalendar.YEAR));
        informacao.setTituloEixoY("Valores");
        informacao.setTituloEixoX("Produtos");
        informacao.setIdItemCombined(null);
        informacao.setIdTipoChartCombined(null);
        informacao.setTipoChart(HORIZONTAL_BAR_CHART);
        informacao.setDrillDown(false);
        informacao.setIdRegistro(informacao.getIdInformacao());

        grupo.setInformacao(informacao.getIdRegistro(), informacao);

        // id_item;id_informacao;ds_titulo;st_status;id_tp_dado
        Item item = new Item();
        item.setTitulo("Vlr");
        item.setStatus(true);
        item.setTipoDado(NUMBER);
        item.setIdRegistro(item.getIdItem());

        informacao.setItem(item.getIdRegistro(), item);

        List<Object[]> lstage = ageBuss.findQtdConsutasProdutoPeriodo(gcDe.getTime(), gcAte.getTime());
        for (int i = 0; i < lstage.size(); i++) {
            // id_dado;id_item;ds_titulo;st_status;id_informacao
            Dado dado = new Dado();
            dado.setIdRegistro((Integer) ((Object[]) lstage.get(i))[0]); // id produto
            dado.setTitulo(String.valueOf(((Object[]) lstage.get(i))[3])); // valor
            dado.setIdInformacao(null);
            dado.setStatus(true);
            item.setDado(dado.getIdRegistro(), dado);
            EixoX eixoX = new EixoX();
            eixoX.setTitulo((String) ((Object[]) lstage.get(i))[1]);
            informacao.setEixoX(eixoX.getIdEixoX(), eixoX);
        }

// -----------------------------------------------------------------------------
// DrillDown Quantidade de produtos vendidos por mes
// -----------------------------------------------------------------------------
        int codigoProduto = 0;
        List<TbProduto> lstprod = prodBuss.findAll();
        Informacao info = null;
        Item it = null;
        for (int i = 0; i < lstprod.size(); i++) {
            codigoProduto = lstprod.get(i).getIdProduto();
            // id_informacao;id_grupo;ds_titulo;st_status;ds_tituloChart;ds_vAxis;ds_hAxis;id_ItemCombined;id_TipoChartCombined;id_tp_chart
            info = new Informacao();
            info.setTitulo("Formas de Pagamento");
            info.setStatus(true);
            info.setTituloChart(lstprod.get(i).getDsNome()); // descrição do produto
            info.setTituloEixoY("Valores");
            info.setTituloEixoX("Meses");
            info.setIdItemCombined(null);
            info.setIdTipoChartCombined(null);
            info.setTipoChart(LINE_CHART);
            info.setDrillDown(true);
            info.setIdRegistro(info.getIdInformacao());

            grupo.setInformacao(info.getIdRegistro(), info);

            // id_item;id_informacao;ds_titulo;st_status;id_tp_dado
            it = new Item();
            it.setTitulo("Consultas"); // mes
            it.setStatus(true);
            it.setTipoDado(NUMBER);
            it.setIdRegistro(it.getIdItem());
            info.setItem(it.getIdRegistro(), it);

            List<Object[]> lstdd = ageBuss.findQtdConsutasProdutoPorMesNoPeriodo(gcDe, gcAte, lstprod.get(i).getIdProduto());

            for (int x = 0; x < lstdd.size(); x++) {
                // id_dado;id_item;ds_titulo;st_status;id_informacao
                Dado dado = new Dado();
                dado.setIdRegistro(codigoProduto);
                dado.setTitulo(String.valueOf(((Object[]) lstdd.get(x))[3]));      // valor
                dado.setIdInformacao(null);
                dado.setStatus(true);
                String identificador = String.valueOf(dado.getIdRegistro()) + String.valueOf(dado.getIdDado());
                it.setDado(Integer.valueOf(identificador), dado);

                // id_eixox;id_informacao;ds_titulo
                EixoX eixoX = new EixoX();
                eixoX.setTitulo(String.valueOf(((Object[]) lstdd.get(x))[5]));    // mes
                info.setEixoX(eixoX.getIdEixoX(), eixoX);
            }

            // Localiza na Informação principal o dado associado a este produto para linkar o codigo da sub informacao e fazer o drilldown
            informacao.setCodigoDD(info.getIdInformacao(), item.getIdRegistro(), codigoProduto);
        }

    }

}
