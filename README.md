[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/LhnUoRsc)
# Proyecto: The Last Starfighter

##  1.  Integrantes del equipo:

Usqueda Carrizo, Lautaro
Gonzalez Bonetto, Marcos
Katzen, Danna Berenice

## 2. Dominio y Alcance del sistema

### Descripción del problema
Se busca desarrollar una aplicación de escritorio inspirada en el famoso juego “The Super Dimension Fortress Macross”. El jugador debe enfrentarse contra un enjambre de alienígenas con distintas formas que le atacarán disparando misiles y a veces volviendose invisibles. Cuando inicia el juego, los alienígenas no aparecen inmediatamente. En su lugar, aparecen haciendo diferentes patrones de movimiento. El objetivo del juego es alcanzar el mayor puntaje posible sin perder tus 5 vidas y quedar registrado en la tabla de los mejores puntajes.

### Objetivo del sistema
El sistema será un juego funcional que permitirá al jugador experimentar las mecanicas básicas de un juego “Shooter matamarcianos”. El diseño es modular para facilitar la adición de nuevos tipos de enemigos con distintos movimientos y ataques. Junto con un sistema de puntajes que administra los mejores 5 jugadores en una base de datos, utilizando el patrón DAO. Aplicando conceptos básicos del paradigma orientado a objetos.

### Funcionalidades Principales (Features)
**Mecánicas de juego: **
El jugador controla una nave espacial que puede desplazarse y disparar misiles.
En pantalla aparecen enemigos con distintos patrones de movimiento que también pueden disparar.
Si la nave colisionó con un enemigo, proyectil enemigo o algún obstáculo, pierde una vida.
Cuando eliminamos un enemigo, aumenta nuestro puntaje según el enemigo que eliminamos.
El juego termina cuando el jugador se queda sin vidas.
Nave evoluciona temporalmente tras eliminar cierto número de enemigos.

**Sistema de colisiones: **
Se detecta colisiones entre:
Misiles del jugador y enemigos.
Enemigos y la nave del jugador.
Obstáculos y la nave del jugador.
Misiles enemigos y la nave.

**Sistema de puntaje: **
Cada enemigo otorga una determinada cantidad de puntos al ser destruido.
Al finalizar la partida, el jugador puede proporcionar su nombre si rompió algún récord de puntaje.
Los puntajes se almacenan de forma persistente en una base de datos SQLite mediante el patrón DAO.

**Interfaz Gráfica (IGU): **
Interfaz desarrollada con Java Swing, utilizando CardLayout para manejar las distintas pantallas:
Pantalla de inicio
Pantalla de juego
Pantalla de instrucciones
Pantalla de ranking
Se emplea un fondo animado dinámico con desplazamiento continuo.
Los botones utilizan imágenes personalizadas para seguir con la estética retro del juego

**Persistencia: **
Sistema de guardado y carga de los puntajes más altos (High Scores) en una base de datos.
## 3. Arquitectura y diseño
### patron de diseño Singleton
**Justificación: ** se utiliza Singleton en la clase de conexión a SQLite para garantizar una única instancia compartida en todo el sistema. Los DAOs acceden a esta instancia para ejecutar consultas sin duplicar conexiones. Y se utiliza en `AudioManager` Controla música, FX, pausa, resumen, soundtrack EVO, y volumen. Tambien evita múltiples manejadores de audio desordenados.
### Patrón de Diseño Adicional: Factory Method (Método de Fábrica)
**Justificación: ** Se utilizara este patrón para la creación de los objetos ‘enemigo’. Tendremos una clase abstracta ‘personaje_base’ con un método ‘agregaEnemigo()’. La cual crea subclases concretas como ‘Enemigo_rojo’ o ‘Enemigo_violeta’, que implementarán métodos de movimiento y ataque que los caracteriza
### Patrón de diseño Decorator
**Justificación: ** Se utilizará el patrón decorator para el uso de power-ups (potenciadores) o NaveEvolucionada, que se aplicarán (o decorarán) la nave principal. Estos consisten en obtener una vida extra y otro en  que los misiles hagan el triple de daño. En el caso de la nave evolucionada aumenta la velocidad, aumenta en 1 el daño y da mas velocidad a los misiles.
### Patrón de Diseño: Strategy (movimientos de enemigos) ###
**Justificacion: ** Se utiliza para definir diferentes comportamientos de movimiento de los enemigos sin modificar la clase base. El enemigo fantasma cambia dinámicamente su estrategia según su estado (visible / invisible), aplicando polimorfismo dinámico.
Esto permite agregar nuevos tipos de movimientos sin modificar el código existente
### Concurrencia (Hilos)
**FondoAnimado: **  Se emplea un timer en la clase `FondoAnimado` para ejecutar actualizaciones del fondo de manera periódica (parallax). Este `Timer` utiliza un hilo independiente que actualiza el desplazamiento cada 16 ms (~60 FPS) sin bloquear la lógica del juego.
El hilo principal ejecuta el bucle de juego (movimientos, colisiones, disparos).
**Event Dispatch Thread (EDT): **
El EDT de Swing maneja toda la parte gráfica y eventos de la UI, mantiene la interfaz reactiva mientras el juego corre.
**Game Loop: **
Hilo dedicado al game loop, separado del hilo de Swing. Se encarga de actualizar la lógica de juego a ~60 FPS, mientras Swing se ocupa solo de dibujar y manejar eventos
**java.util.Timer: **
Para los power-ups se usa un java.util.Timer con un TimerTask que corre en un hilo en segundo plano. Cuando toca spawnear un powerup, desde ese hilo se programa la actualización en el EDT con SwingUtilities.invokeLater, evitando problemas de concurrencia.

### Principios POO aplicados
**Abstracción: ** La clase `personaje_base` define los atributos y métodos comunes como posición, tamaño, vida, velocidad,  y funciones compartidas como getBounds() (para detectar colisiones), estaVivo() y recibirDanio().
spaceship_1, Enemigo_Violeta, Enemigo_Rojo y Enemigo_Fantasma utilizan estos elementos y definen sus propios comportamientos,  como su movimiento o colision.
**Herencia: ** La herencia se utiliza para reutilizar código y definir una estructura común.
personaje_base contiene los atributos y métodos generales (posición, vida, velocidad, colisión).
Las clases spaceship_1, Enemigo_Violeta , Enemigo_Rojo y Enemigo_Fantasma heredan de ella y redefinen comportamientos concretos como movimiento o ataque.
**Polimorfismo: ** Usamos polimorfismo para que nos permita que todas las clases derivadas de personaje_base se manejen de forma uniforme, pero ejecuten acciones distintas según su tipo.
En el juego, esto se aplica cuando todos los personajes (nave, enemigos, misiles, obstáculos) se actualizan o dibujan con las mismas llamadas de método, sin distinguir su clase específica.
Por ejemplo, spaceship_1 redefine ataque(), mientras los enemigos redefinen mover(), ataque(), getPuntos() y getTipoEnemigo().
**Encapsulamiento: ** Los atributos del personaje (posicion, vida, velocidad, etc.) son privados y se accede a ellos mediante métodos públicos (getters y setters), evitando el acceso directo desde fuera de la clase para garantizar que los cambios en el estado interno se realicen de forma controlada


### Diagramas de Diseño

#### **Diagrama de Clases UML (Conceptual)**
#### **Implementacion  de Decorators**
![Diagrama Decorator](diagrama/diagrama.png)

####
**Prototipo de la IGU (Wireframe)**
![Prototipo IGU](diagrama/prototipo_igu.png)
## 4. Stack Tecnologico

**Lenguaje:** Java 24
**IDE:** Visual Studio Code
**Base de Datos:** SQLite 3.50.4
**Framework de IGU:** Java Swing
**Control de Versiones:** Git y Github

- **Compilar el proyecto en Visual Studio Code**
- Abrir el proyecto en VS Code.
- Asegurarse de tener instalado el paquete Java Extension Pack.
- Verificar que el JDK 17 esté configurado en el entorno.
- Compilar el proyecto. Visual Studio Code lo hace automáticamente si detecta Maven o configuración de proyecto.

- **Ejecutar la aplicación**
- Abrir el archivo Main.java 
- Ejecutar el archivo presionando RUN desde VS Code.
- Se abrira el menu prinicipal.
