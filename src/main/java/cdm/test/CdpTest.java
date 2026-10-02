package cdm.test;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cdm.test.comun.BaseTest;
import cdm.test.pages.materiales.CierrePage;
import cdm.test.pages.partesAccidentes.vt.AccidentePage;
import cdm.test.pages.partesAccidentes.vt.ConductorPage;
import cdm.test.pages.partesAccidentes.vt.DanosPage;
import cdm.test.pages.partesAccidentes.vt.FinalPage;
import cdm.test.pages.partesAccidentes.vt.InicioPage;
import cdm.test.pages.partesAccidentes.vt.PreguntasInicialesPage;
import cdm.test.pages.partesAccidentes.vt.TomadorPage;
import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class CdpTest extends BaseTest{
	
	// Si existen variables de sistema se usan, si no, toma los valores por defecto
    private String user = System.getProperty("test.user", "PIC2511");
    private String pass = System.getProperty("test.pass", "NPIC2511");
	private String plateVt = "7154DDD";
	private String plateVi = "R4891BBC";
	private String urlCDPVI = "https://consorcio:CcsCast3llaNa!@preapps.consorseguros.es/ComunicacionPartesAccidenteVITest";
	
	@BeforeEach
	public void iniciarSesion() throws IOException {
		
	}

	@Test()
	@Story("Alta de Parte de Accidente vía telefónica")
    @DisplayName("CP01 - Verificar el Alta de Parte de Accidente vía telefónica")
    @Description("Prueba end-to-end que valida el flujo completo de Alta de Parte de Accidente vía telefónica con captura de evidencias.")
	public void altaParteAccidenteVT() throws IOException, InterruptedException {
		
		String modo = "vt";
		loginVt();
		
		PreguntasInicialesPage preguntasIniciales = new PreguntasInicialesPage(driver);
		preguntasIniciales.rellenarFormularioYEnviar(plateVt, modo);
		
		TomadorPage tomador = new TomadorPage(driver);
		tomador.rellenarYEnviar(modo);
		
		ConductorPage conductor = new ConductorPage(driver);
		conductor.rellenarYEnviar(modo);
		
		AccidentePage accidente = new AccidentePage(driver);
		accidente.rellenarYEnviar(modo);
		
		DanosPage danos = new DanosPage(driver);
		danos.rellenarYEnviar(modo);
		
		FinalPage finalPage = new FinalPage(driver);
		finalPage.comprobacionCierreLlamada();		
		
	}
	
	@Test()
	@Story("Alta de Parte de Accidente vía internet")
    @DisplayName("CP01 - Verificar el Alta de Parte de Accidente vía internet")
    @Description("Prueba end-to-end que valida el flujo completo de Alta de Parte de Accidente vía internet con captura de evidencias.")
	public void altaParteAccidenteVI() throws InterruptedException {
		
		String modo = "vi";
		loginCertificado(urlCDPVI);
		PreguntasInicialesPage preguntasIniciales = new PreguntasInicialesPage(driver);
		preguntasIniciales.rellenarFormularioYEnviar(plateVi, modo);
		
		TomadorPage tomador = new TomadorPage(driver);
		tomador.rellenarYEnviar(modo);
		
		ConductorPage conductor = new ConductorPage(driver);
		conductor.rellenarYEnviar(modo);
		
		AccidentePage accidente = new AccidentePage(driver);
		accidente.rellenarYEnviar(modo);
		
		DanosPage danos = new DanosPage(driver);
		danos.rellenarYEnviar(modo);
		
		FinalPage finalPage = new FinalPage(driver);
		finalPage.comprobarResumen();
		finalPage.comprobacionCierreLlamada();
		
		CierrePage cierre = new CierrePage(driver);
		cierre.descargarPdf();
		cierre.comprobarDescargaPdf("cdp");
		
		System.out.println("Finalizado OK");	
		
	}	
	
	private void loginVt() throws IOException {
        InicioPage inicioPage = new InicioPage(driver);
        
        String url = "https://apacheppro.intranet.consorseguros.es/ComunicacionPartesAccidenteVTTest/logon.do";
        inicioPage.navegateTo(url);               
        
        inicioPage.loginVt(user, pass);
    }
		
}
