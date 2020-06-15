public class Position {
	private int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
 
public abstract class Piece {
	private Position pos;
	public final boolean isWhite;
	private boolean isAlive;
	
	public Piece(boolean isWhite) {
		this.isWhite = isWhite;
		this.isAlive = true;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean getIsAlive() {
		return isAlive;
	}
	
	public abstract boolean checkMove(Position dest);
}

public class King extends Piece{
	
	public King(boolean isWhite) {
		super(isWhite);
	}

	public boolean checkMove(Position dest) {
		//Code to check if the move is valid or not.
	}
}

public class Queen extends Piece{
	
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public boolean checkMove(Position dest) {
		//Code to check if the move is valid or not.
	}
}

public class Knight extends Piece{
	
	public Knight(boolean isWhite) {
		super(isWhite);
	}
	
	public boolean checkMove(Position dest) {
		if(dest.x<0 || dest.x>7 || dest.y<0 || dest.y>7) {
			return false;
		}
		
		//If current piece can move to that position.
		int[] tx = new int[]{-2, -2, -1, 1, 2, 2, 1, -1};
		int[] ty = new int[]{-1, 1, 2, 2, 1, -1, -2, -2};
		boolean result = false;
		
		for(int i=0;i<8;i++) {
			int x = this.position.getX();
			int y = this.position.getY();
			if(x+tx[i]>=0 && x+tx[i]<8 && y+ty[i]>=0 && y+ty[i]<8 && x+tx[i]==dest.x && y+ty[i]==dest.y) {
				result = true;
			}
		}
		
		return result;
	}
}

public class Move {
	private Position src, dest;
	
	public Position getSrc() {
		return src;
	}

	public Position getDest() {
		return dest;
	}

	//Initialize setters.
}

public class Player {
	private final boolean isWhite;
	
	private King king;
	private Queen queen;
	private Knight[] knights;
	
	public Player(boolean isWhite) {
		this.isWhite = isWhite;
		this.king = new King(isWhite);
		this.queen = new Queen(isWhite);
		this.knights = new Knight[2];
		knights[0] = new Knight(isWhite);
		knights[1] = new Knight(isWhite);
	}

	//Initialize setters and getters.
	
	public Move takeMoveInput() {
		Move move = new Move();
		//Take inputs from user and put those inputs in move object.
		return move;
	}

	public void showOutput(GameStatus status) {
		//Show output accordind to the status.
	}

}

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

public enum GameStatus {
	ACTIVE,
	ERROR,
	CHECK,
	DRAW,
	BLACK_WINS,
	WHITE_WINS;
}

public class ChessGame {
	private Player black;
	private Player white;
	
	private ChessBoard chessBoard;
	
	private boolean whiteTurn;
	private GameStatus status;
	
	public ChessGame() {
		this.black = new Player(false);
		this.white = new Player(true);
		
		this.chessBoard = new ChessBoard(black, white);
		
		this.whiteTurn = true;
		this.status = GameStatus.ACTIVE;
	}
		
	public int makeTurn() {
		//Return -1 if turn is invalid, else return 1. 
		Player current, other;
		if(this.whiteTurn) {
			current = white;
			other = black;
		}
		else {
			current = black;
			other = white;
		}
		
		Move move = current.takeMoveInput();
		//Error while taking input.
		if(move==null) {
			return -1;
		}
		
		Piece pieceAtSrc = this.chessBoard.getPiece(move.getSrc());
		if(pieceAtSrc==null) {
			return -1;
		}
		//Piece color and Player color should be same.
		if(current.isWhite != pieceAtSrc.isWhite) {
			return -1;
		}
		
		Piece pieceAtDest = chessBoard.getPiece(move.getDest());
		if(pieceAtDest != null && pieceAtSrc.isWhite == pieceAtDest.isWhite) {
			return -1;
		}
		
		boolean isValid = pieceAtSrc.checkMove(move.getDest());
		if(!isValid) {
			return -1;
		}
		else{
			pieceAtSrc.setPosition(move.getDest());
			this.chessBoard.setPiece(null, move.getSrc());
			this.chessBoard.setPiece(pieceAtSrc, move.getDest());
			if(pieceAtDest!=null) {
				pieceAtDest.setPosition(new Position(-1, -1));
				pieceAtDest.setIsAlive(false);
			}
			
			//Check if current's king is getting into danger with this move.
			boolean isCurrentKingSafe = isCheck(other, current);
			
			//If the current player's king is not safe with the current player's move, move is not valid.
			//Backtrack all the changes made.
			if(!isCurrentKingSafe) {
				pieceAtSrc.setPosition(move.getSrc());
				this.chessBoard.setPiece(pieceAtSrc, move.getSrc());
				if(pieceAtDest != null) {
					pieceAtDest.setIsAlive(true);
					pieceAtDest.setPosition(move.getDest());
					this.chessBoard.setPiece(pieceAtDest, move.getDest());
				}
				else {
					this.chessBoard.setPiece(null, move.getDest());
				}
				return -1;
			}
		}
		
		return 1;
	}

	public boolean isAttackingSpot(Player player, Position dest) {
		boolean flag = false;
		flag = flag | player.getKing().checkMove(chessBoard, dest);
		if(this.queen.isAlive)
			flag = flag | player.getQueen().checkMove(chessBoard, dest);
		if(this.knights[0].isAlive)
			flag = flag | player.getKnight(0).checkMove(chessBoard, dest);
		if(this.knights[1].isAlive)
			flag = flag | player.getKnights(1).checkMove(chessBoard, dest);
		
		return flag;
	}

	public boolean isCheck(Player current, Player other) {
		//Check if other's king is being attacked by current player.
		Position otherKingPos = other.king.getPosition();
		boolean check = isAttackingAtSpot(current, otherKingPos);
		return check;
	}	

	public boolean isCheckMate() {
		//If king is in danger, king cannot move and no other piece can shield the king. 
	}

	public boolean isStaleMate(Player current, Player other) {
		/*Stalemate is a situation in the game of chess where the player whose turn it is to move is not in check but has no legal move.*/

		/*For each alive piece of the other player, move the piece to all the valid positions it can move and check if the king is safe or not. */

		//Implement using Backtracking algorithm.
	}

	public void startGame() {
		GameStatus status = GameStatus.ACTIVE;
		while(status == GameStatus.ACTIVE || status == GameStatus.CHECK) {
			int ok = this.makeTurn();
			if(ok==-1) {
				status = GameStatus.ERROR;
				break;
			}
			boolean over = this.isCheckMate();
			if(over) {
				if(this.whiteTurn) {
					status = GameStatus.WHITE_WINS;
				}
				else {
					status = GameStatus.BLACK_WINS;
				}
				break;
			}
			boolean stale = this.isStaleMate();
			if(stale) {
				status = GameStatus.DRAW;
			}
			Player current, other;
			if(this.whiteTurn) {
				current = white;
				other = black;
			}
			else {
				current = black;
				other = white;
			}
			boolean check = this.isCheck(current, other);
			if(check) {
				status = GameStatus.CHECK;
				//Show output to other player (CHECK).
			}
			else {
				status = GameStatus.ACTIVE;
			}
			this.whiteTurn = !this.whiteTurn;
		}
		//Show output according to the game Status (ERROR, DRAW, BLACK_WINS, WHITE_WINS).
	}
}
