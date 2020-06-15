public class ChessBoard {
	private Piece[][] board;
	
	public ChessBoard(Player black, Player white) {
		this.board = new Piece[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				this.board[i][j] = null;
			}
		}
		
		this.board[0][1] = white.getKnight(0);
		white.getKnight(0).setPosition(new Position(0, 1));
		this.board[0][6] = white.getKnight(1);
		white.getKnight(1).setPosition(new Position(0, 6));
		this.board[0][3] = white.getQueen();
		white.getQueen().setPosition(new Position(0, 3));
		this.board[0][4] = white.getKing();
		white.getKing().setPosition(new Position(0, 4));
		
		this.board[7][1] = black.getKnights(0);
		black.getKnight(0).setPosition(new Position(7, 1));
		this.board[7][6] = black.getKnights(1);
		black.getKnight(1).setPosition(new Position(7, 6));
		this.board[7][3] = black.getQueen();
		black.getQueen().setPosition(new Position(7, 3));
		this.board[7][4] = black.getKing();
		black.getKing().setPosition(new Position(7, 4));
	}
	
	public Piece getPiece(Position pos) {
		return board[pos.x][pos.y];
	}
	
	public void setPiece(Piece piece, Position pos) {
		this.board[pos.x][pos.y] = piece;
	}
}
