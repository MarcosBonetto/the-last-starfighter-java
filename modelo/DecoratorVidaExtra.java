package modelo;

public class DecoratorVidaExtra extends DecoratorSpaceship {

    private int extras = 1;

    public DecoratorVidaExtra(spaceship_1 naveDecorada) {
        super(naveDecorada);
    }

    public void agregarVidaExtra() {
        extras++;
    }
    
    //Intercepta el daño
    @Override
    public void recibirDanio(int danio) {
        if (extras > 0) {
            extras--;                 
            return;                   
        }
        naveDecorada.recibirDanio(danio);
    }

    @Override
    public int getLife() {
        return naveDecorada.getLife() + extras;
    }

    // Mantener “seguir vivo” mientras exista una vida extra
    @Override
    public boolean estaVivo() {
        return (naveDecorada.estaVivo() || extras > 0);
    }
}
