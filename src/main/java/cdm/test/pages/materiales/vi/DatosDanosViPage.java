package cdm.test.pages.materiales.vi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import cdm.test.comun.BasePage;

public class DatosDanosViPage extends BasePage{

	public DatosDanosViPage(WebDriver driver) {
		super(driver);
	}
	
	public void rellenarYAceptar() {
		//tipo bien
		By selectTipoBien = By.name("tipoBien");
        WebElement comboBien = wait.until(ExpectedConditions.visibilityOfElementLocated(selectTipoBien));
        Select select = new Select(comboBien);
	    select.selectByValue("40");
	    //descripcion
	    By txtObservacion = By.name("obserbacionBien");
	    WebElement textArea = wait.until(ExpectedConditions.visibilityOfElementLocated(txtObservacion));
	    textArea.clear();
	    textArea.sendKeys("Texto de observación para el bien de vehículos.");
	    //lupa marca
	    By btnBuscarMarca = By.cssSelector("input.botonAyuda[alt='Buscar']");
	    WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnBuscarMarca));
	    boton.click();
	    //ayuda marca
	    By imgBuscar = By.cssSelector("img[alt='Buscar']");
	    WebElement imagen = wait.until(ExpectedConditions.elementToBeClickable(imgBuscar));
	    imagen.click();
	    //select marca
	    By linkAbarth = By.linkText("ABARTH");
	    WebElement enlace = wait.until(ExpectedConditions.elementToBeClickable(linkAbarth));
	    enlace.click();
	    //ayuda modelo
	    By imgBuscarModelo = By.cssSelector("img[alt='Buscar']");
	    WebElement imagenModelo = wait.until(ExpectedConditions.elementToBeClickable(imgBuscarModelo));
	    imagenModelo.click();
	    //select modelo
	    By linkPuntoEvo = By.linkText("PUNTO EVO");
	    WebElement enlaceModelo = wait.until(ExpectedConditions.elementToBeClickable(linkPuntoEvo));
	    enlaceModelo.click();
	    //campo matricula
		By txtMatricula = By.name("matricula");
		WebElement campoMatricula = wait.until(ExpectedConditions.visibilityOfElementLocated(txtMatricula));
		campoMatricula.clear();
		campoMatricula.sendKeys("1234ABC");
	    //taller no
		By selectEstaEnTaller = By.name("estaEnTaller");
		WebElement comboTaller = wait.until(ExpectedConditions.visibilityOfElementLocated(selectEstaEnTaller));
		Select selectTaller = new Select(comboTaller);
		selectTaller.selectByValue("NO");
		//lugar donde se encuentra
		By txtLugar = By.name("lugarNoTaller");
	    WebElement textAreaLugar = wait.until(ExpectedConditions.visibilityOfElementLocated(txtLugar));
	    textAreaLugar.clear();
	    textAreaLugar.sendKeys("En mi garage");
		//Lupa aseguradora
	    By btnPoliza = By.name("poliza");
	    WebElement botonPoliza = wait.until(ExpectedConditions.elementToBeClickable(btnPoliza));
	    botonPoliza.click();
	    //Ayuda aseguradora
	    By imgBuscarAseguradora = By.cssSelector("img[alt='Buscar']");
	    WebElement imagenAseguradora = wait.until(ExpectedConditions.elementToBeClickable(imgBuscarAseguradora));
	    imagenAseguradora.click();
	    //Select aseguradora
	    By linkAcredia = By.linkText("ACREDIA VERSICHERUNG AG");
	    WebElement enlaceAcredia = wait.until(ExpectedConditions.elementToBeClickable(linkAcredia));
	    enlaceAcredia.click();
	    //numero poliza
	    By txtPoliza = By.name("numeroPoliza");
	    WebElement textPoliza = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPoliza));
	    textPoliza.clear();
	    textPoliza.sendKeys("123456Poliza");
	    //Aceptar
	    By btnAceptar = By.xpath("//input[@value='Aceptar' and contains(@onclick, 'validoSiniestro2')]");
	    WebElement botonAceptar = wait.until(ExpectedConditions.elementToBeClickable(btnAceptar));
	    botonAceptar.click();
	    
	}

}
