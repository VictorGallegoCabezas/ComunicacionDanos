package cdm.test.pages.personales;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class FinalPage extends BasePage {

	public FinalPage(WebDriver driver) {
		super(driver);
	}
	
	public void comprobacion() {
		
		try {
		    WebElement confirmacionCierre = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//div[contains(@class, 'tr12b') and contains(normalize-space(), 'CIERRE DE LA LLAMADA')]")
		        )
		    );
		    guardarCaptura("Alta OK");
		    Assertions.assertTrue(confirmacionCierre.isDisplayed(), "El texto 'CIERRE DE LA LLAMADA' no está visible.");
		} catch (org.openqa.selenium.TimeoutException e) {
		    Assertions.fail("Prueba fallida: No se encontró el texto 'CIERRE DE LA LLAMADA' en la página.");
		}
		
		
		
		
	}

}
