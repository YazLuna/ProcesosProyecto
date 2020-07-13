package dominio;

public enum Genero {
    HOMBRE(1),
    MUJER(0);
    private final int genero;

    Genero(int genero) {
        this.genero= genero;
    }

    public int getGenero(){
        return genero;
    }
}
