package modelo;

public class misilTeledirigido extends misil {
    
    private double velocidadX;
    private double velocidadY; 

    
    public misilTeledirigido(int x, int y, int width, int height, int speed, int targetX, int targetY) {
        super(x, y, width, height, speed);
        
        double deltaX = targetX - getX();
        double deltaY = targetY - getY();

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        
        if (distancia > 0) {
            this.velocidadX = (deltaX / distancia) * getSpeed();
            this.velocidadY = (deltaY / distancia) * getSpeed();
        } else {
            this.velocidadX = -getSpeed(); 
            this.velocidadY = 0;
        }
    }

    @Override
    public void MovimientoMisil() {
        // Se mueve en su dirección calculada
        setX(getX() + (int)Math.round(this.velocidadX));
        setY(getY() + (int)Math.round(this.velocidadY));
    }
}