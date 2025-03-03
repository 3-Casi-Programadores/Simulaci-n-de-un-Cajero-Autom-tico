# 📘 Documentación del Proyecto  

## 📌 1. Descripción  
**Nombre del Proyecto:** [Nombre del Proyect Cajero Automatico ]  
**Fecha de Creación:** [02/03/2025]  
**Autor(es):** [Nombres del encargado]  
**Versión:** [1.0 / 2.0, etc.]  

📢 **Resumen:**  
[Breve explicación del propósito del programa, qué problema resuelve y cómo funciona. Y Explica que logica recomiendas seguir.]  

---

## 🛠 2. Requisitos  
📌 **Lenguaje de programación:** Java  
📌 **Versión de Java recomendada:** [Ejemplo: Java 17]  
📌 **Dependencias necesarias:**  
- [Si el proyecto usa librerías externas, agrégalas aquí o sino borra esto y el "Dependencias necesarias"]  

📌 **Herramientas recomendadas:**  
- IDE sugerido: [VS STUDIO CODE]  
- Compilador: `javac`  




🏦 Simulación de Cajero Automático en Java

Este proyecto simula un cajero automático utilizando hilos (Thread) en Java. Permite manejar cuentas bancarias de múltiples clientes y realizar retiros de manera concurrente, asegurando la sincronización de los accesos a los fondos de cada cuenta.

📌 Características
	•	Permite agregar clientes con saldo inicial.
	•	Cada cliente puede realizar intentos de retiro.
	•	Implementa concurrencia con Thread para simular múltiples accesos simultáneos.
	•	Uso de synchronized para evitar inconsistencias en el saldo.

📜 Código y Explicación

📦 Importación de Librerías

import java.util.HashMap;
import java.util.Scanner;

	•	HashMap: Para almacenar cuentas bancarias asociadas a clientes.
	•	Scanner: Para leer datos ingresados por el usuario.

🏛 Clase CuentaBancaria

class CuentaBancaria {
    private double saldo;

	•	Atributo saldo: Representa el dinero disponible en la cuenta.

🔹 Constructor de CuentaBancaria

    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

	•	Inicializa la cuenta con un saldo dado.

🔹 Método retirar (Sincronizado)

    public synchronized boolean retirar(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            System.out.println("Retiro exitoso. Saldo restante: " + saldo);
            return true;
        } else {
            System.out.println("Saldo insuficiente para retirar " + monto);
            return false;
        }
    }

	•	synchronized: Asegura que solo un hilo pueda acceder al método a la vez.
	•	Si el saldo es suficiente, realiza el retiro y actualiza el saldo.
	•	Si no, muestra un mensaje de saldo insuficiente.

🔹 Método getSaldo

    public double getSaldo() {
        return saldo;
    }

	•	Devuelve el saldo actual de la cuenta.

👨‍💼 Clase Cliente (Extiende Thread)

class Cliente extends Thread {
    private String nombre;
    private CuentaBancaria cuenta;
    private double monto;

	•	Atributos:
	•	nombre: Nombre del cliente.
	•	cuenta: Cuenta bancaria del cliente.
	•	monto: Cantidad a retirar.

🔹 Constructor de Cliente

    public Cliente(String nombre, CuentaBancaria cuenta, double monto) {
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.monto = monto;
    }

	•	Recibe el nombre del cliente, su cuenta y el monto a retirar.

🔹 Método run (Ejecutado en un hilo separado)

    @Override
    public void run() {
        System.out.println(nombre + " intenta retirar " + monto);
        if (cuenta.retirar(monto)) {
            System.out.println(nombre + " ha retirado " + monto);
        } else {
            System.out.println(nombre + " no pudo retirar " + monto);
        }
    }

	•	Muestra el intento de retiro.
	•	Llama al método retirar de CuentaBancaria.
	•	Muestra si el retiro fue exitoso o no.

🏧 Clase CajeroAutomatico

public class CajeroAutomatico {
    private HashMap<String, CuentaBancaria> cuentas;

	•	cuentas: Mapea el nombre de un cliente con su cuenta bancaria.

🔹 Constructor

    public CajeroAutomatico() {
        cuentas = new HashMap<>();
    }

	•	Inicializa el HashMap de cuentas.

🔹 Método agregarCuenta

    public void agregarCuenta(String nombreCliente, double saldoInicial) {
        cuentas.put(nombreCliente, new CuentaBancaria(saldoInicial));
    }

	•	Agrega una nueva cuenta bancaria asociada a un cliente.

🔹 Método obtenerCuenta

    public CuentaBancaria obtenerCuenta(String nombreCliente) {
        return cuentas.get(nombreCliente);
    }

	•	Devuelve la cuenta bancaria de un cliente dado.

🏃‍♂️ Método main (Ejecución del Programa)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CajeroAutomatico cajero = new CajeroAutomatico();

	•	Crea una instancia de Scanner para entrada de usuario.
	•	Crea un objeto CajeroAutomatico.

🔹 Agregar Clientes

        System.out.println("¡Bienvenido al cajero automático!");
        System.out.print("¿Cuántos clientes deseas agregar? ");
        int numClientes = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer del Scanner

	•	Pide la cantidad de clientes.

        for (int i = 0; i < numClientes; i++) {
            System.out.print("Ingresa el nombre del cliente #" + (i + 1) + ": ");
            String nombreCliente = scanner.nextLine();
            System.out.print("Ingresa el saldo inicial para " + nombreCliente + ": ");
            double saldoInicial = scanner.nextDouble();
            scanner.nextLine();
            cajero.agregarCuenta(nombreCliente, saldoInicial);
        }

	•	Repite el proceso para cada cliente ingresado.

🔹 Intentos de Retiro

        System.out.print("¿Cuántos intentos de retiro deseas realizar? ");
        int numIntentos = scanner.nextInt();
        scanner.nextLine();

	•	Pide la cantidad de intentos de retiro.

        for (int i = 0; i < numIntentos; i++) {
            System.out.print("Ingresa el nombre del cliente para el intento de retiro #" + (i + 1) + ": ");
            String nombreCliente = scanner.nextLine();
            System.out.print("Ingresa el monto que " + nombreCliente + " desea retirar: ");
            double monto = scanner.nextDouble();
            scanner.nextLine();

	•	Pide el nombre y el monto a retirar.

            CuentaBancaria cuentaCliente = cajero.obtenerCuenta(nombreCliente);
            if (cuentaCliente != null) {
                Cliente cliente = new Cliente(nombreCliente, cuentaCliente, monto);
                cliente.start();
            } else {
                System.out.println("El cliente " + nombreCliente + " no tiene cuenta registrada.");
            }
        }

	•	Si el cliente existe, crea un Thread para el intento de retiro.

🔹 Esperar la Finalización de los Hilos

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

	•	Se da tiempo para que los hilos terminen de ejecutarse.

🔹 Cerrar el Scanner

        scanner.close();
    }

	•	Se cierra el Scanner para liberar recursos.

  Cómo Ejecutarlo
	1.	Copia el código en un archivo CajeroAutomatico.java.
	2.	Compila el programa:

javac CajeroAutomatico.java


	3.	Ejecuta el programa:

java CajeroAutomatico
