package cdm.test.pages.partesAccidentes.vt;

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
	
	public void rellenarYEnviar(String modo) {
		// 1. Esperar a que el desplegable sea visible
		WebElement selectTipoConductor = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("tipoConductor"))
		);

		// 2. Instanciar Select y seleccionar por el valor "H"
		Select comboConductor = new Select(selectTipoConductor);
		comboConductor.selectByValue("H");
		//--------------------------------------------------
		WebElement botonAceptarConductor = null;
		if (modo.equalsIgnoreCase("VT")) {
			botonAceptarConductor = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoConductor')]"))
			);
		} else if (modo.equalsIgnoreCase("VI")) {
			botonAceptarConductor = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[3]/div/div[2]/input"))
			);
		}		
			
		guardarCaptura("Tipo conductor");
		botonAceptarConductor.click();
		
	}

}
