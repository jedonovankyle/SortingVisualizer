import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class sorting extends JPanel {

    private static final int ARRAY_SIZE = 50;
    private static final int BAR_WIDTH = 10;
    private int[] array;
    public sorting() {
        initializeArray();
    }

    private void initializeArray() {
        array = new int[ARRAY_SIZE];
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(400) + 100; // Generate random values between 100 and 500
        }
    }

    private void bubbleSort() {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            for (int j = 0; j < ARRAY_SIZE - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                    repaint();
                    delay(50); // Add a delay to visualize the sorting process
                }
            }
        }
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawArray(g);
    }

    private void drawArray(Graphics g) {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int x = i * BAR_WIDTH;
            int y = getHeight() - array[i];
            g.fillRect(x, y, BAR_WIDTH, array[i]);
        }
    }

    public void startSorting() {
        new Thread(() -> {
            bubbleSort();
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

            JButton startButton = new JButton("Start Sorting");
            startButton.addActionListener(e -> sortingVisualization.startSorting());
            frame.add(startButton, BorderLayout.SOUTH);
        });
    }
}
