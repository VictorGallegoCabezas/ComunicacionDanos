package cdm.test.comun;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.qameta.allure.Allure;

public class TestWatcherExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Object testInstance = context.getTestInstance().orElse(null);

        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();
            
            // Validar que el driver no sea nulo y que la sesión siga activa
            if (driver != null && ((RemoteWebDriver) driver).getSessionId() != null) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Captura en caso de fallo", "image/png", new ByteArrayInputStream(screenshot), ".png");
                } catch (Exception e) {
                    System.err.println("No se pudo tomar la captura tras el fallo: " + e.getMessage());
                }
            }
        }
    }
}