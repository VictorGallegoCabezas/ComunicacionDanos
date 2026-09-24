package cdm.test.pages.materiales.vi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class ProteccionDatosViPage extends BasePage{

	public ProteccionDatosViPage(WebDriver driver) {
		super(driver);
	}
	
	public void radiosYAceptar() {
		
		// Localizador por clase y atributo name
		By chkGrabacion = By.cssSelector("input.radio3[name='grabacion']");

		// Esperar a que sea clickable
		WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(chkGrabacion));

		// Asegurar que se marque (hacer clic solo si no está seleccionado previamente)
		if (!checkbox.isSelected()) {
		    checkbox.click();
		}
		
		//--------------- boton aceptar
		// Localizador XPath
		By campoInput = By.xpath("/html/body/form/div[3]/div/div[2]/input");

		// Esperar a que el elemento sea clickable y hacer clic
		WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(campoInput));
		elemento.click();
	}

}
