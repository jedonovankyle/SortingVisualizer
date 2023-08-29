import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class sorting extends JPanel {

    private static final int ARRAY_SIZE = 50;
    private static final int BAR_WIDTH = 20;
    private int[] array;
    private int currentMovingBarIndex = -1; // Initialize with an invalid index

    private JPanel sortingPanel;
    private JButton startButton;

    public sorting() {
        initializeArray();
        setupUI();
    }

    private void initializeArray() {
        array = new int[ARRAY_SIZE];
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(400) + 100; // Generates random values between 100 and 500
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        sortingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawArray(g);
            }
        };
        sortingPanel.setBackground(Color.WHITE);
        add(sortingPanel, BorderLayout.CENTER);

        startButton = new JButton("Start Sorting");
        startButton.addActionListener(e -> startSorting());
        add(startButton, BorderLayout.SOUTH);
    }

    private void bubbleSort() {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            for (int j = 0; j < ARRAY_SIZE - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                    currentMovingBarIndex = j + 1; // Updates the index of the moving bar
                    repaint();
                    delay(150);
                }
            }
        }
        currentMovingBarIndex = -1; // Resets the index after sorting is done
        repaint();
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawArray(Graphics g) {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int x = i * BAR_WIDTH;
            int y = sortingPanel.getHeight() - array[i];

            if (i == currentMovingBarIndex) {
                g.setColor(Color.BLUE); // Set color for the moving bar
            } else {
                g.setColor(Color.GRAY); // Set color for other bars
            }

            g.fillRect(x, y, BAR_WIDTH, array[i]);
        }
    }

    public void startSorting() {
        startButton.setEnabled(false);
        new Thread(() -> {
            bubbleSort();
            startButton.setEnabled(true);
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sorting Visualization");
            sorting sortingVisualization = new sorting();
            frame.add(sortingVisualization);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Centers the frame on the screen
        });
    }
}
