package cdm.test.pages.materiales;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class PagosPage extends BasePage {
	
	protected static java.util.logging.Logger log = java.util.logging.Logger.getLogger("EvidenciasLogger");

    public PagosPage(WebDriver driver){
        super(driver);
    }
    
    public void comprobarPaginaVi() {
        // 1. Comprobar si aparece el modal/botón de aceptoDuplicado
        try {
            WebDriverWait waitDuplicado = new WebDriverWait(driver, Duration.ofSeconds(3));
            By btnDuplicadoLocator = By.xpath("//img[contains(@src,'X099M_continuar.gif') or contains(@onclick,'aceptoDuplicado')]");
            
            WebElement btnDuplicado = waitDuplicado.until(
                ExpectedConditions.presenceOfElementLocated(btnDuplicadoLocator)
            );

            guardarCaptura("Aviso de duplicado detectado");

            // Clic por JS para evitar 'ElementClickInterceptedException'
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", btnDuplicado);

        } catch (org.openqa.selenium.TimeoutException e) {
            log.info("No apareció la alerta de duplicado, continuando con la validación de pagos.");
        }

        // 2. Flujo principal: Validar la pantalla DATOS DEL PAGO
        try {
        	By textoPago = By.xpath("//*[contains(normalize-space(.), 'DATOS DEL PAGO')]");

        	// Recuperar el elemento presente en el DOM
        	WebElement elementoTexto = wait.until(
        		    ExpectedConditions.presenceOfElementLocated(textoPago));

            assertTrue(
            		elementoTexto.isDisplayed(),
                "No se encontró la sección 'DATOS DEL PAGO' en la página"
            );

            guardarCaptura("Pantalla Datos del Pago Ok");

        } catch (Exception e) {
            guardarCaptura("ERROR - Fallo al cargar Datos del Pago");
            fail("Error controlado: La aplicación no cargó la pantalla 'DATOS DEL PAGO' dentro del tiempo límite.", e);
        }
    }

    public void rellenarYEnviarFormulario(String modo) {
    	
    	driver.findElement(By.name("ibanControl")).sendKeys("ES72");
    	driver.findElement(By.name("entidad")).sendKeys("0081");
    	driver.findElement(By.name("sucursal")).sendKeys("0052");
    	driver.findElement(By.name("cuenta1")).sendKeys("0000");
    	driver.findElement(By.name("cuenta2")).sendKeys("0440");
    	driver.findElement(By.name("cuenta3")).sendKeys("0044");
    	
    	guardarCaptura("Página pagos");
    	
    	if (modo.equalsIgnoreCase("VI")) {
    		By btnAceptarPago = By.xpath("//input[@value='Aceptar' and contains(@onclick, 'validoPago')]");
    		WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnAceptarPago));
    		boton.click();
    	} else {
	    	WebElement boton = wait.until(ExpectedConditions.presenceOfElementLocated(
			    By.xpath("//img[contains(@onclick, 'validoPago')]")
			));
	
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", boton);
    	}

        
    }
    
    public void gestionarDuplicadoSiExiste() {
        // 1. Buscamos si el texto está presente en el DOM
        By textoDuplicado = By.xpath("//*[contains(text(), 'Tenemos constancia de una comunicación de daños similar')]");
        List<WebElement> coincidencias = driver.findElements(textoDuplicado);

        // 2. Si encontramos el mensaje, hacemos clic en el botón 'continuar'
        if (!coincidencias.isEmpty() && coincidencias.get(0).isDisplayed()) {
            log.info("Se detectó aviso de comunicación duplicada. Continuando...");
            
            By botonContinuar = By.xpath("//img[contains(@onclick, 'aceptoDuplicado') or contains(@src, 'X099M_continuar.gif')]");
            wait.until(ExpectedConditions.elementToBeClickable(botonContinuar)).click();
        }
    }
    
    
}
