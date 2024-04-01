import java.util.Random;
import java.util.Scanner;


public class SimpleSlotMachine {
                                            // Cherry, Bell, Lemon, Orange, Star, Diamond, Seven
    private static final String[] SYMBOLS = { "\uD83C\uDF52", "\uD83D\uDD14", "\uD83C\uDF4B", "\uD83C\uDF4A", "\u2B50", "\uD83D\uDD39", "\u0037"};
    private static final int[] PAYOUTS = {100, 200, 50, 50, 500, 1000, 2000}; // Payouts for each symbol
    private static final int[][] WINNING_PATTERNS = {
            {0, 0, 0}, // All symbols in the first reel
            {1, 1, 1}, // All symbols in the second reel
            {2, 2, 2}, // All symbols in the third reel
            {0, 1, 2}, // Any symbol in each reel
            {2, 1, 0}  // Any symbol in reverse order
    };

    private static Player player = new Player(1000); // Initial player with balance

    public static String[] generateRandomSymbols() {
        Random random = new Random();
        String[] reels = new String[3];
        for (int i = 0; i < 3; i++) {
            reels[i] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        }
        return reels;
    }

    public static Player getPlayer() {
        return player;
    }

    public static String[] getSymbols() {
        return SYMBOLS;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to the Simple Slot Machine!");

        while (true) {
            System.out.println("\nYour balance: " + player.getBalance() + " coins");
            System.out.println("Type 'spin' to play, 'exit' to quit:");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Thanks for playing! Goodbye!");
                break;
            } else if (input.equals("spin")) {
                spinReels(random);
            } else {
                System.out.println("Invalid input. Please type 'spin' to play or 'exit' to quit.");
            }
        }

        scanner.close();
    }

    private static void spinReels(Random random) {
        player.deductBalance(100); // Deduct 100 coins for spinning
        String[] reels = new String[3];

        for (int i = 0; i < 3; i++) {
            reels[i] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        }

        displayResults(reels, random);
    }

    private static void displayResults(String[] reels, Random random) {
        System.out.println("\nResults:");
        for (String symbol : reels) {
            System.out.print(symbol + " ");
        }
        System.out.println();

        int totalPayout = calculatePayout(reels, random);
        if (totalPayout > 0) {
            player.addBalance(totalPayout);
            System.out.println("Congratulations! You win " + totalPayout + " coins!");
        } else {
            System.out.println("Better luck next time!");
        }
    }

    private static int calculatePayout(String[] reels, Random random) {
        for (int[] pattern : WINNING_PATTERNS) {
            if (reels[pattern[0]].equals(reels[pattern[1]]) && reels[pattern[1]].equals(reels[pattern[2]])) {
                // Matched a winning pattern, return the corresponding payout
                return PAYOUTS[indexOfSymbol(reels[pattern[0]])]; // Payout based on the symbol of the winning pattern
            }
        }
        return 0; // No winning pattern matched
    }

    private static int indexOfSymbol(String symbol) {
        for (int i = 0; i < SYMBOLS.length; i++) {
            if (SYMBOLS[i].equals(symbol)) {
                return i;
            }
        }
        return -1; // Symbol not found
    }
}