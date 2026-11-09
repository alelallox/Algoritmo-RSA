package generator;

import java.security.SecureRandom;

/*
Generatore di numeri primi per l'algoritmo RSA.
Utilizza un test di primalità deterministico per verificare se un numero è primo
*/
public class PrimeNumberGenerator {
    private final SecureRandom random;
    
    public PrimeNumberGenerator() {
        this.random = new SecureRandom();
    }
    
    public PrimeNumberGenerator(SecureRandom random) {
        this.random = random;
    }
    
    /*
    Genera un numero primo casuale nell'intervallo specificato. 
    minValue è il valore minimo (incluso)
    maxValue è il valore massimo (escluso)
    restituisce un numero primo nell'intervallo [minValue, maxValue)
    */
    public long generatePrime(int minValue, int maxValue) {
        if (minValue < 2) {
            throw new IllegalArgumentException("Il valore minimo deve essere almeno 2");
        }
        if (maxValue <= minValue) {
            throw new IllegalArgumentException("Il valore massimo deve essere maggiore del minimo");
        }
        
        long candidato;
        int maxTentativi = 10000;
        int tentativi = 0;
        long range = (long) maxValue - minValue;
        
        // Genera un numero casuale nell'intervallo
        do {
            /* 
            Genera un numero intero a 64 bit pseudo-casuale (tramite random.nextLong()). 
            Applica Math.abs() per renderlo sicuramente positivo e usa l'operatore modulo 
            (% range) per ridurlo a un valore compreso strettamente tra 0 e range - 1
            */
            long offset = Math.abs(random.nextLong()) % range;
            //calcolo il candidato sommando l'offset 
            candidato = minValue + offset;
            
            // Se il numero estratto è pari, lo rendiamo dispari in modo sicuro nel range
            if (candidato > 2 && candidato % 2 == 0) {
                if (candidato < maxValue - 1) {
                    candidato++;
                } else {
                    candidato--;
                }
            }
            
            tentativi++;
            if (tentativi > maxTentativi) {
                throw new RuntimeException("Impossibile trovare un numero primo dopo " + maxTentativi + " tentativi");
            }
            
        } while (!isPrime(candidato));
        
        return candidato;
    }
    
    /*
    Verifica se un numero è primo usando un test deterministico.
    Questo metodo divide il candidato per tutti i numeri dispari fino a √n.
    n è il numero da testare e il metodo restituisce true se n è primo, false altrimenti
    */
    public boolean isPrime(long n) {
        //casi base
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        /*
        Controlla divisori della forma 6k ± 1 fino a √n. Questo è più efficiente che controllare 
        tutti i numeri dispari: matematicamente, se un numero n ha un divisore, questo deve essere 
        necessariamente minore o uguale alla sua radice quadrata (√n). Se non trovi nessun divisore 
        entro la √n, è matematicamente garantito che non ne troverai nessuno oltre. 
        */
        long sqrt = (long) Math.sqrt(n);
        for (long i = 5; i <= sqrt; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /*
    Genera due numeri primi distinti nell'intervallo specificato.
    minValue è il valore minimo (incluso)
    maxValue è il valore massimo (escluso)
    restituisce un array di due primi distinti
    */
    public long[] generatePrimes(int minValue, int maxValue) {
        long p = generatePrime(minValue, maxValue);
        long q;

        //p e q devono essere diversi
        do {
            q = generatePrime(minValue, maxValue);
        } while (p == q);
        
        return new long[]{p, q};
    }
}
