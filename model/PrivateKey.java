package model;

import java.math.BigInteger;

/*
Rappresenta una chiave privata RSA. La chiave è composta da:
- n: modulo (stesso della chiave pubblica)
- d: esponente privato (inverso modulare di e)
Questa classe è immutabile per garantire la sicurezza della chiave
*/
public final class PrivateKey {
    private final long n;
    private final long d;
     
    public PrivateKey(long n, long d) {
        if (n <= 0) {
            throw new IllegalArgumentException("Il modulo n deve essere positivo");
        }
        if (d <= 1) {
            throw new IllegalArgumentException("L'esponente d deve essere maggiore di 1");
        }
        this.n = n;
        this.d = d;
    }
    
    /*
    Esegue la decifratura RSA usando BigInteger solo internamente 
    per prevenire l'overflow del calcolo della potenza intermediaria.
    */
    public long decrypt(long text) {
        if (text < 0) {
            throw new IllegalArgumentException("Il messaggio cifrato non può essere negativo");
        }
        BigInteger bigCipher = BigInteger.valueOf(text);
        BigInteger bigD = BigInteger.valueOf(this.d);
        BigInteger bigN = BigInteger.valueOf(this.n);
        
        return bigCipher.modPow(bigD, bigN).longValue();
    }
    
    // verifica il modulo 
    public boolean matches(PublicKey publicKey) {
        if (publicKey == null){
            return false;
        }
        return this.n == publicKey.getN();
    }
   

    /*
    Il metodo toString è l'unica falla di sicurezza che espone 'd'. E' presente con il solo
    fine dimostrativo per la classe main. 
    */
    @Override
    public String toString() {
        return "PrivateKey(n= " + this.n + ", d= " + this.d + ")";
    }
}
