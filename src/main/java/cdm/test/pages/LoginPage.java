package cdm.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class LoginPage extends BasePage {
	
	protected static java.util.logging.Logger log = java.util.logging.Logger.getLogger("EvidenciasLogger");

    public LoginPage(WebDriver driver){
        super(driver);
    }

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
}
