package cdm.test.comun;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import cdm.test.pages.partesAccidentes.vt.InicioPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.util.ResultsUtils;

@ExtendWith(TestWatcherExtension.class)
public class BaseTest {

    protected WebDriver driver;
    protected static Logger log = Logger.getLogger("EvidenciasLogger");

    protected String rutaCarpetaTest;
    protected String downloadPath;
    protected String nombreTest;
    protected String fechaActual;
    private FileHandler fileHandler;

    @BeforeEach
    public void setUp(TestInfo testInfo) throws IOException {

        // 1. Obtener nombre del método de test actual dinámicamente
        nombreTest = testInfo.getTestMethod().isPresent() 
                ? testInfo.getTestMethod().get().getName() 
                : "test_desconocido";

        // Fecha aaaammdd
        fechaActual = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // Carpeta específica para ESTE test: ./logs/CD_YYYYMMDD/nombreDelTest
        rutaCarpetaTest = System.getProperty("user.dir")
                + File.separator + "logs"
                + File.separator + "CD_" + fechaActual
                + File.separator + nombreTest;

        File carpetaTest = new File(rutaCarpetaTest);
        if (!carpetaTest.exists()) {
            carpetaTest.mkdirs();
        }

        // Registrar propiedad de sistema para que BasePage sepa dónde guardar la captura local
        System.setProperty("rutaCarpetaTest", rutaCarpetaTest);

        // Carpeta de descargas específica del test
        downloadPath = rutaCarpetaTest + File.separator + "descargas";
        File dirDescargas = new File(downloadPath);
        if (!dirDescargas.exists()) {
            dirDescargas.mkdirs();
        } else {
            FileUtils.cleanDirectory(dirDescargas);
        }

        // Registrar la ruta de descargas para que BasePage la lea dinámicamente
        System.setProperty("downloadPath", downloadPath);

        // Logger específico para la carpeta de este test
        inicializarLogger(rutaCarpetaTest, fechaActual);
        log.info("Iniciando test: " + nombreTest);
        log.info("Carpeta del test creada en: " + rutaCarpetaTest);
        log.info("Carpeta de descargas configurada en: " + downloadPath);

        // Configuración de metadatos en Allure
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.getLabels().removeIf(label -> 
                "parentSuite".equals(label.getName()) || 
                "suite".equals(label.getName()) || 
                "subSuite".equals(label.getName())
            );

            testResult.getLabels().add(ResultsUtils.createParentSuiteLabel("CD_" + fechaActual));
            testResult.getLabels().add(ResultsUtils.createSuiteLabel(nombreTest));
        });

        // Configurar Chrome
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        // Asignar ruta absoluta resuelta para garantizar compatibilidad al ejecutar desde .jar
        prefs.put("download.default_directory", new File(downloadPath).getAbsolutePath());
        prefs.put("download.prompt_for_download", false);
        prefs.put("plugins.always_open_pdf_externally", true);
        
        // Configuración específica para descargas automáticas sin bloqueos de seguridad (.jnlp)
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        prefs.put("safebrowsing.disable_download_protection", true);

        options.setExperimentalOption("prefs", prefs);
        
        // Argumentos de seguridad para Chrome
        options.addArguments("--safebrowsing-disable-download-protection");
        options.addArguments("--safebrowsing-disable-extension-blacklist");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (fileHandler != null) {
            fileHandler.close();
            log.removeHandler(fileHandler);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getRutaCarpetaTest() {
        return rutaCarpetaTest;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    protected void inicializarLogger(String rutaCarpeta, String fecha) throws IOException {
        String nombreLog = "log_" + nombreTest + "_" + fecha + ".log";
        String rutaLog = rutaCarpeta + File.separator + nombreLog;

        fileHandler = new FileHandler(rutaLog, true);
        fileHandler.setFormatter(new MiFormatoLog());

        for (Handler h : log.getHandlers()) {
            log.removeHandler(h);
        }

        log.addHandler(fileHandler);
        log.setUseParentHandlers(false);
    }

    public static class MiFormatoLog extends java.util.logging.Formatter {
        @Override
        public String format(java.util.logging.LogRecord record) {
            String fecha = java.time.LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return fecha + "  "
                    + record.getLevel() + "  "
                    + record.getMessage() + System.lineSeparator();
        }
    }

    protected void cambiarAFocoNuevaVentana() {
        String original = driver.getWindowHandle();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);

        for (String h : driver.getWindowHandles()) {
            if (!h.equals(original)) {
                driver.switchTo().window(h);
                break;
            }
        }
    }
    
    public void loginCertificado(String url) throws InterruptedException {		
		 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		InicioPage inicioPage = new InicioPage(driver);		
        inicioPage.navegateTo(url);
        
        wait
        .until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.frenteBoton[value='Entrar']")))
        .click();
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("linkJNLP_download")))
        .click();
        
        // 1. Espera de descarga
        Thread.sleep(5000); 
        
        // 2. Comprobar que existe el archivo .jnlp
        File carpetaDescargas = new File(downloadPath);
        File[] archivos = carpetaDescargas.listFiles((dir, name) -> name.endsWith(".jnlp"));
        
        if (archivos == null || archivos.length == 0) {
            log.severe("Fichero .jnlp necesario para el login no se ha descargado");
            fail("Fichero .jnlp necesario para el login no se ha descargado");
        }
        
        File archivoJnlp = archivos[0];
        log.info("Fichero JNLP localizado en: " + archivoJnlp.getAbsolutePath());

        // 3. Abrir el archivo .jnlp simulando un doble clic del sistema operativo
        try {
            log.info("Abriendo el archivo JNLP mediante doble clic del sistema operativo...");
            
            // Comprobar si la plataforma actual soporta el módulo Desktop
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                if (archivoJnlp.exists()) {
                    desktop.open(archivoJnlp); // Equivale a doble clic en Windows
                    log.info("Comando de apertura mediante doble clic enviado con éxito.");
                }
            } else {
                log.severe("java.awt.Desktop no está soportado en este entorno.");
                fail("El entorno no soporta la acción de doble clic automático sobre archivos.");
            }
        } catch (Exception e) {
            log.severe("Error al intentar abrir el archivo JNLP: " + e.getMessage());
            fail("No se pudo abrir el archivo JNLP mediante doble clic. Error: " + e.getMessage());
        }

        // 4. Controlar la ventana Swing/AWT con SikuliX
        Screen screen = new Screen();
        
        try {            
        	Thread.sleep(25000); 
            // Seleccionar certificado
        	// 1. Presionar ESPACIO o ENTER para seleccionar la fila por defecto / o FLECHA ABAJO + ESPACIO
        	screen.type(Key.TAB);
        	screen.type(Key.TAB);
        	screen.type(Key.TAB);
            screen.type(org.sikuli.script.Key.SPACE);       	           	
            
            //Boton enviar
           	Region ventanaViafirma = App.focusedWindow();

           	if (ventanaViafirma != null) {
           	    // La esquina superior izquierda de la ventana es (ventanaViafirma.x, ventanaViafirma.y)
           	    
           	    // Ejemplo: Si el certificado está a 700px a la derecha y 420px hacia abajo de la ventana
           	    int certX = ventanaViafirma.x + 700;
           	    int certY = ventanaViafirma.y + 420;
           	    
	           	screen.click(new org.sikuli.script.Location(certX, certY));
	            log.info("Clic realizado en el certificado dentro de la ventana.");
	            Thread.sleep(1000);
	            
	            screen.type(org.sikuli.script.Key.ENTER);
	            log.info("Certificado aceptado");
	            Thread.sleep(1000);
	            screen.type(org.sikuli.script.Key.ENTER);
	        }
            
        } catch (FindFailed e) {
            log.info("SikuliX no pudo encontrar el elemento visual en pantalla: " + e.getMessage());
            fail("SikuliX no encontró el elemento en la interfaz Java.");
        }       
        
        
	}
}