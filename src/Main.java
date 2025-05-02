import com.google.gson.Gson;
import modelo.Funciones;
import modelo.Logger;
import modelo.Moneda;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            Logger.log("Iniciando el Programa");
            List<Moneda> monedas = new ArrayList<>();
            Funciones funciones = new Funciones();
            if (!funciones.cargarListaMonedas(monedas)){
                System.err.println("No se pudieron Cargar Las Monedas");
                Logger.log("No se puede cargar las monedas desde el JSON");
                return;
            }

            if (!funciones.cargarConversionActual(monedas)){
                System.err.println("No se pudo comunicar con la API y Cargar las Conversiones Actuales");
                Logger.log("No se pudo comunicar con la API y Cargar las Conversiones Actuales");
                return;
            }

            //System.out.println("Convertir un USD a ARS: " + funciones.calcularCambio(monedas,"USD","ARS",1));
            Scanner scanner = new Scanner(System.in);
            int opcion;
            double monto=0.00, resultado=0.00;
            do {
                funciones.imprimirMenu();
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion != 7){
                    System.out.print("Ingresa el Monto a Convertir: ");
                    monto = scanner.nextDouble();
                    scanner.nextLine();
                }
                switch (opcion) {
                    case 1:
                        resultado = funciones.calcularCambio(monedas,"USD","ARS",monto);
                        System.out.print("La conversion de Dólares a Pesos Argentinos seria de: " + resultado);
                        Logger.log("Convirtiendo " + monto + " de USD a  ARS: " + resultado);
                        esperarTecla(scanner);
                        break;
                    case 2:
                        resultado = funciones.calcularCambio(monedas,"ARS","USD",monto);
                        System.out.print("La conversionde Pesos Argentinos a Dólares seria de: " + resultado);
                        Logger.log("Convirtiendo " + monto + " de ARS a  USD: " + resultado);
                        esperarTecla(scanner);
                        break;
                    case 3:
                        resultado = funciones.calcularCambio(monedas,"USD","BRL",monto);
                        System.out.print("La conversionde Dólares a Reales Brasileños seria de: " + resultado);
                        Logger.log("Convirtiendo " + monto + " de USD a  BRL: " + resultado);
                        esperarTecla(scanner);
                        break;
                    case 4:
                        resultado = funciones.calcularCambio(monedas,"BRL","USD",monto);
                        System.out.print("La conversionde Reales Brasileños a Dólares seria de: " + resultado);
                        Logger.log("Convirtiendo " + monto + " de BRL a  USD: " + resultado);
                        esperarTecla(scanner);
                        break;
                    case 5:
                        resultado = funciones.calcularCambio(monedas,"USD","COP",monto);
                        System.out.print("La conversionde Dólares a Pesos Colombianos seria de: " + resultado);
                        Logger.log("Convirtiendo " + monto + " de USD a  COP: " + resultado);
                        esperarTecla(scanner);
                        break;
                    case 6:
                        resultado = funciones.calcularCambio(monedas,"COP","USD",monto);
                        System.out.print("La conversionde Pesos Colombianos a Dólares seria de: " + resultado);
                        Logger.log("Convirtiendo " + monto + " de COP a  USD: " + resultado);
                        esperarTecla(scanner);
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...");
                        Logger.log("Saliendo del Programa !!" );
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo. del 1 al 7");
                }

            }while(opcion != 7);

            scanner.close();

        }
        public static void esperarTecla(Scanner scanner){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
        }

    }
