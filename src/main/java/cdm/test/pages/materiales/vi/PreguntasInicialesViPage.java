package cdm.test.pages.materiales.vi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class PreguntasInicialesViPage extends BasePage{

	public PreguntasInicialesViPage(WebDriver driver) {
		super(driver);
	}

	public void radiosYAceptar() {
		
		// Definir el localizador por el atributo name
		By chkAsegurado = By.cssSelector("input[type='checkbox'][name='asegurado']");

		// Esperar a que sea clickable y hacer clic
		WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(chkAsegurado));
		checkbox.click();
		
		//------Boton aceptar
		// Localizador con XPath absoluto
		By elementoXPath = By.xpath("/html/body/form/div[3]/div/div/input");

		// Esperar a que sea clickable y hacer clic
		WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(elementoXPath));
		elemento.click();
		
		
		
	}	
	
}
