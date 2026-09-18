package cdm.test.pages.personales;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class DanosPage extends BasePage {

	public DanosPage(WebDriver driver) {
		super(driver);
	}

	public void rellenarYEnviar() {
		// Localizamos el elemento
		List<WebElement> botonesEliminar = driver.findElements(
		    By.xpath("//img[contains(@onclick, 'eliminarBien') and contains(@src, 'X099M_borrarFichero.gif')]")
		);

		if (botonesEliminar.isEmpty()) {
		    // ---------------- SI NO EXISTE EL ELEMENTO ----------------
			//-------------------------------- 1. Intentar hacer clic en Añadir Bien
			WebElement enlaceAnadirBien = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'abrirNuevoBien')]"))
			);
			enlaceAnadirBien.click();
			//-------------------------------- 2. Rellenar formulario solo si se permitió abrir			
			WebElement descripcionBienCampo = wait.until(
			    ExpectedConditions.visibilityOfElementLocated(By.name("descripcionBien"))
			);
			descripcionBienCampo.sendKeys("He roto una farola en la calle");

			WebElement botonAceptarBien = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoBien')]"))
			);
			botonAceptarBien.click();
		}
		//-------------------------------- Botón Aceptar Daños (Siempre se ejecuta)
		WebElement botonAceptarDanos = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoDanos')]"))
		);
		guardarCaptura("Datos daño");
		
		botonAceptarDanos.click();

		//-------------------------------- 5. Aceptar Alerta Nativa final de confirmación
		
		wait.until(ExpectedConditions.alertIsPresent());		
		Alert alerta = driver.switchTo().alert();
		alerta.accept();
		
	}

}