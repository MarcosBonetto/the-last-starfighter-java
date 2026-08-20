package modelo;

public class spaceship_1 extends personaje_base{
    
    public spaceship_1(int x, int y, int width, int height,int life,int speed, int speedY, boolean herido){
        super( x, y, width, height, life, speed, speedY, herido);
	}

    @Override
    public void movimiento(){
    }

    @Override
    public misil Ataque(personaje_base jugador) {
        return new misil(getX() + getWidth() - 40, getY() + getHeight() / 2 - 22, 5, 5, 15);
    }


    
}
