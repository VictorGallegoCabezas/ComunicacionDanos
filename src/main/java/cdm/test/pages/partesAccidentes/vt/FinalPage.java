package cdm.test.pages.partesAccidentes.vt;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class FinalPage extends BasePage {

	public FinalPage(WebDriver driver) {
		super(driver);
	}
	
	public void comprobacionCierreLlamada() {
		
		try {
			WebElement confirmacionCierre = wait.until(
			    ExpectedConditions.visibilityOfElementLocated(
			        By.xpath("//*[contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyzáéíóú', 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ'), 'CIERRE DE LA LLAMADA')]")
			    )
			);
		    guardarCaptura("Alta OK");
		    Assertions.assertTrue(confirmacionCierre.isDisplayed(), "El texto 'CIERRE DE LA LLAMADA' no está visible.");
		} catch (org.openqa.selenium.TimeoutException e) {
		    Assertions.fail("Prueba fallida: No se encontró el texto 'CIERRE DE LA LLAMADA' en la página.");
		}	
		
	}
	
	public void comprobarResumen() {
		
		try {
		    boolean confirmacionResumen = wait.until(ExpectedConditions.textToBePresentInElementLocated(
				    By.cssSelector("div.encabezado1D"), 
				    "RESUMEN"
				));
		    guardarCaptura("Alta OK");
		    Assertions.assertTrue(confirmacionResumen, "El texto 'RESUMEN' no está visible.");
		} catch (org.openqa.selenium.TimeoutException e) {
		    Assertions.fail("Prueba fallida: No se encontró el texto 'RESUMEN' en la página.");
		}	
		
		wait.until(ExpectedConditions.elementToBeClickable(
			    By.name("aceptar"))).click();		
		
		//-------------------------------- 5. Aceptar Alerta Nativa final de confirmación		
		wait.until(ExpectedConditions.alertIsPresent());		
		Alert alerta = driver.switchTo().alert();
		alerta.accept();
		
	}

}
