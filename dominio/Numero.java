package dominio;

public enum Numero {
    CERO(0),
    UNO(1),
    DOS(2),
    TRES(3);

    private final int numero;

    Numero(int numero){
        this.numero =numero;
    }

    public int getNumero() {
        return numero;
    }
}
