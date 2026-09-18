package cdm.test.pages.materiales;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;


public class CierrePage extends BasePage {

	public CierrePage(WebDriver driver) {
		super(driver);		
	}
	
	public void comprobarPagina() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement cierre = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='tr12b'][contains(normalize-space(.),'CIERRE DE LA LLAMADA')]")
				)
			);

			assertTrue(
				cierre.isDisplayed(),
				"No se encontró el texto 'CIERRE DE LA LLAMADA' en la página"
			);

			// Captura del estado correcto de la pantalla si carga con éxito
			guardarCaptura("Pantalla Cierre de Llamada Ok");

		} catch (Exception e) {
			// 1. Guardar la evidencia inmediata (en Allure y en carpeta local si esta configurada)
			guardarCaptura("ERROR - Fallo al cargar Cierre de Llamada");
			
			// 2. Hacer fallar la prueba pasando la excepción original
			fail("Error controlado: La aplicación no cargó la pantalla 'CIERRE DE LA LLAMADA' dentro del tiempo límite. Posible fallo en la app o en la navegación.", e);
		}
	}
}