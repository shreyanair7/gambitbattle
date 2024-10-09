package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard extends JFrame {

    private static final int BOARD_SIZE = 8;
    private JPanel boardPanel;
    private Map<String, ImageIcon> pieceImages = new HashMap<>();

    public ChessBoard() {
        setTitle("Chess Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load images for each piece
        loadPieceImages();

        // Create the chessboard panel
        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        add(boardPanel);
        
        // Set up the chessboard squares and add pieces
        setupBoard();
    }

    private void loadPieceImages() {
        // Load images into pieceImages map (adjust paths if necessary)
        pieceImages.put("black_rook", new ImageIcon("resources/black_rook.png"));
        pieceImages.put("black_knight", new ImageIcon("resources/black_knight.png"));
        pieceImages.put("black_bishop", new ImageIcon("resources/black_bishop.png"));
        pieceImages.put("black_queen", new ImageIcon("resources/black_queen.png"));
        pieceImages.put("black_king", new ImageIcon("resources/black_king.png"));
        pieceImages.put("black_pawn", new ImageIcon("resources/black_pawn.png"));
        
        pieceImages.put("white_rook", new ImageIcon("resources/white_rook.png"));
        pieceImages.put("white_knight", new ImageIcon("resources/white_knight.png"));
        pieceImages.put("white_bishop", new ImageIcon("resources/white_bishop.png"));
        pieceImages.put("white_queen", new ImageIcon("resources/white_queen.png"));
        pieceImages.put("white_king", new ImageIcon("resources/white_king.png"));
        pieceImages.put("white_pawn", new ImageIcon("resources/white_pawn.png"));
    }

    private void setupBoard() {
        boolean isWhiteSquare = true;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel();
                square.setBackground(isWhiteSquare ? Color.WHITE : Color.GRAY);
                boardPanel.add(square);

                // Place pieces on their initial squares
                placeInitialPieces(row, col, square);

                // Alternate square color
                isWhiteSquare = !isWhiteSquare;
            }
            isWhiteSquare = !isWhiteSquare; // Alternate colors every row
        }
    }

    private void placeInitialPieces(int row, int col, JPanel square) {
        JLabel pieceLabel = new JLabel();
        
        // Place black pieces on row 0 and 1
        if (row == 0 || row == 1) {
            String pieceType = getPieceType(row, col, "black");
            pieceLabel.setIcon(pieceImages.get(pieceType));
        }
        
        // Place white pieces on row 6 and 7
        if (row == 6 || row == 7) {
            String pieceType = getPieceType(row, col, "white");
            pieceLabel.setIcon(pieceImages.get(pieceType));
        }

        square.add(pieceLabel);
    }

    private String getPieceType(int row, int col, String color) {
        // Row 0 or 7: Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook
        if (row == 0 || row == 7) {
            switch (col) {
                case 0:
                case 7:
                    return color + "_rook";
                case 1:
                case 6:
                    return color + "_knight";
                case 2:
                case 5:
                    return color + "_bishop";
                case 3:
                    return color + (color.equals("black") ? "_queen" : "_queen");
                case 4:
                    return color + (color.equals("white") ? "_king" : "_king");
            }
        }
        // Row 1 or 6: Pawns
        return color + "_pawn";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessBoard chessBoard = new ChessBoard();
            chessBoard.setVisible(true);
        });
    }
}
