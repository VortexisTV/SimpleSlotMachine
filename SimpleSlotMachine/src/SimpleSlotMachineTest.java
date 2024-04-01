import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SimpleSlotMachineTest {
    private SimpleSlotMachine slotMachine;

    @Before
    public void setUp() {
        slotMachine = new SimpleSlotMachine();
    }

    @Test
    public void testRandomSymbolGeneration() {
        String[] symbols = SimpleSlotMachine.getSymbols();
        assertNotNull(symbols);
        for (int i = 0; i < 100; i++) { // Generate symbols multiple times for better validation
            String[] reels = SimpleSlotMachine.generateRandomSymbols();
            assertNotNull(reels);
            assertEquals(3, reels.length);
            for (String symbol : reels) {
                assertTrue(symbol + " is not a valid symbol", isValidSymbol(symbol, symbols));
            }
        }
    }

    @Test
    public void testInsufficientFunds() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set balance lower than the required amount using the Player instance
        SimpleSlotMachine.getPlayer().setBalance(50); // Use the Player instance to set balance

        // Simulate user input and run the main method
        ByteArrayInputStream inContent = new ByteArrayInputStream("spin\nexit".getBytes());
        System.setIn(inContent);
        SimpleSlotMachine.main(new String[]{});

        System.setOut(System.out);

        assertTrue(outContent.toString().contains("Insufficient balance"));
    }

    @Test
    public void testValidInput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("spin\nexit".getBytes());
        System.setIn(inContent);

        SimpleSlotMachine.main(new String[]{});

        System.setOut(System.out);

        assertFalse(outContent.toString().contains("Invalid input"));
    }

    private boolean isValidSymbol(String symbol, String[] symbols) {
        for (String validSymbol : symbols) {
            if (validSymbol.equals(symbol)) {
                return true;
            }
        }
        return false;
    }
}
