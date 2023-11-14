package checkers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Game {
	public static BufferedImage boardImage;
	public static BufferedImage redCheckerImage;
	public static BufferedImage blueCheckerImage;
	public static BufferedImage redCheckerKingImage;
	public static BufferedImage blueCheckerKingImage;
	public static BufferedImage highlightImage;	

	public static void playTheGame() throws IOException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Checkers");
		frame.setBounds(0, 0, 230, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardImage = ImageIO.read(new File(Paths.get("src", "checkers", "assets", "board.png").toString()));
		redCheckerImage = ImageIO.read(new File(Paths.get("src", "checkers", "assets", "redChecker.png").toString()));
		blueCheckerImage = ImageIO.read(new File(Paths.get("src", "checkers", "assets", "blueChecker.png").toString()));
		redCheckerKingImage = ImageIO.read(new File(Paths.get("src", "checkers", "assets", "redCheckerKing.png").toString()));
		blueCheckerKingImage = ImageIO.read(new File(Paths.get("src", "checkers", "assets", "blueCheckerKing.png").toString()));
		highlightImage = ImageIO.read(new File(Paths.get("src", "checkers", "assets", "highlight.png").toString()));
		frame.setIconImage(boardImage);
		GameBoardPanel panel = new GameBoardPanel();
		//JLabel boardImageLabel = new JLabel(new ImageIcon(boardImage));
		//boardImageLabel.setBounds(0,0,64,64);
		//boardImageLabel.setVisible(true);
		//panel.add(boardImageLabel);
		frame.add(panel);
		frame.setVisible(true);
		
	}

}
