package modelo;
// Primer tipo de enemigo: se mueve solo en x y misil apunta a la ultima posicion de la nave
public class Enemigo_Violeta extends personaje_base {
        public Enemigo_Violeta(int x, int y, int width, int height,int life,int speed,int speedY,boolean herido){
        super(x, y, width, height, life, speed, speedY,herido);

    }
    @Override
    public void movimiento(){

        setX(getX() - getSpeed());
    }
    @Override
    public misil Ataque(personaje_base jugador){
        // Solo dispara si está a la derecha del jugador
        if (getX() > jugador.getX()) {
            int objetivoX = jugador.getX() + (jugador.getWidth() / 2);
            int objetivoY = jugador.getY() + (jugador.getHeight() / 2);
            misil m = new misilTeledirigido(
                getX(),
                getY() + getHeight() / 2 - 5,
                10, 10,
                12, // velocidad del misil
                objetivoX,
                objetivoY
                );

                m.setDamage(2);
                return m;          
    }
    return null; // no dispara si ya pasó del jugador
}

    @Override
    public int getPuntos(){
        return 500;
    }
    @Override
    public String getTipoEnemigo(){
        return "violeta";
    };
}
