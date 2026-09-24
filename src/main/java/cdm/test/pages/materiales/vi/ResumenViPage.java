package cdm.test.pages.materiales.vi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class ResumenViPage extends BasePage{

	public ResumenViPage(WebDriver driver) {
		super(driver);
	}
	
	public void comprobarPagina() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			By textoPago = By.xpath("//*[contains(normalize-space(.), 'RESUMEN')]");

        	// Recuperar el elemento presente en el DOM
			WebElement cierre = wait.until(
        		    ExpectedConditions.presenceOfElementLocated(textoPago));
			
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
	
	public void aceptar() {
		//Boton aceptar
		By btnAceptarResumen = By.xpath("//input[@value='Aceptar' and contains(@onclick, 'aceptarResumen')]");
		WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnAceptarResumen));
		boton.click();
		//------------------ 5. Aceptar Alerta Nativa final de confirmación
		wait.until(ExpectedConditions.alertIsPresent());		
		Alert alerta = driver.switchTo().alert();
		alerta.accept();
}

}
