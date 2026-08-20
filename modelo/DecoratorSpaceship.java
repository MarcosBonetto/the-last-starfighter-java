package modelo;

import java.awt.Rectangle;

public abstract class DecoratorSpaceship extends spaceship_1 {
    
    protected spaceship_1 naveDecorada;

    public DecoratorSpaceship(spaceship_1 naveDecorada) {
        super(naveDecorada.getX(),naveDecorada.getY(), 
              naveDecorada.getWidth(),naveDecorada.getHeight(), 
              naveDecorada.getLife(),naveDecorada.getSpeed(), 
              naveDecorada.getSpeedY(), naveDecorada.getHerido());
         this.naveDecorada = naveDecorada;
    }

   // @Override
    //public misil Ataque() {
    //    return naveDecorada.Ataque();
    //}

    @Override
    public int getX(){ 
        return naveDecorada.getX(); 
    }
    @Override
	public int getY(){ 
        return naveDecorada.getY(); 
    }
    @Override
    public int getLife(){ 
        return naveDecorada.getLife(); 
    }
    @Override
    public int getSpeed(){ 
        return naveDecorada.getSpeed(); 
    }
    @Override
    public int getSpeedY(){ 
        return naveDecorada.getSpeedY(); 
    }
    @Override
    public int getWidth(){ 
        return naveDecorada.getWidth(); }
    @Override
	public int getHeight(){ 
        return naveDecorada.getHeight(); 
    }
    @Override
    public void setX(int x){ 
        naveDecorada.setX(x); 
    }
    @Override
	public void setY(int y){ 
        naveDecorada.setY(y); 
    }
    @Override
	public void setLife(int life){ 
        naveDecorada.setLife(life); 
    }
    @Override
    public void setSpeed(int speed){ 
        naveDecorada.setSpeed(speed);
    }
    @Override
    public void setSpeedY(int speedY){ 
        naveDecorada.setSpeedY(speedY); 
    }
    @Override
	public void setHeight(int height){ 
        naveDecorada.setHeight(height); 
    }
    @Override
	public void setWidth(int width){ 
        naveDecorada.setWidth(width); 
    }
    @Override
    public Rectangle getBounds(){ 
        return naveDecorada.getBounds(); 
    }
    @Override
    public boolean estaVivo(){ 
        return naveDecorada.estaVivo(); 
    }
    @Override
    public void recibirDanio(int danio){ 
        naveDecorada.recibirDanio(danio); 
    }

    @Override
    public boolean estaHerido() {
    return naveDecorada.estaHerido();
}

public spaceship_1 getNaveDecorada() {
    return naveDecorada;
}


}