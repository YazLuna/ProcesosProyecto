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
        return isValidoRFC;
    }

    public boolean validarAño (String fecha) {
        Calendar fechaActual = Calendar.getInstance();
        boolean isValidoFecha=true;
        String[] fechaNacimiento = fecha.split("/");
        int anio = Integer.parseInt(fechaNacimiento[2]);
        int anioActual = fechaActual.get(Calendar.YEAR);
        int numeroAnios = anioActual-anio;
        if(numeroAnios<18){
            isValidoFecha=false;
        }
        System.out.println(anioActual +" "+anio);
        return isValidoFecha;
    }
}
