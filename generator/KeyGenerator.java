package generator;
import java.math.BigInteger;
import model.KeyPair;
import model.PrivateKey;
import model.PublicKey;

/*
Generatore di coppie di chiavi RSA. Implementa l'algoritmo completo di 
generazione delle chiavi:
1. Genera due numeri primi p e q
2. Calcola n = p * q
3. Calcola φ(n) = (p-1) * (q-1)
4. Sceglie e coprimo con φ(n)
5. Calcola d = e^(-1) mod φ(n)
6. Restituisce la coppia (PublicKey, PrivateKey)
*/
public class KeyGenerator {
    private final PrimeNumberGenerator primeGenerator;

    //Esponente pubblico standard (2^16 + 1), molto usato in RSA reale
    private static final long DEFAULT_PUBLIC_EXPONENT = 65537;
    
    public KeyGenerator() {
        this.primeGenerator = new PrimeNumberGenerator();
    }
    
    //Costruisce un nuovo generatore con un PrimeNumberGenerator specifico
    public KeyGenerator(PrimeNumberGenerator primeGenerator) {
        this.primeGenerator = primeGenerator;
    }
    
    /*
    Genera una coppia di chiavi RSA con intervallo predefinito per i primi.
    Usa primi nell'intervallo [100_000_000, 1_000_000_000] per poter restare nei limiti di spazio long.
     */
    public KeyPair generateKeyPair() {
        return generateKeyPair(100_000_000, 1_000_000_000);
    }
    
    /*
    Genera una coppia di chiavi RSA specificando l'intervallo per i numeri primi.
    minPrime è il valore minimo per i primi p e q
    maxPrime è il valore massimo per i primi p e q
    */
    public KeyPair generateKeyPair(int minPrime, int maxPrime) {

         // Step 1: Genera due primi distinti p e q
        long[] primes = primeGenerator.generatePrimes(minPrime, maxPrime);
    
        long p = primes[0];
        long q = primes[1];
        
        System.out.println("Primi generati: p = " + p + ", q = " + q);
        
        // Step 2: Calcola n = p * q (modulo)
        long n = p * q;
        System.out.println("Modulo n = " + n);
        
        // Step 3: Calcola φ(n) = (p-1) * (q-1)
        long phi = (p - 1) * (q - 1);
        System.out.println("φ(n) = " + phi);
        
        // Step 4: Scegli 'e' (esponente pubblico) coprimo con φ(n)
        long e = choosePublicExponent(phi);
        System.out.println("Esponente pubblico e = " + e);
        
        // Step 5: Calcola 'd' (esponente privato)
        long d = MathUtils.InversoModulare(e, phi);
        System.out.println("Esponente privato d = " + d);
        
        // Step 6: Crea e restituisci le chiavi
        PublicKey publicKey = new PublicKey(n, e);
        PrivateKey privateKey = new PrivateKey(n, d);
        
        return new KeyPair(publicKey, privateKey);
    }
    
    /*
    Sceglie un esponente pubblico e coprimo con φ(n). Prova prima con il valore standard 65537, 
    altrimenti cerca un altro valore.
    phi è il valore di φ(n)e il metodo restituisce un esponente pubblico valido
    */
    private long choosePublicExponent(long phi) {
        // Prova prima con l'esponente standard
        if (DEFAULT_PUBLIC_EXPONENT < phi && MathUtils.areCoprime(DEFAULT_PUBLIC_EXPONENT, phi)) {
            return DEFAULT_PUBLIC_EXPONENT;
        }
        
        /*
        Altrimenti cerca un valore dispari coprimo con phi. Inizia da 3 e incrementa di 2 
        (solo numeri dispari)
        */
        for (long e = 3; e < phi; e += 2) {
            if (MathUtils.areCoprime(e, phi)) {
                return e;
            }
        }
        
        throw new RuntimeException("Impossibile trovare un esponente pubblico valido");
    }
    
    /*
    Verifica se una coppia di chiavi è valida testando che:
    (m^e)^d ≡ m (mod n) per un messaggio di test m.
    keyPair è la coppia di chiavi da verificare e il metodo restituisce true se la coppia è valida,
    false altrimenti
    */
    public boolean verifyKeyPair(KeyPair keyPair) {
        PublicKey pub = keyPair.getPublicKey();
        PrivateKey priv = keyPair.getPrivateKey();
        
        // Messaggio di test (long)
        long m = 42;
        
        // Cifratura temporanea: m^e mod n usando BigInteger a livello locale
        BigInteger bigM = BigInteger.valueOf(m);
        BigInteger bigE = BigInteger.valueOf(pub.getE());
        BigInteger bigN = BigInteger.valueOf(pub.getN());
        long cifrato= bigM.modPow(bigE, bigN).longValue();
        
        // Decifratura delegata alla chiave privata (accetta long, restituisce long)
        long decifrato = priv.decrypt(cifrato);
        
        return m == decifrato;
    }
}
