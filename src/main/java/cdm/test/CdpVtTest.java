package cdm.test;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cdm.test.comun.BaseTest;
import cdm.test.pages.personales.AccidentePage;
import cdm.test.pages.personales.ConductorPage;
import cdm.test.pages.personales.DanosPage;
import cdm.test.pages.personales.FinalPage;
import cdm.test.pages.personales.InicioPage;
import cdm.test.pages.personales.PreguntasInicialesPage;
import cdm.test.pages.personales.TomadorPage;
import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class CdpVtTest extends BaseTest{
	
	// Si existen variables de sistema se usan, si no, toma los valores por defecto
    private String user = System.getProperty("test.user", "PIC2511");
    private String pass = System.getProperty("test.pass", "PPIC2511");
	private String plate = "7154DDD";
	
	@BeforeEach
	public void iniciarSesion() throws IOException {
		login();
	}

	@Test()
	@Story("Alta de Parte de Accidente vía telefónica")
    @DisplayName("CP01 - Verificar el Alta de Parte de Accidente vía telefónica")
    @Description("Prueba end-to-end que valida el flujo completo de Alta de Parte de Accidente vía telefónica con captura de evidencias.")
	public void altaParteAccidenteVT() {
		
		PreguntasInicialesPage preguntasIniciales = new PreguntasInicialesPage(driver);
		preguntasIniciales.rellenarFormularioYEnviar(plate);
		
		TomadorPage tomador = new TomadorPage(driver);
		tomador.rellenarYEnviar();
		
		ConductorPage conductor = new ConductorPage(driver);
		conductor.rellenarYEnviar();
		
		AccidentePage accidente = new AccidentePage(driver);
		accidente.rellenarYEnviar();
		
		DanosPage danos = new DanosPage(driver);
		danos.rellenarYEnviar();
		
		FinalPage finalPage = new FinalPage(driver);
		finalPage.comprobacion();		
		
	}
	
	private void login() throws IOException {
        InicioPage inicioPage = new InicioPage(driver);        
        
        String url = "https://apacheppro.intranet.consorseguros.es/ComunicacionPartesAccidenteVTTest/logon.do";
        inicioPage.navegateTo(url);               
        
        inicioPage.login(user, pass);
    }
		
}
