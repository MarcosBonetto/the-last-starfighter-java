package modelo;
import java.awt.Rectangle;
public abstract class personaje_base {

private int x, y; //posicion
private int width, height;//dimensiones
private int life;
private int speed;
private int speedY;
private boolean herido;
private long tiempoHerida;

	
	public personaje_base(int x, int y, int width, int height,int life,int speed,int speedY, boolean herido) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
        this.life = life;
        this.speed = speed;
	    this.speedY = speedY;
        this.herido = herido;
    }

    public int getX(){
        return this.x;
    }
	public int getY(){
        return this.y;
    }
    public int getLife(){
        return this.life;
    } 
    public int getSpeed(){
        return this.speed;
    }
    public int getSpeedY(){
        return this.speedY;
    }   
    public boolean getHerido(){
        return this.herido;
    }

    public void setHerido(boolean herido){
        this.herido = herido;
    }
    public void setX(int x){ 
        this.x = x;
    }
	public void setY(int y){
        this.y = y;
    }
	public void setLife(int life){
        this.life = life;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }
    public int getWidth(){
        return this.width;
    }
	public int getHeight(){
        return this.height;
    }
	public void setHeight(int height){
        this.height = height;
    }
	public void setWidth(int width){
        this.width = width;
    }

    public Rectangle getBounds(){
        int paddingX = 50;
        int paddingY = 50;
        return new Rectangle(x, y, paddingX * 2, paddingY * 2);

    }

    public boolean estaVivo() {
        return this.life > 0;
    }

    public void recibirDanio(int danio) { 
        this.life -= danio;
        herido = true;
        tiempoHerida = System.currentTimeMillis();//Guarda cuando fue impactado
    }

    public boolean estaHerido() {
        if (herido && (System.currentTimeMillis() - tiempoHerida > 150)){
            herido = false;
        }
        return herido; // efecto de la herida dura 150ms
    }

    public abstract void movimiento();

    public abstract misil Ataque(personaje_base jugador);
    
    public int getPuntos(){
        return 100;
    }

    public  String getTipoEnemigo(){
        return "Ninguno";
    };

}
