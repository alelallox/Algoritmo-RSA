## Generatore di chiavi RSA

Programma didattico in Java che genera una coppia di chiavi RSA, mostra i passaggi del calcolo nel terminale e verifica la coppia cifrando e decifrando un messaggio di prova.

## Come funziona

1. Estrae con `SecureRandom` due numeri primi distinti, `p` e `q`, tra 100 milioni inclusi e un miliardo escluso. Il test di primalità verifica i possibili divisori fino alla radice quadrata del candidato.
2. Calcola il modulo `n = p × q` e il totiente `φ(n) = (p − 1) × (q − 1)`.
3. Sceglie l'esponente pubblico `e`. Usa `65537` se è minore di `φ(n)` e coprimo con esso, altrimenti cerca un esponente dispari che soddisfi queste condizioni a partire da `3`.
4. Calcola l'esponente privato `d` come inverso modulare di `e` rispetto a `φ(n)`, usando l'algoritmo di Euclide esteso. La chiave pubblica è `(n, e)` e la chiave privata è `(n, d)`.
5. Cifra il valore di prova `42` con la chiave pubblica e lo decifra con quella privata. Entrambe le operazioni usano `BigInteger.modPow` per l'elevamento a potenza modulare. La verifica riesce se il valore ottenuto è di nuovo `42`.

## Struttura del progetto

| File | Responsabilità |
| --- | --- |
| `Main.java` | Avvia il programma e mostra le chiavi e l'esito della verifica. |
| `generator/KeyGenerator.java` | Genera la coppia RSA e verifica che il messaggio di prova venga recuperato. |
| `generator/PrimeNumberGenerator.java` | Estrae i candidati e ne verifica la primalità. |
| `generator/MathUtils.java` | Calcola il massimo comune divisore e l'inverso modulare. |
| `model/PublicKey.java` | Rappresenta la chiave pubblica. |
| `model/PrivateKey.java` | Rappresenta la chiave privata ed esegue la decifratura. |
| `model/KeyPair.java` | Raggruppa le chiavi e controlla che condividano lo stesso modulo. |

## Limiti della dimostrazione

Il progetto è destinato allo studio di RSA. Il modulo è memorizzato in un `long` e, con l'intervallo predefinito, ha al massimo 60 bit. Il programma mostra nel terminale i fattori primi e l'esponente privato. La verifica applica RSA direttamente a un singolo numero, senza padding, quindi le chiavi generate sono adatte solo alla dimostrazione.
