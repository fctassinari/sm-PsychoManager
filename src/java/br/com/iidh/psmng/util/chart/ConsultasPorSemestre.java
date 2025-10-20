package br.com.iidh.psmng.util.chart;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Tassinari
 */
public final class ConsultasPorSemestre extends AbstractChartDataGeneration {


    private final int ano = 2016;
    private boolean criarNovoGrupo = true;


    private AgendaBusiness ageBuss;
    private Grupo grupo = null;

    public static void main(String[] args) {
        new ConsultasPorSemestre();
    }

    public ConsultasPorSemestre() {
        ageBuss = context.getBean(AgendaBusiness.class);
        generateData();

// as tabelas tipo_chart e tipo_dado devem ser escritas somente na primenira carga
//        write(grupo, ESCREVER_TIPO_CHART, ESCREVER_TIPO_DADO);
        write(grupo, NAO_ESCREVER_TIPO_CHART, NAO_ESCREVER_TIPO_DADO);

        System.out.println("FINITO !!!");
    }

    @Override
    public void generateData() {
        GregorianCalendar gcDe1 = new GregorianCalendar();
        gcDe1.set(GregorianCalendar.MONTH, 0); // janeiro
        gcDe1.set(GregorianCalendar.YEAR, ano);
        gcDe1.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gcDe1.set(GregorianCalendar.HOUR, 0);
        gcDe1.set(GregorianCalendar.MINUTE, 0);
        gcDe1.set(GregorianCalendar.SECOND, 0);

        GregorianCalendar gcAte1 = new GregorianCalendar();
        gcAte1.set(GregorianCalendar.MONTH, 5); // junho
        gcAte1.set(GregorianCalendar.YEAR, ano);
        gcAte1.set(GregorianCalendar.DAY_OF_MONTH, 30);
        gcAte1.set(GregorianCalendar.HOUR, 11);
        gcAte1.set(GregorianCalendar.MINUTE, 59);
        gcAte1.set(GregorianCalendar.SECOND, 59);

        GregorianCalendar gcDe2 = new GregorianCalendar();
        gcDe2.set(GregorianCalendar.MONTH, 6); // julho
        gcDe2.set(GregorianCalendar.YEAR, ano);
        gcDe2.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gcDe2.set(GregorianCalendar.HOUR, 0);
        gcDe2.set(GregorianCalendar.MINUTE, 0);
        gcDe2.set(GregorianCalendar.SECOND, 0);

        GregorianCalendar gcAte2 = new GregorianCalendar();
        gcAte2.set(GregorianCalendar.MONTH, 11); // dezembro
        gcAte2.set(GregorianCalendar.YEAR, ano);
        gcAte2.set(GregorianCalendar.DAY_OF_MONTH, 31);
        gcAte2.set(GregorianCalendar.HOUR, 11);
        gcAte2.set(GregorianCalendar.MINUTE, 59);
        gcAte2.set(GregorianCalendar.SECOND, 59);

        FILENAME = "PsychoManagerDataChartSemestral- " + gcDe1.get(GregorianCalendar.YEAR) + ".txt";

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
        informacao.setTitulo("Qtd Consultas por Semestre- " + gcDe1.get(GregorianCalendar.YEAR));
        informacao.setStatus(true);
        informacao.setTituloChart(String.valueOf(ano));
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
        item.setTitulo("1° Semestre");
        item.setStatus(true);
        item.setTipoDado(NUMBER);
        item.setIdRegistro(item.getIdItem());

        informacao.setItem(item.getIdRegistro(), item);

        Item item2 = new Item();
        item2.setTitulo("2° Semestre");
        item2.setStatus(true);
        item2.setTipoDado(NUMBER);
        item2.setIdRegistro(item2.getIdItem());

        informacao.setItem(item2.getIdRegistro(), item2);
// -----------------------------------------------------------------------------
// Quantidade de produtos vendidos por semestre
// -----------------------------------------------------------------------------
        // Carregar os dados do 1° Semestre
        List<Object[]> lstage = ageBuss.findQtdConsutasProdutoPeriodo(gcDe1.getTime(), gcAte1.getTime());
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
        // Carregar os dados do 2° Semestre
        List<Object[]> lstage2 = ageBuss.findQtdConsutasProdutoPeriodo(gcDe2.getTime(), gcAte2.getTime());
        for (int i = 0; i < lstage2.size(); i++) {
            // id_dado;id_item;ds_titulo;st_status;id_informacao
            Dado dado = new Dado();
            dado.setIdRegistro((Integer) ((Object[]) lstage2.get(i))[0]); // id produto
            dado.setTitulo(String.valueOf(((Object[]) lstage2.get(i))[2]));      // quantidade
            dado.setIdInformacao(null);
            dado.setStatus(true);
            item2.setDado(dado.getIdRegistro(), dado);
            // O EixoX já foi carregado na iteração anterior
        }
    }

}
