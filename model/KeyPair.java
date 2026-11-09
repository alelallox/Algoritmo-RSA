package model;
/*
Rappresenta una coppia di chiavi RSA (pubblica e privata).
Questa classe è immutabile e contiene sia la chiave pubblica che quella
privata, generate insieme durante il processo di generazione RSA
*/

public final class KeyPair {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    
    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        if (publicKey == null || privateKey == null) {
            throw new IllegalArgumentException("Le chiavi non possono essere null");
        }
        if (!privateKey.matches(publicKey)) {
            throw new IllegalArgumentException("Le chiavi non appartengono allo stesso modulo n");
        }
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
    
    //Restituisce la chiave pubblica
    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    //Restituisce la chiave privata
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    
    
    @Override
    public String toString() {
        return "KeyPair{\n" + this.publicKey + ",\n" + this.privateKey + "\n}";
    }
}
