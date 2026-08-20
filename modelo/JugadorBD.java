package modelo;

public class JugadorBD {
    private final String nombre;
    private final int puntaje;

    public JugadorBD (String nombre, int puntaje){
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPuntaje(){
        return puntaje;
    }
}
