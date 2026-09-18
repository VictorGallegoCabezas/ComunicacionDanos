package cdm.test.pages.personales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class InicioPage extends BasePage{

	public InicioPage(WebDriver driver) {
		super(driver);
	}
	
	public void login(String user, String pass) {
		
		// 1. Esperar a que el campo sea visible y obtener el elemento
		WebElement usuarioCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("usuario"))
		);
		
		WebElement passCampo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("password"))
        );
		
		WebElement botonEntrar = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/table/tbody/tr[7]/td/p/a"))
			);

		// 2. Limpiar el campo por seguridad y escribir
		usuarioCampo.clear();
		usuarioCampo.sendKeys(user);	
		        
        passCampo.clear();
        passCampo.sendKeys(pass);

        botonEntrar.click();	
		
	}

}
