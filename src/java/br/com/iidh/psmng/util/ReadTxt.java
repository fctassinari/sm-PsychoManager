package br.com.iidh.psmng.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author Tassinari
 *
 * http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
 *
 */
public class ReadTxt {

    String fileName;

    public static void main(String args[]) {
        try {
            ReadTxt re = new ReadTxt("Deuses");

//            String texto = "\"\"";
//            //texto = texto.replace('"', ' ');
//            texto = texto.replaceAll("\"", "");
//            String campo[] =texto.split(";");
//            for(int i = 0; i < campo.length; i++){
//                System.out.println(campo[i]);
//            }
//            re.carregarDadosTxt();
            re.carregarDadosXLSX();
        } catch (Exception ex) {
            Logger.getLogger(ReadTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    public ReadTxt(String fileName) {
        this.fileName = fileName;
    }

    public void carregarDadosTxt() throws Exception {
        try {
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName + ".txt"), "ISO-8859-1"));
            BufferedWriter buffW = new BufferedWriter(new FileWriter(this.fileName + "2.txt"));

            String linha = lerArq.readLine(); // Leu cabeçalho 
            buffW.write(linha); // escreve cabeçalho
            buffW.newLine();

            linha = lerArq.readLine();

            while (linha != null) {
                String campos[] = ((String) linha).split(",");

                buffW.write(campos[0]); //Timestamp
                buffW.write(",");
                buffW.write(campos[1]); //Nome
                buffW.write(",");
                buffW.write(String.valueOf(campos[2].split(";").length)); //Z
                buffW.write(",");
                buffW.write(String.valueOf(campos[3].split(";").length)); //HS
                buffW.write(",");
                buffW.write(String.valueOf(campos[4].split(";").length)); //PO
                buffW.newLine();

                linha = lerArq.readLine();
            }
            buffW.close();
            lerArq.close();
        } catch (IOException e) {
            Logger.getLogger(ReadTxt.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void carregarDadosXLSX() throws Exception {
        BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName + ".txt"), "ISO-8859-1"));
//https://docs.aspose.com/display/cellsjava/Create+Charts+using+Apache+POI+and+Aspose.Cells
        Workbook workbook = new SXSSFWorkbook(100);
        int rownum = 0;

        String linha = lerArq.readLine(); // Leu cabeçalho 
        linha = linha.replaceAll("\"", "");
        String cabec[] = ((String) linha).split(",");

        linha = lerArq.readLine();

        while (linha != null) {
            String campos[] = ((String) linha).split(",");
            Sheet sheet = workbook.createSheet(campos[1]); // Planilha com o nome do paciente
            Row row = sheet.createRow(0);

            // escreve o cabecalho
            int cellnum = 0;
            int cont = 0;
            for (Object obj : cabec) {
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue((String) cabec[cont++]);
            }

            // escreve a linha de detalhe do paciente
            cellnum = 0;
            cont = 0;
            row = sheet.createRow(1);
            String aux;
            for (Object obj : campos) {
                Cell cell = row.createCell(cellnum);
                if (cont == 0 || cont == 1) { //Timestamp ou Nome
                    aux = campos[cont].replaceAll("\"", "");
                    cell.setCellValue((String) aux);
                } else {
                    aux = campos[cont].replaceAll("\"", "");
                    cell.setCellValue((aux.split(";").length == 1 && aux.equals("") ? 0 : (Integer) aux.split(";").length)); //Questões
                }
                cont++;
                cellnum++;
            }
            linha = lerArq.readLine();
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("Deuses.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
