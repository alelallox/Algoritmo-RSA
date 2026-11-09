package generator;

/*
Classe di utilità per operazioni matematiche necessarie all'algoritmo RSA.
Fornisce implementazioni per:
- Massimo Comun Divisore (MCD)
- Inverso modulare usando l'algoritmo di Euclide esteso
*/
public final class MathUtils {
    
    //Classe di utilità, quindi non deve essere istanziata
    private MathUtils() {
        throw new UnsupportedOperationException("Classe di utilità - non istanziabile");
    }
    
    //Calcola il Massimo Comun Divisore (MCD) di due numeri usando l'algoritmo di Euclide.
    public static long mcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    /*
    Calcola l'inverso modulare di 'a' modulo 'm' usando l'algoritmo di Euclide esteso.
    In altre parole, trova x tale che (a * x) ≡ 1 (mod m)
    'a' è il numero di cui calcolare l'inverso e 'm' è il modulo
    usiamo l'algoritmo di Euclide esteso dove oltre atrovae l'MCD tra due numeri, calcola
    anche due coeffcienti che permettono di scrivere l'MCD come combinazione lineare dei numeri 
    di partenza 
    */
    public static long InversoModulare(long a, long m) {
        if (m <= 0) {
            throw new IllegalArgumentException("Il modulo deve essere positivo");
        }
        
        long m0 = m;
        //due coefficienti servono a tenere traccia dei calcoli intermedi per trovare l'inverso 
        // combinando linearmente i resti ottenuti.
        long x0 = 0;
        long x1 = 1;
        
        if (m == 1) {
            return 0;
        }
        
        // Algoritmo di Euclide esteso
        while (a > 1) {
            long q = a / m;
            long temp = m;
            
            m = a % m;
            a = temp;
            temp = x0;
            
            x0 = x1 - q * x0;
            x1 = temp;
        }
        
        if (x1 < 0) {
            x1 += m0;
        }
        
        return x1;
    }
    
    /*
    Verifica se due numeri sono coprimi (il loro MCD è 1).
    restituisce true se a e b sono coprimi, false altrimenti
    */
    public static boolean areCoprime(long a, long b) {
        return mcd(a, b) == 1;
    }
}
