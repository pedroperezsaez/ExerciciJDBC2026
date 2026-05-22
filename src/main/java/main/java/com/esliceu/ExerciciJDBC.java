package main.java.com.esliceu;

import java.sql.*;
import java.util.Scanner;

public class ExerciciJDBC {

    public static void main(String[] args) {
        try {
            System.out.println("--- Inici de l'exercici JDBC ---");

            // 1. Connexió
            Connection conn = connectar();
            if (conn == null) {
                System.out.println("No s'ha pogut establir la connexió. Revisa el mètode connectar().");
                return;
            }
            
            System.out.println("Connexió establerta.");

            // 2. Consulta complexa amb múltiples JOINS
            llistarLlibresAmbDetalls(conn);

            // 3. Inserció amb recuperació de clau generada
            int nouAutorId = afegirNouAutor(conn, "George Orwell", "Britànica");
            if (nouAutorId != -1) {
                System.out.println("Nou autor creat amb ID: " + nouAutorId);
            }

            // 4. Seguretat: SQL Injection (Exemple interactiu)
            vulnerabilitatSQLInjection(conn);

            // 5. Transacció complexa: Préstec de llibre
            realitzarPrestec(conn, 1, 2); // Llibre 1, Soci 2

            conn.close();
            System.out.println("\n--- Exercici finalitzat ---");

        } catch (SQLException e) {
            gestionarErrorSQL(e);
        }
    }

    /**
     * TODO: Implementar la connexió a la base de dades.
     * Ha d'utilitzar les constants de la classe Configuracio.
     */
    public static Connection connectar() throws SQLException {
        // ESCRIU AQUÍ EL TEU CODI
        return DriverManager.getConnection(
                Configuracio.URL,
                Configuracio.USUARI,
                Configuracio.CONTRASENYA
        );
    }
    /**
     * TODO: Consulta complexa amb JOINS.
     * Ha de mostrar: Títol del llibre, Categoria, Nom de l'autor (un o diversos) i Disponibilitat.
     * Cal ajuntar: llibres, categories, llibres_autors i autors.
     */
    public static void llistarLlibresAmbDetalls(Connection conn) {
        System.out.println("\n--- LLISTAT DETALLAT DE LLIBRES ---");
        // Recorda que un llibre pot tenir diversos autors (relació molts-a-molts)
        String sql = ""; // ESCRIU LA CONSULTA AQUÍ

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // TODO: Recórrer el ResultSet i mostrar la informació formatada
            
        } catch (SQLException e) {
            gestionarErrorSQL(e);
        }
    }

    /**
     * TODO: Inserir un nou autor i retornar l'ID generat pel sistema.
     * Cal utilitzar PreparedStatement i l'opció Statement.RETURN_GENERATED_KEYS.
     */
    public static int afegirNouAutor(Connection conn, String nom, String nacionalitat) throws SQLException {
        String sql = "INSERT INTO autors (nom, nacionalitat) VALUES (?, ?)";
        // TODO: Implementar inserció i recuperació de l'ID generat
        return -1;
    }

    /**
     * TODO: Exercici d'injecció SQL.
     * 1. Demanar l'email per teclat.
     * 2. Fer una cerca vulnerable concatenant strings.
     * 3. Fer la mateixa cerca segura amb PreparedStatement.
     */
    public static void vulnerabilitatSQLInjection(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- PROVA D'INJECCIÓ SQL ---");
        System.out.print("Introdueix l'email d'un soci per veure les seves dades: ");
        String email = sc.nextLine();

        // [1] Cas vulnerable (Statement concatenant strings)
        System.out.println("\n[1] Intentant cerca vulnerable...");
        // TODO: Implementar cerca vulnerable

        // [2] Cas segur (PreparedStatement)
        System.out.println("\n[2] Intentant cerca segura...");
        // TODO: Implementar cerca segura
    }

    /**
     * TODO: Realitzar un préstec com una transacció atòmica.
     * Passos obligatoris:
     * 1. Comprovar si el llibre està disponible (SELECT disponible FROM llibres WHERE id = ?).
     * 2. Si està disponible, desactivar autocommit.
     * 3. Insertar a la taula 'prestecs'.
     * 4. Update 'llibres' set disponible = FALSE.
     * 5. Fer COMMIT.
     * 6. En cas de qualsevol error, fer ROLLBACK.
     * 7. Finalment, tornar a activar l'autocommit.
     */
    public static void realitzarPrestec(Connection conn, int llibreId, int sociId) {
        System.out.println("\n--- PROCESSANT PRÈSTEC ---");
        
     /*   try {
            // TODO: Implementar lògica de transacció

        } catch (SQLException e) {
            System.err.println("Error en la transacció. Realitzant rollback...");
            // TODO: Rollback
            gestionarErrorSQL(e);
        }

      */
    }

    /**
     * Helper per gestionar errors SQL de forma centralitzada.
     */
    private static void gestionarErrorSQL(SQLException e) {
        System.err.println("\n!!! ERROR SQL !!!");
        System.err.println("Missatge: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Codi d'error: " + e.getErrorCode());
        // e.printStackTrace(); // Descomentar per a depuració profunda
    }
}
