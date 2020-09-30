public class CaptureExit extends Thread {
    // Runs a thread to listen for the program quitting (force quit included) and prints an ending dialogue
    public void run() {
        System.out.println("\nExiting game...\n");
        System.out.println("Thank you for playing board game simulator! Please play again!");
        System.out.println("Goodbye!");
    }
 }