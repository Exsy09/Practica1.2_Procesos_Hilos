import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TrianguloThread extends Thread {
    private double base;
    private double altura;
    private double area;

    public TrianguloThread(double base, double altura) {
        this.base = base;
        this.altura = altura;
    }

    // Constructor que permite establecer prioridad
    public TrianguloThread(double base, double altura, int prioridad) {
        this.base = base;
        this.altura = altura;
        this.setPriority(prioridad);
    }

    @Override
    public void run() {
        // Cálculo del área: (base * altura) / 2
        // Se evita el uso de multiplicación directa para base*altura
        double suma = 0;
        for (int i = 0; i < (int) altura; i++) {
            suma += base; // suma repetida en lugar de multiplicación
        }

        // Parte decimal (si existe)
        double parteDecimal = altura - (int) altura;
        suma += base * parteDecimal; // permitido según el enunciado

        area = suma / 2;

        // Mostrar resultado
        System.out.println(
                "\n[Hilo " + getName() + "]" +
                        " Base: " + base +
                        " Altura: " + altura +
                        " → Área: " + area +
                        " (Prioridad: " + getPriority() + ")"
        );
    }
}

public class CalculoAreasTriangulos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<TrianguloThread> hilos = new ArrayList<>();

        System.out.println("=== CÁLCULO DE ÁREAS DE TRIÁNGULOS EN PARALELO ===");
        System.out.print("¿Cuántos triángulos desea calcular? ");
        int n = sc.nextInt();

        System.out.print("¿Desea asignar prioridades a los hilos? (s/n): ");
        boolean asignarPrioridad = sc.next().equalsIgnoreCase("s");

        for (int i = 1; i <= n; i++) {
            System.out.println("\nTriángulo " + i + ":");
            System.out.print("Base: ");
            double base = sc.nextDouble();
            System.out.print("Altura: ");
            double altura = sc.nextDouble();

            if (asignarPrioridad) {
                System.out.print("Prioridad (1–10): ");
                int prioridad = sc.nextInt();
                hilos.add(new TrianguloThread(base, altura, prioridad));
            } else {
                hilos.add(new TrianguloThread(base, altura));
            }
        }

        System.out.println("\n--- Iniciando cálculos en paralelo ---");
        for (TrianguloThread hilo : hilos) {
            hilo.start();
        }

        // Esperar a que todos los hilos terminen
        for (TrianguloThread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nTodos los cálculos han finalizado.");
        sc.close();
    }
}
