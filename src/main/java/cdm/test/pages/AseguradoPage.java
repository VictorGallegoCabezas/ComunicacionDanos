
package cdm.test.pages;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;


public class AseguradoPage extends BasePage{
	
	protected static java.util.logging.Logger log = java.util.logging.Logger.getLogger("EvidenciasLogger");

	public AseguradoPage(WebDriver driver) {
		super(driver);
	}
	
	public void comprobarPagina() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//div[@class='tr12b' and contains(.,'¿Me puede indicar su NIF')]")
		));

		assertTrue(elemento.isDisplayed(), "No se encontró la pregunta de NIF/CIF en la página");
	}
	
	public void rellenarYEnviarFormulario() {
	    // Rellenar DNI
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("documentoInterlocutor")))
	        .sendKeys("77777777B");
	    
	    // Pulsa interrogante (es recomendable esperarlo también antes del click)
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='data']/div[3]/div[6]/img"))).click();
	    
	    // Esperar a que la fila esté presente y clickeable antes de pulsar
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@id='0']"))).click();
	    
	    guardarCaptura("Página asegurado");
	    // Aceptar
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@src,'X099M_aceptar.gif')]"))).click();         
	}

}
