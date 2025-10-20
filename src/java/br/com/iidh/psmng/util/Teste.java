package br.com.iidh.psmng.util;

import br.com.iidh.psmng.model.dao.TbPacienteDAO;
import br.com.iidh.psmng.model.entities.TbPaciente;
import br.com.iidh.psmng.view.handler.AbstractHandlerFacade;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Teste extends AbstractHandlerFacade {

    private TbPacienteDAO pacienteDAO = new TbPacienteDAO();

    public static void main(String[] args) throws ParseException {
        new Teste();
    }

    public Teste() throws ParseException {
        super(Teste.class);
//        inserir();
//        atualizar(3);
//        testeData();
//        criptografarSenha();
        System.exit(0);
    }

    private void criptografarSenha() {

        String senha = "ju449";
        String wildfly = "";
        String glassfish = "";

        try {
            glassfish = Base64EncoderPSM.criptografarSenhaGlassFish(senha, "SHA-256");
            wildfly = Base64EncoderPSM.criptografarSenhaWildFly(senha, "SHA-256");
        } catch (Exception ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("GlassFish   = " + glassfish);
        System.out.println("MySql       = gXT/a0vn8OiY62yTzdGq+DZP9aY31mgXopY0v0rWgTU=");
        System.out.println("WildFly     = " + wildfly);
    }

    public void inserir() {
        try {
            TbPaciente paciente = new TbPaciente();
            paciente.setDsNome("Fabiosssssss");
            paciente.setDsEndereco("Endereço");
            paciente.setDsBairro("Bairro");
            paciente.setNrCep("03167030");
            pacienteDAO.create(paciente);
            System.out.println(getLabelMessages("InformacoesGravadasComSucesso"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void atualizar(int id) {
        try {
            TbPaciente paciente = pacienteDAO.find(new Integer(id));

            paciente.setDsCalmante("gagagaggagaa");

            pacienteDAO.edit(paciente);
            System.out.println(getLabelMessages("InformacoesGravadasComSucesso"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void testeData() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String txt = "2016-05-10 07:00:00.0";
        Date dt = formatter.parse(txt);
    }

}
