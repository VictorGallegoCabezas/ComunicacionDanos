package cdm.test.pages.materiales.vi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class DanosViPage extends BasePage {

    public DanosViPage(WebDriver driver) {
        super(driver);
    }
    
    public void rellenarYEnviar() {
        // 1. Causa
        By selectCausa = By.name("causaSiniestro");
        WebElement comboCausa = wait.until(ExpectedConditions.visibilityOfElementLocated(selectCausa));        
        Select select = new Select(comboCausa);
        select.selectByValue("10");

        // 2. Altura en cm
        driver.findElement(By.name("altura")).sendKeys("200");

        // 3. Fecha y quitar el foco
        rellenarFecha();
        perderFoco();

        // 4. Copiar dirección
        By btnCopiarDireccion = By.id("direccionContacto");
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnCopiarDireccion));
        boton.click();

        // 5. Clic seguro en Aceptar (resistente a refrenscos DOM / StaleElement)
        hacerClicSeguro(By.cssSelector("input.frenteBoton[value='Aceptar']"));   
    }
    
    public void comprobarPaginaDanos() {
        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='encabezado1D'][contains(.,'DATOS SOBRE LOS DAÑOS')]")
        ));

        assertTrue(elemento.isDisplayed(), 
                "No se encontró el encabezado de datos sobre los daños en la página");
    }
    
    private void rellenarFecha() {
        // Obtener la fecha de hace 90 días
        LocalDate ayer = LocalDate.now().minusDays(90);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaAyer = ayer.format(formato);

        // Escribir en el campo
        WebElement campoFecha = driver.findElement(By.name("fechaSiniestro"));
        campoFecha.clear();
        campoFecha.sendKeys(fechaAyer);
    }
    
    private void perderFoco() {
        WebElement saltoLinea = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.saltoLinea")
        ));     
        Actions actions = new Actions(driver);
        actions.moveToElement(saltoLinea).click().perform();
    }

    /**
     * Reintenta el clic en un elemento re-localizándolo en el DOM si se produce 
     * una StaleElementReferenceException por refresco AJAX/DOM.
     */
    private void hacerClicSeguro(By locator) {
        int intentos = 0;
        WebDriverWait esperaCorta = new WebDriverWait(driver, Duration.ofSeconds(10));

        while (intentos < 3) {
            try {
                WebElement elemento = esperaCorta.until(ExpectedConditions.elementToBeClickable(locator));
                elemento.click();
                break; // Clic exitoso, salir del bucle
            } catch (StaleElementReferenceException e) {
                intentos++;
                if (intentos == 3) {
                    throw e; // Si tras 3 intentos sigue fallando, re-lanzar la excepción
                }
            }
        }
    }
}