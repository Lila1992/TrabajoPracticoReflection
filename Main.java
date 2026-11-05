import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

// Clase Persona con atributos privados y métodos requeridos
class Persona {
    private String nombre;
    private int edad;

    // Constructores
    public Persona() {
        this.nombre = "Sin Nombre";
        this.edad = 0;
    }

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Método a inspeccionar / invocar
    public String saludar() {
        return "Hola, mi nombre es " + nombre + " y tengo " + edad + " años.";
    }

    public void mostrarInformacion() {
        System.out.println("Info Persona -> Nombre: " + nombre + " | Edad: " + edad);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("--- 1. OBTENER E IMPRIMIR CONSTRUCTORES VÍA REFLECTION ---");
            Class<?> clasePersona = Persona.class;
            Constructor<?>[] constructores = clasePersona.getDeclaredConstructors();
            
            for (Constructor<?> c : constructores) {
                System.out.println("Constructor hallado: " + c);
            }

            System.out.println("\n--- 2. INSTANCIAR UN OBJETO UTILIZANDO REFLECTION ---");
            Constructor<?> constructorParametrizado = clasePersona.getConstructor(String.class, int.class);
            Object objetoPersona = constructorParametrizado.newInstance("Shirley Torres", 32);
            System.out.println("Instancia creada dinámicamente con éxito.");

            System.out.println("\n--- 3. MODIFICAR EL ATRIBUTO PRIVADO 'nombre' EN TIEMPO DE EJECUCIÓN ---");
            Field campoNombre = clasePersona.getDeclaredField("nombre");
            campoNombre.setAccessible(true); // Romper / eludir el encapsulamiento privado
            System.out.println("Valor anterior de 'nombre': " + campoNombre.get(objetoPersona));
            
            campoNombre.set(objetoPersona, "Lila Torres (Modificado vía Reflection)");
            System.out.println("Nuevo valor inyectado en 'nombre': " + campoNombre.get(objetoPersona));

            System.out.println("\n--- 4. INVOCAR MÉTODOS DINÁMICAMENTE ---");
            Method metodoMostrarInfo = clasePersona.getDeclaredMethod("mostrarInformacion");
            metodoMostrarInfo.invoke(objetoPersona);

            Method metodoSaludar = clasePersona.getDeclaredMethod("saludar");
            Object resultadoSaludo = metodoSaludar.invoke(objetoPersona);
            System.out.println("Ejecución de saludar(): " + resultadoSaludo);

            System.out.println("\n--- 5. MANIPULACIÓN DE ACCESIBILIDAD Y METADATOS ---");
            System.out.println("Accesibilidad actual de saludar(): " + metodoSaludar.canAccess(objetoPersona));
            metodoSaludar.setAccessible(false);
            System.out.println("Se restableció la restricción de encapsulamiento mediante Reflection.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}