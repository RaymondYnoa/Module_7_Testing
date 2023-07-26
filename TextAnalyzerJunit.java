/* CEN 3024C
 * Module 7 Testing Assignment
 * Author: Raymond Ynoa
 * 
 * Update your "word occurrences" application. Add unit testing to the word occurrences class.
 * Please perform this task using JUnit.
 * */

// Imported Java packages.
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class TextAnalyzerJunit extends JFrame {
    JTextArea outputTextArea;
    private JButton analyzeButton;
    private JButton skipToTopButton;

    public TextAnalyzerJunit() {
        setTitle("Text Analyzer"); // Frame title.
        setSize(400, 600); // Frame size.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close frame on exit.

        outputTextArea = new JTextArea(); // The output text area.
        
        // Scrolling for output text area.
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane);

        analyzeButton = new JButton("Analyze Text"); // Analyze Text button.
        analyzeButton.addActionListener(new ActionListener() { // Listen for Analyze Text button.
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm"; // The URL of the text file.
                analyzeAndDisplayText(url); // Analyze and display text from url.
            }
        });

        skipToTopButton = new JButton("Skip to Top"); // Skip to top button.
        skipToTopButton.addActionListener(new ActionListener() { // Listen for Analyze Text button.
            @Override
            public void actionPerformed(ActionEvent e) {
                outputTextArea.setCaretPosition(0); // Set output area to the top.
            }
        });

        // Organize button components.
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(analyzeButton);
        buttonPanel.add(skipToTopButton);
        add(buttonPanel, "South");
    }

    void analyzeAndDisplayText(String url) {
        Map<String, Integer> wordFrequencies = new HashMap<>(); // Read text file words and frequencies to Map.

        URL textUrl;
        try {
            textUrl = new URL(url);
        } catch (IOException e) { // Throw an exception.
            showError("Error opening URL: " + url);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(textUrl.openStream()))) { // Extract text from URL.
            boolean isInPoem = false; // Test if poem text is counted.
            String line;
            while ((line = reader.readLine()) != null) {
                if (!isInPoem && line.matches(".*<h1>.*")) { // HTML tag at the title.
                    isInPoem = true;
                    continue;
                }

                if (isInPoem && line.matches(".*<!--end chapter-->.*")) { // End using HTML tag at end of chapter.
                    break;
                }

                // Within poem's text.
                if (isInPoem) {
                    line = line.replaceAll("<.*?>", ""); // Remove HTML tags
                    line = line.replaceAll("[^A-Za-z ]", ""); // Remove non-word characters
                    String[] words = line.split("\\s+"); // Split line into words using whitespace as delimiter
                    for (String word : words) { // For each word.
                        if (!word.isEmpty()) { // If word is not empty.
                            wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) { // Throw an exception.
            showError("Error reading text from URL: " + url);
            return;
        }

        displayWordFrequencies(wordFrequencies);
    }

    private void displayWordFrequencies(Map<String, Integer> wordFrequencies) { // Return words and their frequency.
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordFrequencies.entrySet()); // Map word and frequency.

        // Sort the word frequencies in descending order.
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Build the result string with numbered list.
        StringBuilder result = new StringBuilder();
        int count = 1;
        
        // Print word frequencies.
        for (Map.Entry<String, Integer> entry : entries) {
            result.append(count).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            count++;
        }

        // Display the word frequencies in the JTextArea.
        outputTextArea.setText(result.toString());
    }

    void showError(String message) { // JFrame error message.
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextAnalyzerJunit frame = new TextAnalyzerJunit();
            frame.setVisible(true);
        });
    }
}