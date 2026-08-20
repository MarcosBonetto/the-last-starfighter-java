package modelo;

import java.awt.Rectangle; 
public class misil {

    private int x,y;
    private int width;
    private int height;
    private int speed;
    private int damage;

    public misil(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = 1; // valor por defecto
    }

    // Overload con daño explícito
    public misil(int x, int y, int width, int height, int speed, int damage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
    }

    public int getX() {
        return x;
    }   
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getSpeed() {
        return speed;
    }
    public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
    public boolean colision(personaje_base otro){
        return this.getBounds().intersects(otro.getBounds());
    }
    public void MovimientoMisil(){
        this.x += speed;     
    }

    public int getDamage(){
        return this.damage;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }


}
