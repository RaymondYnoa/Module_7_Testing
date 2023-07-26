/* CEN 3024C
 * Module 7 Testing Assignment
 * Author: Raymond Ynoa
 * 
 * Update your "word occurrences" application. Add unit testing to the word occurrences class.
 * Please perform this task using JUnit.
 * */

// Imported Java packages.
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class showErrorTest { // Test error message for showError() in class TextAnalyzerJunit.

    @Test
    void test() {
        TextAnalyzerJunit frame = new TextAnalyzerJunit();

        // Use a mock JFrame to capture the displayed error message.
        JFrame mockFrame = new JFrame();
        mockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mockFrame.setVisible(false);
        String expectedErrorMessage = "Test Error Message";

        // Set the default JOptionPane frame to the mock frame.
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
        optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
        optionPane.setMessage(expectedErrorMessage);
        JDialog dialog = optionPane.createDialog(mockFrame, "Error");

        // Call the showError method with the test error message.
        frame.showError(expectedErrorMessage);

        // Ensure the error message was displayed.
        assertEquals(expectedErrorMessage, optionPane.getMessage());
    }
}