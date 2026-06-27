import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

public class Main extends JFrame {

    private JTextField[][] cells = new JTextField[9][9];

    public Main() {

        setTitle("Sudoku Solver");
        setSize(700, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(25, 25, 35));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sudoku Solver", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        add(title, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(9, 9, 2, 2));
        gridPanel.setBackground(new Color(25, 25, 35));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font cellFont = new Font("Arial", Font.BOLD, 20);

        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                JTextField cell = new JTextField();

                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(cellFont);

                cell.setBackground(new Color(40, 40, 55));
                cell.setForeground(Color.WHITE);
                cell.setCaretColor(Color.WHITE);

                cell.setBorder(new LineBorder(
                        (i / 3 + j / 3) % 2 == 0
                                ? new Color(0, 255, 180)
                                : new Color(100, 100, 255),
                        1
                ));

                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        JButton generateBtn = new JButton("Generate Puzzle");
        JButton solveBtn = new JButton("Solve Sudoku");
        JButton clearBtn = new JButton("Clear");

        generateBtn.setFont(new Font("Arial", Font.BOLD, 15));
        solveBtn.setFont(new Font("Arial", Font.BOLD, 15));
        clearBtn.setFont(new Font("Arial", Font.BOLD, 15));

        generateBtn.setBackground(new Color(255, 180, 0));
        solveBtn.setBackground(new Color(0, 220, 180));
        clearBtn.setBackground(new Color(255, 80, 80));

        generateBtn.setForeground(Color.BLACK);
        solveBtn.setForeground(Color.BLACK);
        clearBtn.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(25, 25, 35));

        buttonPanel.add(generateBtn);
        buttonPanel.add(solveBtn);
        buttonPanel.add(clearBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Generate Puzzle

        generateBtn.addActionListener(e -> {

            Random random = new Random();

            int[][] puzzle =
                    SudokuPuzzles.PUZZLES[
                            random.nextInt(
                                    SudokuPuzzles.PUZZLES.length)];

            for (int i = 0; i < 9; i++) {

                for (int j = 0; j < 9; j++) {

                    if (puzzle[i][j] == 0) {
                        cells[i][j].setText("");
                    } else {
                        cells[i][j].setText(
                                String.valueOf(puzzle[i][j]));
                    }
                }
            }
        });

        // Solve Sudoku

        solveBtn.addActionListener(e -> {

            int[][] board = new int[9][9];

            try {

                for (int i = 0; i < 9; i++) {

                    for (int j = 0; j < 9; j++) {

                        String text =
                                cells[i][j].getText().trim();

                        if (text.isEmpty()) {
                            board[i][j] = 0;
                        } else {

                            int value =
                                    Integer.parseInt(text);

                            if (value < 0 || value > 9) {

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Only values 0-9 allowed!"
                                );

                                return;
                            }

                            board[i][j] = value;
                        }
                    }
                }

                if (SudokuSolver.solve(board)) {

                    for (int i = 0; i < 9; i++) {

                        for (int j = 0; j < 9; j++) {

                            cells[i][j].setText(
                                    String.valueOf(board[i][j]));
                        }
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            "Sudoku Solved Successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No Solution Exists!"
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Input!"
                );
            }
        });

        // Clear Board

        clearBtn.addActionListener(e -> {

            for (int i = 0; i < 9; i++) {

                for (int j = 0; j < 9; j++) {

                    cells[i][j].setText("");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Main::new);
    }
}
