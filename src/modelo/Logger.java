package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE = "conversiones.log";
    private static final Path LOG_PATH = Paths.get(LOG_DIR, LOG_FILE);

    static {
        try{
            Files.createDirectories(LOG_PATH.getParent());
        }catch (IOException e){
            System.err.println("Error creando carpeta de logs: " + e.getMessage());
        }
    }

    public static void log(String mensaje){
        String registro = String.format("[%s] %s\n",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), mensaje);
        try {
            Files.write(
              LOG_PATH,
              registro.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e){
            System.err.println("Error escribiendo en el log: " + e.getMessage());
        }
    }
}
