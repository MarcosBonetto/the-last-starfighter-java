import vista.Ventanas;

public class Main {
    public static void main(String[] args) {
        Ventanas ventana = new Ventanas();
        ventana.setVisible(true);
    }

    //javac -cp "SQLite\sqlite-jdbc-3.50.3.0.jar" -d out Main.java modelo\*.java vista\*.java controlador\*.java
    //java -cp "out;SQLite\sqlite-jdbc-3.50.3.0.jar" Main

}