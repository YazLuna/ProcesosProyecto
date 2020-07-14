package dominio;

public class Docente extends Usuario{
    private String numeroPersonal;
    private String perfil;

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "numeroPersonal='" + numeroPersonal + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
