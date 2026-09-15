package cdm.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cdm.test.comun.BaseTest;
import cdm.test.pages.AseguradoPage;
import cdm.test.pages.CierrePage;
import cdm.test.pages.DanosPage;
import cdm.test.pages.LoginPage;
import cdm.test.pages.PagosPage;
import cdm.test.pages.PreguntasInicialesPage;

public class CdmvtTest extends BaseTest {

	// Si existen variables de sistema se usan, si no, toma los valores por defecto
    private String user = System.getProperty("test.user", "PIC2511");
    private String pass = System.getProperty("test.pass", "PPIC2511");

    @Test
    public void comunicacionDanosVTTest() throws IOException {

    	// INICIO Y LOGIN
        LoginPage loginPage = new LoginPage(driver);
        String url = "https://apacheppro.intranet.consorseguros.es/ComunicacionDanosMaterialesVTTest";
        loginPage.navegateTo(url);
        log.info("Abriendo " + url);
        
        cambiarAFocoNuevaVentana();

        log.info("User: " + user + " pass: " + pass);
        loginPage.rellenarYEnviarFormulario(user, pass);
        
        // PREGUNTAS INICIALES Y ACEPTAR GRABACION
        PreguntasInicialesPage preguntasInicialesPage = new PreguntasInicialesPage(driver);

        // Espera// Esperar a que cargue la página
        preguntasInicialesPage.esperarCargaPreguntasIniciales();

	     // Hacer clic en los radios
        preguntasInicialesPage.clickGroup1Si();
        preguntasInicialesPage.clickGroup0Persona();
        log.info("Preguntas iniciales OK");
	     // Clic en aceptar
        preguntasInicialesPage.clickAceptarLectura();
        // Segundo aceptar (irA('interlocutor'))
        preguntasInicialesPage.aceptarGrabacion();
        log.info("Aceptar grabacion OK");
        
        // ASEGURADO PAGE
        AseguradoPage aseguradoPage = new AseguradoPage(driver);
        aseguradoPage.comprobarPagina();
        
        aseguradoPage.rellenarYEnviarFormulario();
        
        DanosPage danosPage = new DanosPage(driver);
        danosPage.comprobarPaginaDanos();
        
        danosPage.rellenarYEnviarFormularioDanos();  
        
        danosPage.comprobarPaginaDanos2();
        
        danosPage.rellenarYEnviarFormularioDanos2();
        
        PagosPage pago = new PagosPage(driver);
        pago.gestionarDuplicadoSiExiste();
        pago.comprobarPagina();
        
        pago.rellenarYEnviarFormulario();
        
        CierrePage cierre = new CierrePage(driver);
        
        cierre.comprobarPagina();
        
    }
}
