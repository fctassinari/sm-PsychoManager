package br.com.iidh.psmng.util.chart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tassinari
 */
public abstract class AbstractChartDataGeneration {

    public ApplicationContext context;

    String FILENAME;

    final boolean ESCREVER_TIPO_DADO = true;
    final boolean NAO_ESCREVER_TIPO_DADO = false;

    final boolean ESCREVER_TIPO_CHART = true;
    final boolean NAO_ESCREVER_TIPO_CHART = false;

    final String TB_TIPO_CHART = "TIPOCHART";
    final String TB_TIPO_DADO = "TIPODADO";
    final String TB_GRUPOS = "GRUPOS";
    final String TB_INFORMACOES = "INFORMACOES";
    final String TB_EIXOX = "EIXOX";
    final String TB_ITENS = "ITENS";
    final String TB_DADOS = "DADOS";

    final int STRING = 1;
    final int DATE = 2;
    final int NUMBER = 3;
    final int BOOLEAN = 4;
    final int DATETIME = 5;
    final int TIMEOFDAY = 6;

    final int PIE_CHART = 1;
    final int COMBINED_CHART = 2;
    final int BAR_CHART = 3;
    final int AREA_CHART = 4;
    final int LINE_CHART = 5;
    final int HORIZONTAL_BAR_CHART = 6;
    final int BUBBLE_CHART = 7;
    final int OHLC_CHART = 8;
    final int DONUT_CHART = 9;
    final int METER_GAUGE_CHART = 10;

    List<String> tbEixoX;
    List<String> tbItens;
    List<String> tbDados;
    List<String> tbInformacoes;
    List<String> tbGrupos;
    List<String> tbTipoDado;
    List<String> tbTipoChart;
    List<String> FIM;

    public AbstractChartDataGeneration() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        tbEixoX = new ArrayList<>();
        tbItens = new ArrayList<>();
        tbDados = new ArrayList<>();
        tbInformacoes = new ArrayList<>();
        tbGrupos = new ArrayList<>();
        tbTipoDado = new ArrayList<>();
        tbTipoChart = new ArrayList<>();
        FIM = new ArrayList<>();

    }

    public void write(Grupo grupo, boolean escreverTipoChart, boolean escreverTipoDado) {
        if(grupo.getLinha() != null) // qdo for null indica que sera usado id de grupo ja existente
            tbGrupos.add(grupo.getLinha());
        // id_informacao;id_grupo;ds_titulo;st_status;ds_tituloChart;ds_vAxis;ds_hAxis;id_ItemCombined;id_TipoChartCombined;id_tp_chart
        for (Informacao info : grupo.getInformacoes().values()) {
            for (Item item : info.getItens().values()) {
                for (Dado dado : item.getDados().values()) {
                    tbDados.add(dado.getLinha());
                }
                tbItens.add(item.getLinha());
            }
            tbInformacoes.add(info.getLinha());
            for (EixoX eixox : info.getEixoXs().values()) {
                tbEixoX.add(eixox.getLinha());
            }
        }
        writeTBTipoChart(escreverTipoChart);
        writeTBTipoDado(escreverTipoDado);
        writeTBGrupos();
        writeTBInformacoes();
        writeTBItens();
        writeTBDados();
        writeTBEixoX();
        writeFim();
    }

    private void writeTBDados() {
        tbDados.add(0, TB_DADOS);
        // Implementar analizador dos dados da List
        writeFile(tbDados);
    }

    private void writeTBItens() {
        tbItens.add(0, TB_ITENS);
        // Implementar analizador dos dados da List
        writeFile(tbItens);
    }

    private void writeTBEixoX() {
        tbEixoX.add(0, TB_EIXOX);
        // Implementar analizador dos dados da List
        writeFile(tbEixoX);
    }

    private void writeTBInformacoes() {
        tbInformacoes.add(0, TB_INFORMACOES);
        // Implementar analizador dos dados da List
        writeFile(tbInformacoes);
    }

    private void writeTBGrupos() {
        tbGrupos.add(0, TB_GRUPOS);
        // Implementar analizador dos dados da List
        writeFile(tbGrupos);
    }

    private void writeTBTipoChart(boolean escreverConteudo) {
        tbTipoChart.add(0, TB_TIPO_CHART);
        //id_tp_chart;ds_titulo
        tbTipoChart.add(PIE_CHART + ";Pie Chart");
        tbTipoChart.add(COMBINED_CHART + ";Combined Chart");
        tbTipoChart.add(BAR_CHART + ";Bar Chart");
        tbTipoChart.add(AREA_CHART + ";Area Chart");
        tbTipoChart.add(LINE_CHART + ";Line Chart");
        tbTipoChart.add(HORIZONTAL_BAR_CHART + ";Horizontal Bar Chart");
        tbTipoChart.add(BUBBLE_CHART + ";Bubble Chart");
        tbTipoChart.add(OHLC_CHART + ";Ohlc Chart");
        tbTipoChart.add(DONUT_CHART + ";Donut Chart");
        tbTipoChart.add(METER_GAUGE_CHART + ";Meter Gauge Chart");
        // Implementar analizador dos dados da List
        if (escreverConteudo) {
            writeFile(tbTipoChart);
        }
    }

    private void writeTBTipoDado(boolean escreverConteudo) {
        tbTipoDado.add(0, TB_TIPO_DADO);
        // id_tp_dado;ds_titulo
        tbTipoDado.add(STRING + ";string");
        tbTipoDado.add(DATE + ";date");
        tbTipoDado.add(NUMBER + ";number");
        tbTipoDado.add(BOOLEAN + ";boolean");
        tbTipoDado.add(DATETIME + ";datetime");
        tbTipoDado.add(TIMEOFDAY + ";timeofday");
        // Implementar analizador dos dados da List
        if (escreverConteudo) {
            writeFile(tbTipoDado);
        }
    }

    private void writeFim() {
        FIM.add(0, "FIM");
        writeFile(FIM);
    }

    private void writeFile(List<String> list) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(FILENAME, true);
            bw = new BufferedWriter(fw);

            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                bw.write(iterator.next());
                bw.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public abstract void generateData();
}
