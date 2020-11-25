package ui;

import java.io.FileNotFoundException;

/**
 * Runs App.
 */

public class Main {
    public static void main(String[] args) {
        try {
            new App();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}

