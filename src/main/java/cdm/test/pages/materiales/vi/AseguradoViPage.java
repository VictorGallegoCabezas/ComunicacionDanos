package cdm.test.pages.materiales.vi;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cdm.test.comun.BasePage;

public class AseguradoViPage extends BasePage {

    public AseguradoViPage(WebDriver driver) {
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
        abrirAyudaYBuscar(By.id("loc"));

        // Seleccionar localidad
        By linkAcebedo = By.linkText("ACEBEDO");
        wait.until(ExpectedConditions.elementToBeClickable(linkAcebedo)).click();

        // Si la ayuda se abrió en una nueva ventana, volver a la ventana principal
        restaurarVentanaPrincipal();

        // --- AYUDA CALLE ---
        abrirAyudaYBuscar(By.id("cal"));

        // Seleccionar calle
        By linkOmecillo = By.linkText("OMECILLO");
        wait.until(ExpectedConditions.elementToBeClickable(linkOmecillo)).click();

        // Volver a la ventana principal
        restaurarVentanaPrincipal();

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
     * Abre el buscador auxiliar, cambia el foco de Selenium (si abre pop-up o iframe) 
     * y hace clic en 'Buscar'.
     */
    private void abrirAyudaYBuscar(By btnAyudaLocator) {
        String ventanaOriginal = driver.getWindowHandle();

        // 1. Asegurar el clic en el botón de la ayuda (loc o cal)
        WebElement btnAyuda = wait.until(ExpectedConditions.elementToBeClickable(btnAyudaLocator));
        try {
            btnAyuda.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", btnAyuda);
        }

        // 2. Si se abrió una ventana emergente (Pop-up), cambiar el foco a ella
        Set<String> todasLasVentanas = driver.getWindowHandles();
        if (todasLasVentanas.size() > 1) {
            for (String handle : todasLasVentanas) {
                if (!handle.equals(ventanaOriginal)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
        }

        // 3. Comprobar si existe un iframe activo y cambiar a él si es necesario
        try {
            driver.switchTo().defaultContent(); // Reset
            if (driver.findElements(By.tagName("iframe")).size() > 0) {
                driver.switchTo().frame(0);
            }
        } catch (Exception ignored) {
            // Si no hay iframe, continuar en el contexto actual
        }

        // 4. Hacer clic en el botón 'Buscar'
        By imgBuscar = By.cssSelector("img[alt='Buscar']");
        hacerClicRobusto(imgBuscar);
    }

    /**
     * Vuelve al foco de la ventana principal si se estaba trabajando en un pop-up o iframe.
     */
    private void restaurarVentanaPrincipal() {
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() > 1) {
            // Si hay pop-up abierta, se cierra tras la selección
            driver.close();
            driver.switchTo().window(handles.iterator().next());
        } else {
            // Si era un iframe, vuelve al contexto HTML principal
            driver.switchTo().defaultContent();
        }
    }

    /**
     * Realiza un clic tolerante a fallos de visibilidad y animaciones.
     */
    private void hacerClicRobusto(By locator) {
        WebDriverWait esperaRobusta = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        WebElement elemento = esperaRobusta.until(ExpectedConditions.presenceOfElementLocated(locator));
        
        try {
            esperaRobusta.until(ExpectedConditions.elementToBeClickable(elemento)).click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", elemento);
        }
    }
}