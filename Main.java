import generator.KeyGenerator;
import model.KeyPair;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Generatore di Chiavi RSA ===\n");
        
        // Crea un generatore di chiavi
        KeyGenerator keyGenerator = new KeyGenerator();
        
        // Genera una coppia di chiavi
        System.out.println("Generazione coppia di chiavi in corso...\n");
        KeyPair keyPair = keyGenerator.generateKeyPair();
        
        // Mostra le chiavi generate
        System.out.println("\n=== CHIAVI GENERATE ===");
        System.out.println(keyPair);
        
        // Verifica che le chiavi siano valide
        System.out.println("\n=== VERIFICA CHIAVI ===");
        boolean isValid = keyGenerator.verifyKeyPair(keyPair);
        System.out.println("Le chiavi sono valide: " + (isValid ? "SI" : "NO"));
    

    }
}
