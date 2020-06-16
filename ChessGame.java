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
