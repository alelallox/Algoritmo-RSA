package model;

/*
Rappresenta una chiave pubblica RSA. La chiave è composta da:
- n: modulo (prodotto di due primi p e q)
- e: esponente pubblico (di solito 65537)
 Questa classe è immutabile per garantire la sicurezza della chiave
 */
public final class PublicKey {
    private final long n;
    private final long e;
    
    public PublicKey(long n, long e) {
        if (n <= 0) {
            throw new IllegalArgumentException("Il modulo n deve essere positivo");
        }
        if (e <= 1) {
            throw new IllegalArgumentException("L'esponente e deve essere maggiore di 1");
        }
        this.n = n;
        this.e = e;
    }
    
    //Restituisce il modulo n
    public long getN() {
        return this.n;
    }
    
    //Restituisce l'esponente pubblico e
    public long getE() {
        return this.e;
    }
    
    @Override
    public String toString() {
        return "PublicKey(n= " + this.n + ", e= " + this.e + ")";
    }
}
