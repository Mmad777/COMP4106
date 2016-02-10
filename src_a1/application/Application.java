package application;

import java.io.IOException;

public class Application {
 
    public static void main(String[] args) throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        new Controller();
    }
}