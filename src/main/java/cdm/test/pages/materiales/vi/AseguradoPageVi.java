package cdm.test.pages.materiales.vi;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class AseguradoPageVi extends BasePage {

    public AseguradoPageVi(WebDriver driver) {
        super(driver);
    }

    public void rellenarYEnviarFormulario() {

        // Rellenar DNI
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("documentoInterlocutor")))
                .sendKeys("77777777B");

        // Rellenar Nombre y Apellidos
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombreInterlocutor"))).sendKeys("Manolo");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("apellidoInterlocutor"))).sendKeys("Garcia");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("apellido2Interlocutor"))).sendKeys("Perez");

        // --- AYUDA LOCALIDAD ---
        By btnAyudaLocalidad = By.id("loc");
        wait.until(ExpectedConditions.elementToBeClickable(btnAyudaLocalidad)).click();

        // Buscar en ayuda localidad (esperar primero a que sea visible en el DOM)
        By imgBuscar = By.cssSelector("img[alt='Buscar']");
        hacerClicRobusto(imgBuscar);

        // Seleccionar localidad
        By linkAcebedo = By.linkText("ACEBEDO");
        wait.until(ExpectedConditions.elementToBeClickable(linkAcebedo)).click();

        // --- AYUDA CALLE ---
        By btnAyudaCalle = By.id("cal");
        wait.until(ExpectedConditions.elementToBeClickable(btnAyudaCalle)).click();

        // Buscar en ayuda calle
        hacerClicRobusto(imgBuscar);

        // Seleccionar calle
        By linkOmecillo = By.linkText("OMECILLO");
        wait.until(ExpectedConditions.elementToBeClickable(linkOmecillo)).click();

        // Campo número
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("numeroInterlocutor"))).sendKeys("1");

        // Correo y Móvil
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("correoInterlocutor"))).sendKeys("prueba@prueba.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("movilInterlocutor"))).sendKeys("654654654");

        // Botón Aceptar
        By btnAceptar = By.cssSelector("input.frenteBoton[value='Aceptar']");
        hacerClicRobusto(btnAceptar);
    }

    /**
     * Espera a que el elemento esté visible y sea interactuable.
     * Si el clic nativo falla por un problema de capa/modal, aplica un clic por JavaScript.
     */
    private void hacerClicRobusto(By locator) {
        WebDriverWait esperaRobusta = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // 1. Asegurar que el elemento está presente y visible primero
        WebElement elemento = esperaRobusta.until(ExpectedConditions.visibilityOfElementLocated(locator));
        
        // 2. Intentar clic estándar o fallback mediante JS si la interfaz tiene capas/animaciones
        try {
            esperaRobusta.until(ExpectedConditions.elementToBeClickable(elemento)).click();
        } catch (Exception e) {
            log.warning("Clic nativo falló en " + locator + ". Reintentando con JavaScript Click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", elemento);
        }
    }
}