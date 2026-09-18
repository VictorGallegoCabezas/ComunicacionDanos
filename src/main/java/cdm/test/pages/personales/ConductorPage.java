package cdm.test.pages.personales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import cdm.test.comun.BasePage;

public class ConductorPage extends BasePage {

	public ConductorPage(WebDriver driver) {
		super(driver);
	}
	
	public void rellenarYEnviar() {
		// 1. Esperar a que el desplegable sea visible
		WebElement selectTipoConductor = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("tipoConductor"))
		);

		// 2. Instanciar Select y seleccionar por el valor "H"
		Select comboConductor = new Select(selectTipoConductor);
		comboConductor.selectByValue("H");
		//--------------------------------------------------
		WebElement botonAceptarConductor = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoConductor')]"))
		);
		botonAceptarConductor.click();
		
	}

}
