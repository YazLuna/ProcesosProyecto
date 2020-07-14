package logica;

import dominio.Numero;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionGeneral {

    public String eliminarEspacios (String palabras) {
        if(palabras.length()>0) {
            palabras = palabras.replaceAll("\\s+", " ");
            if (palabras.charAt(palabras.length() - 1) == ' ') {
                palabras = palabras.substring(0, palabras.length() - 1);
            }
        }
        return palabras;
    }

    public boolean validarPalabra (String palabras) {
        boolean esValido;
        if(palabras.length()!= Numero.CERO.getNumero()){
            esValido=true;
        }else{
            esValido=false;
        }
        return esValido;
    }

    public boolean validarCorreo (String correo) {
        boolean esValidoCorreo;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);
        esValidoCorreo = mather.find();
        return esValidoCorreo;
    }

    public boolean validarNombre (String nombre) {
        boolean esValidoNombre;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(nombre);
        esValidoNombre = mather.find();
        return esValidoNombre;
    }

    public boolean validarTelefono (String telefono) {
        boolean esValidoTelefono;
        Pattern pattern = Pattern
                .compile("[0-9]{10}");
        Matcher mather = pattern.matcher(telefono);
        esValidoTelefono = mather.find();
        return esValidoTelefono;
    }

    public boolean validarContrasenia (String contrasenia) {
        Pattern pattern = Pattern
                .compile("[A-Za-z0-9]{10,20}");
        Matcher mather = pattern.matcher(contrasenia);
        boolean esValidoContrasenia = mather.find();
        return esValidoContrasenia;
    }

    public boolean validarApellidos (String apellidos) {
        boolean esValidoApellidos;
        Pattern patternName = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = patternName.matcher(apellidos);
        esValidoApellidos = mather.find();
        return esValidoApellidos;
    }

    public boolean validarRFC (String rfc){
        boolean isValidoRFC;
        rfc=rfc.toUpperCase().trim();
        isValidoRFC = rfc.toUpperCase().matches("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\\d]{3})?$");
        if(isValidoRFC){
            Calendar fechaActual = Calendar.getInstance();
            int anioActual = fechaActual.get(Calendar.YEAR);
            String anioActualCad = Integer. toString(anioActual);
            String anioRFC = anioActualCad.charAt(2)+""+anioActualCad.charAt(3);
            int anioRFCActual = Integer.parseInt(anioRFC);

            String anio = rfc.charAt(4) +""+rfc.charAt(5);
            int anioRFCIngresado = Integer.parseInt(anio);

            int limiteAnio = anioActual-89;
            String anioLimite = Integer. toString(limiteAnio);
            String aniolimiteRFCCad = anioLimite.charAt(2)+""+anioLimite.charAt(3);
            int anioLimiteRFC = Integer.parseInt(aniolimiteRFCCad);

            if(anioRFCIngresado<anioRFCActual || anioRFCIngresado>anioLimiteRFC){
                String mes = rfc.charAt(6) +""+rfc.charAt(7);
                String dia = rfc.charAt(8) +""+rfc.charAt(9);
                int diaRFC = Integer.parseInt(dia);
                if((mes.equals("04")||mes.equals("06")||mes.equals("09")||mes.equals("11")) && diaRFC<31 ){
                    isValidoRFC=true;
                }else{
                    if(mes.equals("02") && diaRFC<30){
                        double promedioAnio = anioRFCIngresado%2;
                        if(promedioAnio!=0 && diaRFC<=28){
                            isValidoRFC=true;
                        }else {
                            if(promedioAnio==0 && diaRFC<=29){
                                isValidoRFC=true;
                            }else {
                                isValidoRFC = false;
                            }
                        }
                    }else {
                        if(mes.equals("01")||mes.equals("03")||mes.equals("05")||mes.equals("07")||
                                mes.equals("08")||mes.equals("10")||mes.equals("12")){
                            isValidoRFC=true;
                        }else {
                            isValidoRFC = false;
                        }
                    }
                }
            }else {
                isValidoRFC=false;
            }
        }
        return isValidoRFC;
    }

    public boolean validarAño (String fecha) {
        Calendar fechaActual = Calendar.getInstance();
        boolean esValidoFecha=true;
        String[] fechaNacimiento = fecha.split("/");
        int anio = Integer.parseInt(fechaNacimiento[2]);
        int anioActual = fechaActual.get(Calendar.YEAR);
        int numeroAnios = anioActual-anio;
        if(numeroAnios<18 || numeroAnios>89){
            esValidoFecha=false;
        }
        return esValidoFecha;
    }

    public boolean validarConcidenciaRFC (String rfc, String fecha){
        boolean esValidoConcidencia=true;
        String[] fechaNacimiento = fecha.split("/");
        String diaFecha = fechaNacimiento[0];
        String mesFecha = fechaNacimiento[1];
        String anioFecha = fechaNacimiento[2];
        anioFecha = anioFecha.charAt(2)+""+anioFecha.charAt(3);
        String anioRFC = rfc.charAt(4) +""+rfc.charAt(5);
        String mesRFC = rfc.charAt(6) +""+rfc.charAt(7);
        String diaRFC = rfc.charAt(8) +""+rfc.charAt(9);
        if(!anioFecha.equals(anioRFC) || !mesFecha.equals(mesRFC) || !diaFecha.equals(diaRFC)){
            esValidoConcidencia=false;
        }
        return  esValidoConcidencia;
    }
}
