package cdm.test.pages.personales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import cdm.test.comun.BasePage;

public class TomadorPage extends BasePage{

	public TomadorPage(WebDriver driver) {
		super(driver);
	}
	
	public void rellenarYEnviar() {
		// 1. Esperar a que el desplegable sea visible
		WebElement selectTipoPersona = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("tipoPersona"))
		);

		// 2. Instanciar Select y seleccionar por el valor "T"
		Select comboPersona = new Select(selectTipoPersona);
		comboPersona.selectByValue("T");
		//-------------------------------------------------------
		WebElement botonAceptarTomador = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoTomador')]"))
		);
		guardarCaptura("Seleccion tipo persona");
		botonAceptarTomador.click();
	}

}
