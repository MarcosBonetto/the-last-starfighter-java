    package modelo;
    import java.util.Random;
    // Segundo tipo de enemigo: se mueve en ZIGZAG y misil con movimiento horizontal
    public class Enemigo_Rojo extends personaje_base {

        private Random random;
        private int tCambio; // tiempo hasta el próximo cambio de dirección.
        private int contadort = 0;
        private final int boundsHeight = 800; // Altura del área de juego.
        public Enemigo_Rojo(int x, int y, int width, int height, int life, int speed,int speedY ,boolean herido) {
            super(x, y, width, height, life, speed, speedY, herido);
            // usar el campo heredado en personaje_base
            setSpeedY(speedY);
            this.random = new Random();
            this.tCambio = random.nextInt(101) + 50; // inicializar tiempo hasta cambio
        }


        @Override
        public void movimiento() {
            contadort++;

            if (contadort >= tCambio || getY() <= 0 || getY() >= (boundsHeight - getHeight())) {
                setSpeedY(-getSpeedY());//invierte velocidad en Y
                this.tCambio = random.nextInt(101) + 50; //  tiempo entre 50 y 150 ms
                this.contadort = 0;
            }
            setX(getX() - getSpeed());
            setY(getY() + getSpeedY());
            
            
        }

        @Override
        public misil Ataque(personaje_base jugador){// misiles del enemigo con movimiento horizontal
            misil m = new misil(getX(), getY() + getHeight() / 2 - 5, 10, 10, -15);
            m.setDamage(1);
            return m;
        }

        @Override
        public int getPuntos(){
            return 200;
        }
        @Override
        public String getTipoEnemigo(){
            return "Rojo";
        };
    }