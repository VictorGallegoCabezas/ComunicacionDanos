package cdm.test.pages.partesAccidentes.vt;

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
	
	public void rellenarYEnviar(String modo) {
		// 1. Esperar a que el desplegable sea visible
		WebElement selectTipoPersona = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("tipoPersona"))
		);

		// 2. Instanciar Select y seleccionar por el valor "T"
		Select comboPersona = new Select(selectTipoPersona);
		comboPersona.selectByValue("T");
		//-------------------------------------------------------
		WebElement botonAceptarTomador = null;
		if (modo.equalsIgnoreCase("VT")) {
			botonAceptarTomador = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoTomador')]"))
			);
		} else if (modo.equalsIgnoreCase("VI")) {
			botonAceptarTomador = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[3]/div/div[2]/input"))
			);
		}
		guardarCaptura("Seleccion tipo persona");
		botonAceptarTomador.click();
	}

}
