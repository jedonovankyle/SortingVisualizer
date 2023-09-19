import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class sorting extends JPanel {

    private static final int ARRAY_SIZE = 50;
    private static final int BAR_WIDTH = 40;
    private int[] array;
    private int currentMovingBarIndex = -1;
    private JPanel sortingPanel;
    private JButton startButton;

    private Timer timer; // Timer for bar animation

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

        // Creates a timer to update the visualization
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    private void drawArray(Graphics g) {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int x = i * BAR_WIDTH;
            int y = sortingPanel.getHeight() - array[i];
            String numberText = String.valueOf(array[i]); // Converts the number to text

            if (i == currentMovingBarIndex) {
                g.setColor(Color.RED); // Sets color for the moving bar
            } else {
                g.setColor(Color.BLUE); // Sets color for other bars
            }

            g.fillRect(x, y, BAR_WIDTH, array[i]);

            // Sets color for text
            g.setColor(Color.BLACK);

            // Calculates the position to center the text within the bar
            int textX = x + BAR_WIDTH / 2 - 10;
            int textY = sortingPanel.getHeight() - 10;
            g.drawString(numberText, textX, textY);
        }
    }

    private void startSorting() {
        startButton.setEnabled(false);

        // Starts the timer for animation
        timer.start();

        new Thread(() -> {
            bubbleSort();

            // Stops the timer when sorting is done
            timer.stop();

            startButton.setEnabled(true);
        }).start();
    }

    private void bubbleSort() {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            for (int j = 0; j < ARRAY_SIZE - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                    currentMovingBarIndex = j + 1;
                    delay(200);
                }
            }
        }
        currentMovingBarIndex = -1;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sorting Visualization");
            sorting sortingVisualization = new sorting();
            frame.add(sortingVisualization);

            int frameWidth = ARRAY_SIZE * BAR_WIDTH + 50;
            frame.setSize(frameWidth, 600);
            frame.setSize(1200, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
