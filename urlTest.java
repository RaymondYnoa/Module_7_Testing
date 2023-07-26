/* CEN 3024C
 * Module 7 Testing Assignment
 * Author: Raymond Ynoa
 * 
 * Update your "word occurrences" application. Add unit testing to the word occurrences class.
 * Please perform this task using JUnit.
 * */

// Imported Java packages.
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

// Test URL validation for analyzeAndDisplayText() in class TextAnalyzerJunit.
public class urlTest {

	// Test for valid URL.
    @Test
    public void testAnalyzeAndDisplayText_ValidURL() {
        TextAnalyzerJunit frame = new TextAnalyzerJunit();

        String validURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
        frame.analyzeAndDisplayText(validURL);

        // Verify that the outputTextArea is not empty after analyzing a valid URL
        assertFalse(frame.outputTextArea.getText().isEmpty());
    }

    // Test for invalid URL.
    @Test
    public void testAnalyzeAndDisplayText_InvalidURL() {
        TextAnalyzerJunit frame = new TextAnalyzerJunit();

        String invalidURL = "https://www.invalidurl.com/nonexistentfile.htm";

        // Redirect System.err to capture error messages
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Call analyzeAndDisplayText with an invalid URL
        frame.analyzeAndDisplayText(invalidURL);

        // Reset System.err
        System.setErr(System.err);

        // Verify that an error message related to the invalid URL is displayed
        String expectedErrorMessage = "Error opening URL: " + invalidURL;
        assertEquals(expectedErrorMessage, errContent.toString().trim());
    }
}