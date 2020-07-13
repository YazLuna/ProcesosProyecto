package dominio;

public class Unidad {
    private String nombreUnidad;
    private String temas;
    private String fechas;
    private String tareasPracticas;
    private String porcentajeAvance;

    Unidad(){

    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getTemas () {
        return temas;
    }

    public void setTemas (String temas) {
        this.temas = temas;
    }

    public String getFechas () {
        return fechas;
    }

    public void setFechas (String fechas) {
        this.fechas = fechas;
    }

    public String getTareasPracticas () {
        return tareasPracticas;
    }

    public void setTareasPracticas (String tareasPracticas) {
        this.tareasPracticas = tareasPracticas;
    }
}
