package config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 *  Centralizando la configuracion del proyecto (API key, URL, ETC.).
 *  las variables se cargan desde variables del sistema de no existir buscan un archivo .env
 */
public class Config {
    private static final String API_KEY = cargarApiKey();
    public static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    private static String cargarApiKey() {
        String apiKey = System.getenv("API_KEY");
        if (apiKey == null || apiKey.isEmpty()){
            apiKey = Dotenv.load().get("API_KEY");
        }
        return apiKey;
    }
}
