package dominio;

public class Unidad {
    private String nombreUnidad;
    private String temas;
    private String fechas;
    private String tareasPracticas;
    private int porcentajeAvance;

    public Unidad(){

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

    public int getPorcentajeAvance () {
        return  porcentajeAvance;
    }

    public void setPorcentajeAvance (int porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }
}
