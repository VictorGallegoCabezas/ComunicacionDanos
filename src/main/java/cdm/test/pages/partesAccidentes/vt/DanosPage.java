package cdm.test.pages.partesAccidentes.vt;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class DanosPage extends BasePage {

	public DanosPage(WebDriver driver) {
		super(driver);
	}

	public void rellenarYEnviar(String modo) {
		// Localizamos el elemento
		boolean existeElemento = false;

	    try {
	        WebElement confirmacionResumen = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//img[contains(@onclick, 'eliminarBien') and contains(@src, 'X099M_borrarFichero.gif')]")
	            )
	        );
	        existeElemento = confirmacionResumen.isDisplayed();
	    } catch (Exception e) {
	        // No se encontró dentro del tiempo límite del wait, continua el flujo normalmente
	        existeElemento = false;
	    }

		if (!existeElemento) {
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

			if (modo.equalsIgnoreCase("VT")) {
				wait.until( 
				    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoBien')]"))
				).click();
				
			} else if (modo.equalsIgnoreCase("VI")) {
				wait.until( 
					    ExpectedConditions.elementToBeClickable(By.name("victima2"))
					).click();
			}
		}
		//-------------------------------- Botón Aceptar Daños (Siempre se ejecuta)
		WebElement botonAceptarDanos = null;
		if (modo.equalsIgnoreCase("VT")) {
			botonAceptarDanos = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoDanos')]"))
			);
			guardarCaptura("Datos daño");		
			botonAceptarDanos.click();
			
			//-------------------------------- 5. Aceptar Alerta Nativa final de confirmación		
			wait.until(ExpectedConditions.alertIsPresent());		
			Alert alerta = driver.switchTo().alert();
			alerta.accept();
			
			
		} else if (modo.equalsIgnoreCase("VI")) {
			botonAceptarDanos = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[3]/div/div[2]/input"))
			);
			guardarCaptura("Datos daño");		
			botonAceptarDanos.click();
		}		
		
	}

}