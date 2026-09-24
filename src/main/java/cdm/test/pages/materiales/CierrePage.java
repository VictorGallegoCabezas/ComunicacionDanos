package cdm.test.pages.materiales;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;


public class CierrePage extends BasePage {

	public CierrePage(WebDriver driver) {
		super(driver);		
	}
	
	public void comprobarPagina() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			
			By textoCierre = By.xpath("//*[contains(normalize-space(.), 'CIERRE')]");			
			
			WebElement cierre = wait.until(
        		    ExpectedConditions.presenceOfElementLocated(textoCierre));
			
			assertTrue(
				cierre.isDisplayed(),
				"No se encontró el texto 'CIERRE DE LA LLAMADA' en la página"
			);
			
			// Captura del estado correcto de la pantalla si carga con éxito
			guardarCaptura("Pantalla Cierre de Llamada Ok");

		} catch (Exception e) {
			// 1. Guardar la evidencia inmediata (en Allure y en carpeta local si esta configurada)
			guardarCaptura("ERROR - Fallo al cargar Cierre de Llamada");
			
			// 2. Hacer fallar la prueba pasando la excepción original
			fail("Error controlado: La aplicación no cargó la pantalla 'CIERRE DE LA LLAMADA' dentro del tiempo límite. Posible fallo en la app o en la navegación.", e);
		}
	}
	
	public void descargarPdf() {
		By btnDescargarPdf = By.name("pdf");

		// Esperar a que sea cliqueable y hacer clic
		WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnDescargarPdf));
		boton.click();
	}
	
	public void comprobarDescargaPdf() {
	    // 1. Recuperar el nombre
	    String nombreFichero = obtenerNombrePdf();

	    // 2. Comprobación directa (ej. esperando hasta 15 segundos)
	    boolean descargado = this.comprobarArchivoDescargado(nombreFichero, 15);

	    // 3. Afirmación de JUnit 5
	    Assertions.assertTrue(descargado, "El archivo 'informe_mensual.pdf' no se ha descargado correctamente.");
	}

	private String obtenerNombrePdf() {
	    // Localizador por clase
	    By locator = By.cssSelector("label.navegacionRojo");
	    
	    // Obtener el elemento y su texto
	    WebElement elemento = driver.findElement(locator);
	    String textoBruto = elemento.getText(); 
	    
	    // 1. Quitar espacios normales y el espacio de no separación HTML (&nbsp; o \u00a0 en Java)
	    String textoLimpio = textoBruto.replace("\u00a0", "").replace("&nbsp;", "").trim();
	    
	    // 2. Quitar las barras '/'
	    textoLimpio = textoLimpio.replace("/", "");
	    
	    // 3. Formatear y devolver
	    String nombreArchivo = "Solicitud_" + textoLimpio + ".pdf";
	    
	    log.info("Número de solicitud procesado: " + textoLimpio);
	    log.info("Nombre de archivo esperado: " + nombreArchivo);
	    
	    return nombreArchivo;
	}
}