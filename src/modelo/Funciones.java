package modelo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import config.Config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static config.Config.API_URL;

public class Funciones {

    // private static final String API_KEY = "62d2f4826d85f49406fbca47";
    //private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    /**
     * Actualiza las tasas de conversión de las monedas usando una API externa.
     * @param monedas Lista de monedas a actualizar.
     * @return true si la actualización fue exitosa, false si hubo error.
     */
    public boolean cargarConversionActual(List<Moneda> monedas) {
        if (monedas == null || monedas.isEmpty()) {
            return false;
        }

        try {
            // Hacer la petición HTTP a la API

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                System.err.println("Error en la API: Código " + conn.getResponseCode());
                return false;
            }

            // Leer y parsear el JSON de respuesta
            JsonObject jsonResponse = JsonParser.parseReader(new InputStreamReader(conn.getInputStream()))
                    .getAsJsonObject();

            if (!jsonResponse.get("result").getAsString().equals("success")) {
                System.err.println("La API no devolvió éxito: " + jsonResponse);
                return false;
            }

            JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");

            // Actualizar cada moneda en la lista
            for (Moneda moneda : monedas) {
                String codigo = moneda.getCurrencyCode();
                if (rates.has(codigo)) {
                    moneda.setConversionRate(rates.get(codigo).getAsDouble());
                }
            }

            return true;

        } catch (Exception e) {
            System.err.println("Error al actualizar tasas: " + e.getMessage());
            return false;
        }
    }


    /**
     * Carga una lista de monedas desde un archivo JSON.
     * @param monedas Lista donde se almacenarán las monedas (se borrará antes de cargar).
     * @return true si la carga fue exitosa, false si hubo un error.
     */
    public boolean cargarListaMonedas(List<Moneda> monedas){
        // Validación de entrada
        if (monedas == null){
            return false;
        }
        // Limpiar la lista existente para evitar duplicados
        monedas.clear();

        try {
            // Leer el archivo JSON desde /resources/
            InputStream inputStream = Funciones.class.getResourceAsStream("/resources/monedas.json");
            if (inputStream == null){
                System.err.println("Error: El archivo monedas.json no econtrado");
                return false;
            }
            // Convertir el JSON a List<Moneda> usando Gson
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(inputStream);
            List<Moneda> listaTemporal = gson.fromJson(reader, new TypeToken<List<Moneda>>(){}.getType());
            // Agregar todas las monedas a la lista recibida
            monedas.addAll(listaTemporal);
            return true;

        } catch (Exception e){
            System.err.println("Error al cargar monedas: " + e.getMessage());
            return false;
        }
    }

    /**
     * Calcula el cambio entre dos monedas.
     * @param monedas Lista de monedas con tasas actualizadas.
     * @param codigoOrigen Código de la moneda origen (ej: "ARS").
     * @param codigoDestino Código de la moneda destino (ej: "USD").
     * @param importe Cantidad a convertir.
     * @return Valor convertido, o -1 si hay error.
     */
    public double calcularCambio(List<Moneda> monedas, String codigoOrigen, String codigoDestino, double importe) {
        if (monedas == null || codigoOrigen == null || codigoDestino == null || importe < 0) {
            return -1;
        }
        // Buscar monedas usando Streams primero lo hice con for pero el amigo GPT me suguirio los STRAM()
        Moneda monedaOrigen = monedas.stream()
                .filter(m -> m.getCurrencyCode().equalsIgnoreCase(codigoOrigen))
                .findFirst()
                .orElse(null);

        Moneda monedaDestino = monedas.stream()
                .filter(m -> m.getCurrencyCode().equalsIgnoreCase(codigoDestino))
                .findFirst()
                .orElse(null);
        if (monedaOrigen == null || monedaDestino == null) {
            System.err.println("Error: Códigos de moneda no válidos.");
            return -1;
        }
        // Evitar división por cero
        if (monedaOrigen.getConversionRate() == 0) {
            System.err.println("Error: La tasa de origen no puede ser cero.");
            return -1;
        }
        return (importe / monedaOrigen.getConversionRate()) * monedaDestino.getConversionRate();
    }

    public void imprimirMenu(){
        for (int i=0; i<50; i++){
            System.out.println();
        }
        System.out.println("""
                **************************************************************************************
                Sea bienvenido/a al Conversor de Monedas =]
                
                1) Dólar =>> Peso Argentino
                2) Peso Argentino =>> Dólar
                3) Dólar =>> Real Brasileño
                4) Ral Brasileño =>> Dólar
                5) Dólar =>> Peso Colombiano
                6) Peso Colombiano =>> Dólar
                7) Salir
                Elija una Opcion Valida:
                ***************************************************************************************
                """);
    }
}
