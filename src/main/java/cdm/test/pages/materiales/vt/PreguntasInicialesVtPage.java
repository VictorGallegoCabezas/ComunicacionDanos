package cdm.test.pages.materiales.vt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cdm.test.comun.BasePage;

public class PreguntasInicialesVtPage extends BasePage {

    // --- SELECTORES ---

	private By tituloPreguntasIniciales = 
            By.xpath("//*[contains(text(), 'PREGUNTAS INICIALES')]");

    private By radioGroup1Si =
            By.xpath("//input[@class='radio3' and @name='group1' and @value='si']");

    private By radioGroup0Persona =
            By.xpath("//input[@class='radio3' and @name='group0' and @value='p']");

    private By radioGroup3Si =
            By.xpath("//input[@class='radio3' and @name='group3' and @value='si']");

    // Primer botón aceptar (irA('lectura'))
    private By botonAceptarLectura =
            By.xpath("//div[@id='botonera']//img[contains(@src,'X099M_aceptar.gif')]");

    // Segundo botón aceptar (irA('interlocutor'))
    private By botonAceptarInterlocutor =
            By.xpath("//img[contains(@src,'X099M_aceptar.gif') and @id='botonera']");


    // --- CONSTRUCTOR ---
    public PreguntasInicialesVtPage(WebDriver driver) {
        super(driver);
    }


    // --- MÉTODOS DE LA PÁGINA ---

    public void esperarCargaPreguntasIniciales() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPreguntasIniciales));
    }

    public boolean estaEnPreguntasIniciales() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPreguntasIniciales));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickGroup1Si() {
        wait.until(ExpectedConditions.elementToBeClickable(radioGroup1Si)).click();
    }

    public void clickGroup0Persona() {
        wait.until(ExpectedConditions.elementToBeClickable(radioGroup0Persona)).click();
    }

    public void clickGroup3Si() {
        wait.until(ExpectedConditions.elementToBeClickable(radioGroup3Si)).click();
    }

    public void clickAceptarLectura() {
        wait.until(ExpectedConditions.elementToBeClickable(botonAceptarLectura)).click();
    }

    public void aceptarGrabacion() {
    	wait.until(ExpectedConditions.elementToBeClickable(
    		    By.xpath("//input[@name='group3' and @value='si']")
    		)).click();
    	guardarCaptura("Página grabación");
    	wait.until(ExpectedConditions.elementToBeClickable(botonAceptarInterlocutor)).click();
    	
    }
}
