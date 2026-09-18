package cdm.test.pages.personales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import cdm.test.comun.BasePage;

public class AccidentePage extends BasePage {

	public AccidentePage(WebDriver driver) {
		super(driver);
	}
	
	public void rellenarYEnviar() {
		
		//Localidad
		WebElement localidadAyuda = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("locOcurrencia"))
			);
		localidadAyuda.click();
		
		WebElement localidadAyuda2 = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("imagenBuscar"))
			);
		localidadAyuda2.click();
		
		WebElement filaLocalidad = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//tr[contains(@onclick, 'cargoLocalidad') and contains(@onclick, 'ACEBEDO')]"))
		);
		filaLocalidad.click();
		
		//--------------------------------calle
		WebElement municipioAyuda = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("calOcurrencia"))
			);
		municipioAyuda.click();
		
		WebElement municipioBuscar = wait.until(
		    ExpectedConditions.elementToBeClickable(By.id("imagenBuscar"))
		);
		municipioBuscar.click();
		
		WebElement filaCalle = wait.until(
		    ExpectedConditions.elementToBeClickable(
		        By.xpath("//tr[contains(@onclick, 'cargoCalle') and contains(@onclick, 'NTRA. SRA. DE LA ASUNCION')]")
		    )
		);
		filaCalle.click();
		//--------------------------------numero
		WebElement numeroCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("numeroOcurrencia"))
		);
		numeroCampo.clear();
		numeroCampo.sendKeys("1");
		//--------------------------------Descripcion accidente
		WebElement descripcionCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("descripcionAccidente"))
		);
		descripcionCampo.clear();
		descripcionCampo.sendKeys("Me choque con una farola");
		//--------------------------------descripcionDañosPropios
		WebElement danosPropiosCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("descripcionDanosPropiso"))
		);
		danosPropiosCampo.clear();
		danosPropiosCampo.sendKeys("Parachoques delantero roto");
		//--------------------------------culpabilidad
		WebElement culpabilidadCombo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("culpabilidad"))
		);
		Select selectCulpabilidad = new Select(culpabilidadCombo);
		selectCulpabilidad.selectByValue("S");
		//-------------------------------- Atestado
		WebElement atestadoCombo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("atestado"))
		);
		Select selectAtestado = new Select(atestadoCombo);
		selectAtestado.selectByValue("N");
		//-------------------------------- Enviado Juzgado
		WebElement enviadoJuzgadoCombo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("enviadoJuzgado"))
		);
		Select selectEnviadoJuzgado = new Select(enviadoJuzgadoCombo);
		selectEnviadoJuzgado.selectByValue("O");
		//-------------------------------- numero Vehiculos
		WebElement numeroVehiculosCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("numeroVehiculos"))
		);
		numeroVehiculosCampo.clear();
		numeroVehiculosCampo.sendKeys("0");		
		//-------------------------------- numero de personas
		WebElement numeroPersonasCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("numeroPersonas"))
		);
		numeroPersonasCampo.clear();
		numeroPersonasCampo.sendKeys("0");	
		//-------------------------------- numero de bienes
		WebElement numeroBienesCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.name("numeroBienes"))
		);
		numeroBienesCampo.clear();
		numeroBienesCampo.sendKeys("1");
		//-------------------------------- Botón Aceptar
		WebElement botonAceptar = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@src, 'X099M_aceptar.gif')]"))
		);
		guardarCaptura("Datos accidente");
		botonAceptar.click();
		
		
		
	}

}
