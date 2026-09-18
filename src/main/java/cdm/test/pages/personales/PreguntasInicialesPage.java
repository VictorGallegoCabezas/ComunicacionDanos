package cdm.test.pages.personales;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import cdm.test.comun.BasePage;

public class PreguntasInicialesPage extends BasePage {

	public PreguntasInicialesPage(WebDriver driver) {
		super(driver);
	}
	
	public void rellenarFormularioYEnviar(String plate) {
		
		WebElement matriculaCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("matricula"))
		);		
		matriculaCampo.sendKeys(plate);
		//-----------------------------
		WebElement fechaAccidenteCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("fechaAccidente"))
		);		
		fechaAccidenteCampo.sendKeys(calculaFecha());
		//-----------------------------		
		WebElement botonAceptar = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@src, 'X099M_aceptar.gif')]"))
		);
		botonAceptar.click();
		//-----------------------------		
		// 1. Esperar a que el elemento select esté visible
		WebElement selectElement = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("grabacionAceptada"))
		);

		// 2. Crear el objeto Select y seleccionar por valor "SI"
		Select comboGrabacion = new Select(selectElement);
		comboGrabacion.selectByValue("SI");
		//-----------------------------
		
		WebElement botonAceptar2 = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@onclick, 'validoPreguntasIniciales')]"))
		);
		guardarCaptura("Preguntas Iniciales");
		botonAceptar2.click();
		
	}
	
	public String calculaFecha() {
        // Obtiene la fecha de hoy y le resta 1 día
        LocalDate fechaAyer = LocalDate.now().minusDays(1);
        
        // Define el formato dd-MM-yyyy (MM en mayúsculas representa el mes)
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // Devuelve la fecha formateada como String
        return fechaAyer.format(formato);
    }

}
