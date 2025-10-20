package br.com.iidh.psmng.util;

import br.com.iidh.psmng.control.business.PacienteBusiness;
import br.com.iidh.psmng.model.entities.TbFone;
import br.com.iidh.psmng.model.entities.TbPaciente;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tassinari
 * 
 * http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
 * 
 */
public class ReadExcel {

    ApplicationContext context;
    PacienteBusiness pacBus;

    public static void main(String args[]){
        try {
            //new ReadExcel().lerPlanilhaPacientes();
            new ReadExcel().carregarDadosPacientes();
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ReadExcel.class.getName()).error(ex);
        }
    }
    
    public void lerPlanilhaPacientes() {

        try {
            FileInputStream file = new FileInputStream(new File("Pacientes.xlsx"));

            //Get the workbook instance for XLS file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                    }
                }
                System.out.println("");
                break;
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void carregarDadosPacientes() throws Exception{
        
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        pacBus = context.getBean(PacienteBusiness.class);
        
        int cabec = 0;
//        int contCell = 0;
        FileInputStream file = new FileInputStream(new File("Pacientes.xlsx"));

//        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Padroes.config.getString("persistencia"));
//        EntityManager manager = factory.createEntityManager();

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(cabec >= 1){ //pula a primeira linha pois eh o cabecalho
                TbPaciente ph = new TbPaciente();
                Collection<TbFone> foneCollection = new ArrayList<TbFone>();
                Iterator<Cell> cellIterator = row.cellIterator();
                if(cellIterator.hasNext()) {
//                    manager.getTransaction().begin(); 
                    Cell cell = cellIterator.next();
                    ph.setDsNome(cell.getStringCellValue());
                    System.out.println(">>>" + ph.getDsNome());
                    cell = cellIterator.next();
                    tratarFone(cell.getStringCellValue(),'C',foneCollection,ph);
                    cell = cellIterator.next();
                    tratarFone(cell.getStringCellValue(),'R',foneCollection,ph);
                    cell = cellIterator.next();
                    ph.setDsEmail(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsProfissao(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsEscolaridade(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setStEstadocivil(validarEstadoCivil(cell.getStringCellValue()));
                    cell = cellIterator.next();
                    ph.setDtNascimento(cell.getDateCellValue());
                    cell = cellIterator.next();
                    ph.setDsFilhos(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsEndereco(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsBairro(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setNrCep(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsQueixas(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsProbsaude(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsAcompmedico(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsRemedios(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setStBebe((cell.getStringCellValue().equals("Sim")?'S':'N'));
                    cell = cellIterator.next();
                    ph.setStFuma((cell.getStringCellValue().equals("Sim")?'S':'N'));
                    cell = cellIterator.next();
                    ph.setStDrogas((cell.getStringCellValue().equals("Sim")?'S':'N'));
                    cell = cellIterator.next();
                    ph.setStInsonia((cell.getStringCellValue().equals("Sim")?'S':'N'));
                    cell = cellIterator.next();
                    ph.setDsCalmante(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setStTratpsic((cell.getStringCellValue().equals("Sim")?'S':'N'));
                    cell = cellIterator.next();
                    ph.setDsResultado(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsObservacao(cell.getStringCellValue());
                    cell = cellIterator.next();
                    ph.setDsFicha(cell.getStringCellValue());
                    
                    ph.setTbFoneCollection(foneCollection);
                    
                    pacBus.persistirPaciente(ph);
//                    manager.persist(ph);
//                    manager.getTransaction().commit();  
                }
            }
            else
                cabec++;
        }
        file.close();
        System.out.println("Finito!!!!");
    }

    private char validarEstadoCivil(String str){
        if(str.equals("Solteiro"))
            return 'S';
        else if(str.equals("Casado"))
            return 'C';
        else if(str.equals("Separado"))
            return 'E';
        else if(str.equals("Viuvo"))
            return 'V';
        else if(str.equals("Divorciado"))
            return 'D';
        else
            return ' ';
    }

    /**
     * Caso a celula contenha mais de um numero, esta celula será montada em uma colecao de fones
     * @param stringCellValue
     * @param tpFone R = resicencial, C = celular, W = Trabalho
     * @param foneCollection 
     */
    private void tratarFone(String stringCellValue, Character tpFone, Collection<TbFone> foneCollection, TbPaciente ph) {
        
        String[] fones = stringCellValue.split(",");
        for(String nrFone:fones){
            if(!nrFone.trim().equals("")){
                TbFone fone = new TbFone(nrFone,tpFone,ph); 
                foneCollection.add(fone);
            }
        }
        
    }
}

