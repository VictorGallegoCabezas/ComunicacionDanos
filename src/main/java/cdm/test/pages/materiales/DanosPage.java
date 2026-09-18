package cdm.test.pages.materiales;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class DanosPage extends BasePage{

	public DanosPage(WebDriver driver) {
		super(driver);		
	}
	
	public void comprobarPaginaDanos() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//div[@class='tr12b'][contains(.,'causa que ha originado los daños')]")
		));

		assertTrue(elemento.isDisplayed(),
		        "No se encontró la pregunta sobre la causa de los daños en la página");

	}
	
	public void rellenarYEnviarFormularioDanos() {
		
		driver.findElement(By.name("observaciones"))
			.sendKeys("se me ha inundado toda la casa");
		
		driver.findElement(By.name("altura"))
			.sendKeys("8");
		
		rellenarFecha();
		
		guardarCaptura("Página daños");
		
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/form/div[2]/div/img[2]"))
			.click();
		
	}
	
	private void rellenarFecha() {
		// Obtener la fecha de ayer
		LocalDate ayer = LocalDate.now().minusDays(1);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaAyer = ayer.format(formato);

		// Escribir en el campo
		WebElement campoFecha = driver.findElement(By.name("fechaSiniestro"));
		campoFecha.clear();
		campoFecha.sendKeys(fechaAyer);
	}
	
	public void comprobarPaginaDanos2() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement elemento = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//div[@class='tr12b'][contains(.,'tipo de bien que resultó afectado')]")
		        )
		);

		assertTrue(
		        elemento.isDisplayed(),
		        "No se encontró la pregunta sobre el tipo de bien afectado en la página"
		);

	}
	
	public void rellenarYEnviarFormularioDanos2() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement campoBien = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.name("obserbacionBien"))
		);

		campoBien.clear();
		campoBien.sendKeys("todo ha quedado destruido");
		
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/form/div[1]/div[7]/div[2]/div[1]/img")))
		.click();
		
		driver.findElement(By.id("imagenBuscar")).click();

		By aseguradora = By.xpath("//td[contains(normalize-space(.),'AACHENMUNCHENER VERSICHERUNGS AG')]");

		// Espera a que sea visible o clickeable
		wait.until(ExpectedConditions.elementToBeClickable(aseguradora)).click();
		
		
		driver.findElement(By.name("numeroPoliza")).sendKeys("123456ABCDE");
		
		driver.findElement(
			    By.xpath("//a[img[contains(@src,'X099M_flechader.gif')]]")
			).click();
		
		driver.findElement(By.name("importe")).sendKeys("50000");
		
		// Localizar por la función JS 'validoSiniestro2' que ejecuta al hacer clic
		By botonAceptar = By.xpath("//img[contains(@onclick, 'validoSiniestro2')]");

		// Esperar a que sea interactuable y pulsar
		wait.until(ExpectedConditions.elementToBeClickable(botonAceptar)).click();

	}

}
