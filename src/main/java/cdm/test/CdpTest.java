package cdm.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

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
    private String pass = System.getProperty("test.pass", "PPIC2511");
	private String plateVt = "7154DDD";
	private String plateVi = "R4891BBC";
	
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
		loginVi();
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
	
	private void loginVi() throws InterruptedException {		
				 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		InicioPage inicioPage = new InicioPage(driver);
		String url = "https://consorcio:CcsCast3llaNa!@preapps.consorseguros.es/ComunicacionPartesAccidenteVITest";
        inicioPage.navegateTo(url);
        
        wait
        .until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.frenteBoton[value='Entrar']")))
        .click();
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("linkJNLP_download")))
        .click();
        
        // 1. Espera de descarga
        Thread.sleep(5000); 
        
        // 2. Comprobar que existe el archivo .jnlp
        File carpetaDescargas = new File(downloadPath);
        File[] archivos = carpetaDescargas.listFiles((dir, name) -> name.endsWith(".jnlp"));
        
        if (archivos == null || archivos.length == 0) {
            log.severe("Fichero .jnlp necesario para el login no se ha descargado");
            fail("Fichero .jnlp necesario para el login no se ha descargado");
        }
        
        File archivoJnlp = archivos[0];
        log.info("Fichero JNLP localizado en: " + archivoJnlp.getAbsolutePath());

        // 3. Abrir el archivo .jnlp simulando un doble clic del sistema operativo
        try {
            log.info("Abriendo el archivo JNLP mediante doble clic del sistema operativo...");
            
            // Comprobar si la plataforma actual soporta el módulo Desktop
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                if (archivoJnlp.exists()) {
                    desktop.open(archivoJnlp); // Equivale a doble clic en Windows
                    log.info("Comando de apertura mediante doble clic enviado con éxito.");
                }
            } else {
                log.severe("java.awt.Desktop no está soportado en este entorno.");
                fail("El entorno no soporta la acción de doble clic automático sobre archivos.");
            }
        } catch (Exception e) {
            log.severe("Error al intentar abrir el archivo JNLP: " + e.getMessage());
            fail("No se pudo abrir el archivo JNLP mediante doble clic. Error: " + e.getMessage());
        }

        // 4. Controlar la ventana Swing/AWT con SikuliX
        Screen screen = new Screen();
        
        try {            
        	Thread.sleep(25000); 
            // Seleccionar certificado
        	// 1. Presionar ESPACIO o ENTER para seleccionar la fila por defecto / o FLECHA ABAJO + ESPACIO
        	screen.type(Key.TAB);
        	screen.type(Key.TAB);
        	screen.type(Key.TAB);
            screen.type(org.sikuli.script.Key.SPACE);       	           	
            
            //Boton enviar
           	Region ventanaViafirma = App.focusedWindow();

           	if (ventanaViafirma != null) {
           	    // La esquina superior izquierda de la ventana es (ventanaViafirma.x, ventanaViafirma.y)
           	    
           	    // Ejemplo: Si el certificado está a 700px a la derecha y 420px hacia abajo de la ventana
           	    int certX = ventanaViafirma.x + 700;
           	    int certY = ventanaViafirma.y + 420;
           	    
	           	screen.click(new org.sikuli.script.Location(certX, certY));
	            log.info("Clic realizado en el certificado dentro de la ventana.");
	            Thread.sleep(1000);
	            
	            screen.type(org.sikuli.script.Key.ENTER);
	            log.info("Certificado aceptado");
	            Thread.sleep(1000);
	            screen.type(org.sikuli.script.Key.ENTER);
	        }
            
        } catch (FindFailed e) {
            log.info("SikuliX no pudo encontrar el elemento visual en pantalla: " + e.getMessage());
            fail("SikuliX no encontró el elemento en la interfaz Java.");
        }       
        
        
	}
	
	private void loginVt() throws IOException {
        InicioPage inicioPage = new InicioPage(driver);
        
        String url = "https://apacheppro.intranet.consorseguros.es/ComunicacionPartesAccidenteVTTest/logon.do";
        inicioPage.navegateTo(url);               
        
        inicioPage.loginVt(user, pass);
    }
		
}
