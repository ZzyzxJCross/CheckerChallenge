package checkers;

public class Board {
	public int[][] spaces;
	public boolean player1Turn;
	public int victoryState;
	public Board() {
		super();
		this.spaces = new int[8][8];
		this.player1Turn = true;
		this.victoryState = 0;
		populateBoard();
	}
	public void populateBoard() {
		spaces = new int[][]{{0,1,0,1,0,1,0,1},
		          {1,0,1,0,1,0,1,0},
		          {0,1,0,1,0,1,0,1},
		          {0,0,0,0,0,0,0,0},
		          {0,0,0,0,0,0,0,0},
		          {2,0,2,0,2,0,2,0},
		          {0,2,0,2,0,2,0,2},
		          {2,0,2,0,2,0,2,0}};
	}
	
	
}
