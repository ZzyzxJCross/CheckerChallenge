package checkers;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {
	
	private Board gameState;
	private int moveStartFound;
	private int moveStartX;
	private int moveStartY;
	private int jumperX;
	private int jumperY;
	private boolean jumping;
	
	public static int[] boardSpaceStates;
	public static boolean jumpCom;
	public static int jumpComX;
	public static int jumpComY;
	public static int p1Move;
	public static int p2Move;
	public static int winner = 0;
	
	
	public void handleMove(int xPos, int yPos) {
		System.out.println("Click detected at " + xPos + ", " + yPos);
		int clickedX = xPos;
		int clickedY = yPos;
		if(clickedX < 128 && clickedY < 128) {
			if(moveStartFound == 0) {
				moveStartX = clickedX / 16;
				moveStartY = clickedY / 16;
				moveStartFound = gameState.spaces[moveStartX][moveStartY];
				if(gameState.player1Turn) {
					if(moveStartFound == 2 || moveStartFound == 4) {
						moveStartFound = 0;
					}
				} else {
					if(moveStartFound == 1 || moveStartFound == 3) {
						moveStartFound = 0;
					}
				}
			} else {
				boolean legalMove = false;
				int destX = clickedX / 16;
				int destY = clickedY / 16;
				int jumpOverSpace;
				if(gameState.spaces[destX][destY] == 0) { 
					switch(moveStartFound) {
					case 1:
						if(destX == moveStartX + 1 && (destY == moveStartY + 1 || destY == moveStartY - 1) && !jumping) {
							legalMove = true;
						}
						jumpOverSpace = gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2];
						if((destX == moveStartX + 2 && (destY == moveStartY + 2 || destY == moveStartY - 2) && (jumpOverSpace == 2 || jumpOverSpace == 4)) && (!jumping || (moveStartX == jumperX && moveStartY == jumperY))) {
							legalMove = true;
							jumping = true;
							jumperX = destX;
							jumperY = destY;
							gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2] = 0;
						}
						break;
					case 2:
						if(destX == moveStartX - 1 && (destY == moveStartY + 1 || destY == moveStartY - 1) && !jumping) {
							legalMove = true;
						}
						jumpOverSpace = gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2];
						if((destX == moveStartX - 2 && (destY == moveStartY + 2 || destY == moveStartY - 2) && (jumpOverSpace == 1 || jumpOverSpace == 3)) && (!jumping || (moveStartX == jumperX && moveStartY == jumperY))) {
							legalMove = true;
							jumping = true;
							jumperX = destX;
							jumperY = destY;
							gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2] = 0;
						}
						break;
					case 3:
						if((destX == moveStartX + 1 || destX == moveStartX - 1) && (destY == moveStartY + 1 || destY == moveStartY - 1) && !jumping) {
							legalMove = true;
						}
						jumpOverSpace = gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2];
						if(((destX == moveStartX + 2 || destX == moveStartX - 2) && (destY == moveStartY + 2 || destY == moveStartY - 2) && (jumpOverSpace == 2 || jumpOverSpace == 4)) && (!jumping || (moveStartX == jumperX && moveStartY == jumperY))) {
							legalMove = true;
							jumping = true;
							jumperX = destX;
							jumperY = destY;
							gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2] = 0;
						}
						break;
					case 4:
						if((destX == moveStartX + 1 || destX == moveStartX - 1) && (destY == moveStartY + 1 || destY == moveStartY - 1) && !jumping) {
							legalMove = true;
						}
						jumpOverSpace = gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2];
						if(((destX == moveStartX + 2 || destX == moveStartX - 2) && (destY == moveStartY + 2 || destY == moveStartY - 2) && (jumpOverSpace == 1 || jumpOverSpace == 3)) && (!jumping || (moveStartX == jumperX && moveStartY == jumperY))) {
							legalMove = true;
							jumping = true;
							jumperX = destX;
							jumperY = destY;
							gameState.spaces[(moveStartX + destX) / 2][(moveStartY + destY) / 2] = 0;
						}
						break;
					}
				}
				if(legalMove) {
					gameState.spaces[destX][destY] = gameState.spaces[moveStartX][moveStartY];
					gameState.spaces[moveStartX][moveStartY] = 0;
					if(moveStartFound == 1) {
						if(destX == 7) {
							gameState.spaces[destX][destY] = 3;
						}
					}
					if(moveStartFound == 2) {
						if(destX == 0) {
							gameState.spaces[destX][destY] = 4;
						}
					}
					if(!jumping) {
						gameState.player1Turn = !gameState.player1Turn;
					}
					int compressionIndex = 0;
					for(int i = 0; i < 8; i++) {
						for(int j = (i + 1) % 2; j < 8; j += 2) {
							boardSpaceStates[compressionIndex] = gameState.spaces[i][j];
						}
					}
				}
				moveStartFound = 0;
			}
		} else {
			moveStartFound = 0;
			if(jumping) {
				gameState.player1Turn = !gameState.player1Turn;
				jumping = false;
			}
		}
		repaint();
	}
	
	public GameBoardPanel() {
		super();
		this.gameState = new Board();
		boardSpaceStates = new int[32];
		
		moveStartFound = 0;
		jumping = false;
		p1Move = 1;
		p2Move = 0;
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				handleMove(e.getX(), e.getY());
			}
		});
		
		Thread aiThread = new Thread(() -> {
			System.out.println("AIMan p1Move: " + AIManager.p1Move + ", PANEL p1Move: " + p1Move);
			boolean gotTheInfo = false;
			while(true) {
				//handle any communication with the AI here
				//System.out.println("LOOPING");
				if(AIManager.p1Move == p1Move) {
					System.out.println("THEY'RE THE SAME!");
					if(!gotTheInfo) {
						gotTheInfo = true;
						System.out.println("GTI AIMan p1Move: " + AIManager.p1Move + ", PANEL p1Move: " + p1Move);
						handleMove(AIManager.p1MoveCords[0], AIManager.p1MoveCords[1]);
						handleMove(AIManager.p1MoveCords[2], AIManager.p1MoveCords[3]);
					}
				}
				
			}
		});
		aiThread.start();
		
	}
	
	


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(Game.boardImage, 1, 0, this);
		int p1Pieces = 0;
		int p2Pieces = 0;
		
		if(jumping) {
			g.drawImage(Game.highlightImage, jumperX * 16 + 1, jumperY * 16, this);
		}
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				switch(gameState.spaces[i][j]) {
				case 1:
					g.drawImage(Game.redCheckerImage, 1 + i * 16, j * 16, this);
					p1Pieces++;
					break;
				case 2:
					g.drawImage(Game.blueCheckerImage, 1 + i * 16, j * 16, this);
					p2Pieces++;
					break;
				case 3:
					g.drawImage(Game.redCheckerKingImage, 1 + i * 16, j * 16, this);
					p1Pieces++;
					break;
				case 4:
					g.drawImage(Game.blueCheckerKingImage, 1 + i * 16, j * 16, this);
					p2Pieces++;
					break;
				}
			}
		}
		if(p1Pieces == 0) {
			this.gameState = new Board();
			g.drawChars("Player 2 Wins!!!".toCharArray(), 0, 16, 0, 138);
		}else if(p2Pieces == 0) {
			this.gameState = new Board();
			g.drawChars("Player 1 Wins!!!".toCharArray(), 0, 16, 0, 138);
		}else if(gameState.player1Turn) {
			g.drawChars("Player 1's turn".toCharArray(), 0, 15, 0, 138);
		} else {
			g.drawChars("Player 2's turn".toCharArray(), 0, 15, 0, 138);
		}
		String pPieceStr = "Player 1: " + p1Pieces;
		g.drawChars(pPieceStr.toCharArray(), 0, pPieceStr.length(), 0, 150);
		pPieceStr = "Player 2: " + p2Pieces;
		g.drawChars(pPieceStr.toCharArray(), 0, pPieceStr.length(), 64, 150);
	}
}
