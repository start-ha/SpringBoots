package Therad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

public class MoleGame extends JFrame {
    private static final int ROWS = 3;            // 행 수
    private static final int COLS = 4;            // 열 수
    private static final int GAME_TIME = 30;      // 총 게임 시간(초)
    private static final int SPAWN_INTERVAL = 800; // 두더지 생성 간격(ms)
    private static final int MOLE_DURATION = 1500; // 두더지 유지 시간(ms)

    private JButton[][] buttons = new JButton[ROWS][COLS];
    private JLabel scoreLabel = new JLabel("점수: 0");
    private JLabel timeLabel = new JLabel("남은 시간: " + GAME_TIME);
    private int score = 0;
    private int timeLeft = GAME_TIME;
    private ScheduledExecutorService scheduler;
    private Random random = new Random();

    public MoleGame() {
        super("🐹 두더지 잡기 게임 (Swing)");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        // 상단 정보 패널
        JPanel topPanel = new JPanel();
        topPanel.add(scoreLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(timeLabel);
        add(topPanel, BorderLayout.NORTH);

        // 중앙 버튼 패널 (게임 필드)
        JPanel fieldPanel = new JPanel(new GridLayout(ROWS, COLS, 8, 8));
        fieldPanel.setBackground(Color.WHITE);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setFocusPainted(false);
                btn.addActionListener(e -> {
                    if ("두더지!".equals(btn.getText())) {
                        score++;
                        scoreLabel.setText("점수: " + score);
                        btn.setText("");
                        btn.setBackground(Color.LIGHT_GRAY);
                    }
                });
                buttons[i][j] = btn;
                fieldPanel.add(btn);
            }
        }

        add(fieldPanel, BorderLayout.CENTER);
        startGame();
    }

    private void startGame() {
        scheduler = Executors.newScheduledThreadPool(3);

        // 두더지 스폰 스레드
        scheduler.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> spawnMole());
        }, 0, SPAWN_INTERVAL, TimeUnit.MILLISECONDS);

        // 시간 카운트다운
        scheduler.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                timeLeft--;
                timeLabel.setText("남은 시간: " + timeLeft);
                if (timeLeft <= 0) endGame();
            });
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void spawnMole() {
        if (timeLeft <= 0) return;

        int i = random.nextInt(ROWS);
        int j = random.nextInt(COLS);
        JButton target = buttons[i][j];

        // 이미 두더지가 있으면 스킵
        if ("두더지!".equals(target.getText())) return;

        target.setText("두더지!");
        target.setBackground(new Color(255, 204, 102));

        // 일정 시간 후 자동 사라짐
        scheduler.schedule(() -> {
            SwingUtilities.invokeLater(() -> {
                if ("두더지!".equals(target.getText())) {
                    target.setText("");
                    target.setBackground(Color.LIGHT_GRAY);
                }
            });
        }, MOLE_DURATION, TimeUnit.MILLISECONDS);
    }

    private void endGame() {
        scheduler.shutdownNow();
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, "게임 종료!\n최종 점수: " + score, "결과", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MoleGame().setVisible(true);
        });
    }
}
