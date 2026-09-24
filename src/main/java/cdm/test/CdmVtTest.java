package cdm.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import cdm.test.comun.BaseTest;
import cdm.test.pages.materiales.CierrePage;
import cdm.test.pages.materiales.LoginPage;
import cdm.test.pages.materiales.PagosPage;
import cdm.test.pages.materiales.vi.AseguradoPageVi;
import cdm.test.pages.materiales.vi.DanosViPage;
import cdm.test.pages.materiales.vi.DatosDanosViPage;
import cdm.test.pages.materiales.vi.PreguntasInicialesViPage;
import cdm.test.pages.materiales.vi.ProteccionDatosViPage;
import cdm.test.pages.materiales.vi.ResumenViPage;
import cdm.test.pages.materiales.vt.AseguradoPageVt;
import cdm.test.pages.materiales.vt.DanosVtPage;
import cdm.test.pages.materiales.vt.PreguntasInicialesVtPage;

public class CdmVtTest extends BaseTest {

	// Si existen variables de sistema se usan, si no, toma los valores por defecto
    private String user = System.getProperty("test.user", "PIC2511");
    private String pass = System.getProperty("test.pass", "PPIC2511");

    //@Test
    public void comunicacionDanosMaterialesVTTest() throws IOException {

    	// INICIO Y LOGIN
        LoginPage loginPage = new LoginPage(driver);
        String url = "https://apacheppro.intranet.consorseguros.es/ComunicacionDanosMaterialesVTTest";
        loginPage.navegateTo(url);
        log.info("Abriendo " + url);
        
        cambiarAFocoNuevaVentana();

        log.info("User: " + user + " pass: " + pass);
        loginPage.rellenarYEnviarFormulario(user, pass);
        
        // PREGUNTAS INICIALES Y ACEPTAR GRABACION
        PreguntasInicialesVtPage preguntasInicialesPage = new PreguntasInicialesVtPage(driver);
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
        AseguradoPageVt aseguradoPage = new AseguradoPageVt(driver);
        aseguradoPage.comprobarPagina();
        
        aseguradoPage.rellenarYEnviarFormulario();
        
        DanosVtPage danosPage = new DanosVtPage(driver);
        danosPage.comprobarPaginaDanos();
        
        danosPage.rellenarYEnviarFormularioDanos();  
        
        danosPage.comprobarPaginaDanos2();
        
        danosPage.rellenarYEnviarFormularioDanos2();
        
        PagosPage pago = new PagosPage(driver);
        pago.gestionarDuplicadoSiExiste();
        pago.comprobarPaginaVi();
        
        pago.rellenarYEnviarFormulario("VT");
        
        CierrePage cierre = new CierrePage(driver);        
        cierre.comprobarPagina();
        cierre.descargarPdf();
        cierre.comprobarDescargaPdf();
        
    }
    
    @Test
    public void comunicacionDanosMaterialesVITest() {
    	LoginPage loginPage = new LoginPage(driver);
        String url = "https://consorcio:CcsCast3llaNa!@preapps.consorseguros.es/ComunicacionDanosMaterialesVITest";
        loginPage.navegateTo(url);
        log.info("Abriendo " + url);
        loginPage.botonEntrar();       
        
        
        // PREGUNTAS INICIALES Y ACEPTAR GRABACION
        PreguntasInicialesViPage preguntasInicialesPage = new PreguntasInicialesViPage(driver);
        preguntasInicialesPage.radiosYAceptar();
        
        ProteccionDatosViPage proteccion = new ProteccionDatosViPage(driver);
        proteccion.radiosYAceptar();

        AseguradoPageVi asegurado = new AseguradoPageVi(driver);
        asegurado.rellenarYEnviarFormulario();
        
        DanosViPage danos = new DanosViPage(driver);
        danos.comprobarPaginaDanos();
        danos.rellenarYEnviar();
        
        DatosDanosViPage datosDanos = new DatosDanosViPage(driver);
        datosDanos.rellenarYAceptar();
        
        PagosPage pago = new PagosPage(driver);
        pago.gestionarDuplicadoSiExiste();
        pago.comprobarPaginaVi();        
        pago.rellenarYEnviarFormulario("VI");
        
        ResumenViPage resumen = new ResumenViPage(driver);
        resumen.comprobarPagina();
        resumen.aceptar();
        
        
        
        CierrePage cierre = new CierrePage(driver);        
        cierre.comprobarPagina();
        cierre.descargarPdf();
        cierre.comprobarDescargaPdf();
    }
}
