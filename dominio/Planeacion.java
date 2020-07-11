package dominio;

public class Planeacion {
    private String unidad;
    private String temas;
    private String fechas;
    private String tareasPracticas;

    Planeacion(){

    }

    public String getUnidad () {
        return unidad;
    }

    public void setUnidad (String unidad) {
        this.unidad = unidad;
    }

    public String getTemas () {
        return temas;
    }

    public void setTemas (String temas) {
        this.temas = temas;
    }

    public String getFechas () {
        return temas;
    }

    public void setFechas (String fechas) {
        this.fechas = fechas;
    }

    public String getTareasPracticas (String tareasPracticas) {
        return tareasPracticas;
    }
}
