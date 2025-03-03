import java.util.HashMap;
import java.util.Scanner;

class CuentaBancaria {
    private double saldo;

    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Método sincronizado para asegurar que solo un hilo pueda hacer el retiro a la vez.
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

    public double getSaldo() {
        return saldo;
    }
}

class Cliente extends Thread {
    private String nombre;
    private CuentaBancaria cuenta;
    private double monto;

    public Cliente(String nombre, CuentaBancaria cuenta, double monto) {
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void run() {
        System.out.println(nombre + " intenta retirar " + monto);
        if (cuenta.retirar(monto)) {
            System.out.println(nombre + " ha retirado " + monto);
        } else {
            System.out.println(nombre + " no pudo retirar " + monto);
        }
    }
}

public class CajeroAutomatico {
    private HashMap<String, CuentaBancaria> cuentas;

    public CajeroAutomatico() {
        cuentas = new HashMap<>();
    }

    // Agregar cuentas al cajero automático
    public void agregarCuenta(String nombreCliente, double saldoInicial) {
        cuentas.put(nombreCliente, new CuentaBancaria(saldoInicial));
    }

    // Obtener una cuenta bancaria por cliente
    public CuentaBancaria obtenerCuenta(String nombreCliente) {
        return cuentas.get(nombreCliente);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CajeroAutomatico cajero = new CajeroAutomatico();

        // Agregar cuentas iniciales
        System.out.println("¡Bienvenido al cajero automático!");
        System.out.print("¿Cuántos clientes deseas agregar? ");
        int numClientes = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer del Scanner

        for (int i = 0; i < numClientes; i++) {
            System.out.print("Ingresa el nombre del cliente #" + (i + 1) + ": ");
            String nombreCliente = scanner.nextLine();

            double saldoInicial = 0;
            boolean saldoValido = false;
            while (!saldoValido) {
                try {
                    System.out.print("Ingresa el saldo inicial para " + nombreCliente + ": ");
                    saldoInicial = Double.parseDouble(scanner.nextLine());
                    if (saldoInicial < 0) {
                        System.out.println("El saldo inicial no puede ser negativo. Intenta nuevamente.");
                    } else {
                        saldoValido = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor ingresa un número válido para el saldo.");
                }
            }

            cajero.agregarCuenta(nombreCliente, saldoInicial);
        }

        // Ahora simulamos los intentos de retiro de los clientes
        System.out.print("¿Cuántos intentos de retiro deseas realizar? ");
        int numIntentos = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer del Scanner

        for (int i = 0; i < numIntentos; i++) {
            System.out.print("Ingresa el nombre del cliente para el intento de retiro #" + (i + 1) + ": ");
            String nombreCliente = scanner.nextLine();
            System.out.print("Ingresa el monto que " + nombreCliente + " desea retirar: ");
            double monto = 0;
            boolean montoValido = false;
            while (!montoValido) {
                try {
                    monto = Double.parseDouble(scanner.nextLine());
                    if (monto <= 0) {
                        System.out.println("El monto de retiro debe ser mayor que cero. Intenta nuevamente.");
                    } else {
                        montoValido = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor ingresa un número válido para el monto de retiro.");
                }
            }

            // Crear el hilo del cliente que intenta retirar
            CuentaBancaria cuentaCliente = cajero.obtenerCuenta(nombreCliente);
            if (cuentaCliente != null) {
                Cliente cliente = new Cliente(nombreCliente, cuentaCliente, monto);
                cliente.start();
            } else {
                System.out.println("El cliente " + nombreCliente + " no tiene cuenta registrada.");
            }
        }

        // Esperamos a que todos los hilos terminen
        try {
            Thread.sleep(2000);  // Damos tiempo para que todos los hilos terminen de ejecutar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Cerramos el scanner
        scanner.close();
    }
}
