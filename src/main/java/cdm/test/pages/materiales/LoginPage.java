package cdm.test.pages.materiales;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class LoginPage extends BasePage {
	
	protected static java.util.logging.Logger log = java.util.logging.Logger.getLogger("EvidenciasLogger");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    //Comunicacion de daño via telefonica
    public void rellenarYEnviarFormulario(String user, String pass) {

        WebElement userCampo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("usuario"))
        );
        WebElement passCampo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("password"))
        );       
        
        userCampo.sendKeys(user);
        passCampo.sendKeys(pass);

        driver.findElement(By.xpath("//img[@src='images/X099M_entrar.gif']")).click();
        log.info("Login realizado correctamente");
    }
    
    //Comunicacion de daño via internet
    public void botonEntrar() {
    	
    	// Crear la instancia de espera (tiempo máximo: 10 segundos)
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	// Esperar a que el botón esté visible e interactuable
    	WebElement botonEntrar = wait.until(
    	    ExpectedConditions.elementToBeClickable(By.cssSelector("input.frenteBoton[value='Entrar']"))
    	);

    	// Realizar el clic
    	botonEntrar.click();
    }
    
}
