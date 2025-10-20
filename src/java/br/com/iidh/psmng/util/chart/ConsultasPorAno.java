package br.com.iidh.psmng.util.chart;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Tassinari
 */
public final class ConsultasPorAno extends AbstractChartDataGeneration {


    private final int ano = 2016;
    private boolean criarNovoGrupo = false;


    private AgendaBusiness ageBuss;
    private Grupo grupo = null;

    public static void main(String[] args) {
        new ConsultasPorAno();
    }

    public ConsultasPorAno() {
        ageBuss = context.getBean(AgendaBusiness.class);
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

        FILENAME = "PsychoManagerDataChartAnual- " + gcDe.get(GregorianCalendar.YEAR) + ".txt";

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
        informacao.setTitulo("Qtd Consultas por Produto - " + gcDe.get(GregorianCalendar.YEAR));
        informacao.setStatus(true);
        informacao.setTituloChart("Período " + (gcDe.get(GregorianCalendar.MONTH) + 1) + "/" + gcDe.get(GregorianCalendar.YEAR) + " a " + (gcAte.get(GregorianCalendar.MONTH) + 1) + "/" + gcAte.get(GregorianCalendar.YEAR));
        informacao.setTituloEixoY("Quantidades");
        informacao.setTituloEixoX("Produtos");
        informacao.setIdItemCombined(null);
        informacao.setIdTipoChartCombined(null);
        informacao.setTipoChart(BAR_CHART);
        informacao.setDrillDown(false);
        informacao.setIdRegistro(informacao.getIdInformacao());

        grupo.setInformacao(informacao.getIdRegistro(), informacao);

        // id_item;id_informacao;ds_titulo;st_status;id_tp_dado
        Item item = new Item();
        item.setTitulo("Qtd");
        item.setStatus(true);
        item.setTipoDado(NUMBER);
        item.setIdRegistro(item.getIdItem());

        informacao.setItem(item.getIdRegistro(), item);

        List<Object[]> lstage = ageBuss.findQtdConsutasProdutoPeriodo(gcDe.getTime(), gcAte.getTime());
        for (int i = 0; i < lstage.size(); i++) {
            // id_dado;id_item;ds_titulo;st_status;id_informacao
            Dado dado = new Dado();
            dado.setIdRegistro((Integer) ((Object[]) lstage.get(i))[0]); // id produto
            dado.setTitulo(String.valueOf(((Object[]) lstage.get(i))[2]));      // quantidade
            dado.setIdInformacao(null);
            dado.setStatus(true);
            item.setDado(dado.getIdRegistro(), dado);
            EixoX eixoX = new EixoX();
            eixoX.setTitulo((String) ((Object[]) lstage.get(i))[1]);
            informacao.setEixoX(eixoX.getIdEixoX(), eixoX);
        }

// -----------------------------------------------------------------------------
// DrillDown contendo as quantidades de forma de pagamento por produto
// -----------------------------------------------------------------------------
        int codigoProduto = 0;
        int codigoProdutoAnt = 0;
        List<Object[]> lstdd = ageBuss.findQtdFormaPagamentoProduto(gcDe.getTime(), gcAte.getTime());
        Informacao info = null;
        Item it = null;
        for (int i = 0; i < lstdd.size(); i++) {
            codigoProduto = (Integer) ((Object[]) lstdd.get(i))[1];
            if (codigoProduto != codigoProdutoAnt) {
                // id_informacao;id_grupo;ds_titulo;st_status;ds_tituloChart;ds_vAxis;ds_hAxis;id_ItemCombined;id_TipoChartCombined;id_tp_chart
                info = new Informacao();
                info.setTitulo("Formas de Pagamento");
                info.setStatus(true);
                info.setTituloChart("Período " + (String) ((Object[]) lstdd.get(i))[2] + " - " + (gcDe.get(GregorianCalendar.MONTH) + 1) + "/" + gcDe.get(GregorianCalendar.YEAR) + " a " + (gcAte.get(GregorianCalendar.MONTH) + 1) + "/" + gcAte.get(GregorianCalendar.YEAR));
                info.setTituloEixoY("Quantidades");
                info.setTituloEixoX("Produto");
                info.setIdItemCombined(null);
                info.setIdTipoChartCombined(null);
                info.setTipoChart(PIE_CHART);
                info.setDrillDown(true);
                info.setIdRegistro(info.getIdInformacao());

                grupo.setInformacao(info.getIdRegistro(), info);

                // id_item;id_informacao;ds_titulo;st_status;id_tp_dado
                it = new Item();
                it.setTitulo("Qtd");
                it.setStatus(true);
                it.setTipoDado(NUMBER);
                it.setIdRegistro(it.getIdItem());

                info.setItem(it.getIdRegistro(), it);
                codigoProdutoAnt = codigoProduto;
            }

            // id_dado;id_item;ds_titulo;st_status;id_informacao
            Dado dado = new Dado();
            dado.setIdRegistro(codigoProduto);
            dado.setTitulo(String.valueOf(((Object[]) lstdd.get(i))[3]));      // quantidade
            dado.setIdInformacao(null);
            dado.setStatus(true);
            String identificador = String.valueOf(dado.getIdRegistro()) + String.valueOf(dado.getIdDado());
            it.setDado(Integer.valueOf(identificador), dado);

            // id_eixox;id_informacao;ds_titulo
            EixoX eixoX = new EixoX();
            eixoX.setTitulo((String) ((Object[]) lstdd.get(i))[0]);    // nome do pagamento
            info.setEixoX(eixoX.getIdEixoX(), eixoX);

            // Localiza na Informação principal o dado associado a este produto para linkar o codigo da sub informacao e fazer o drilldown
            informacao.setCodigoDD(info.getIdInformacao(), item.getIdRegistro(), codigoProduto);
        }
    }

}
