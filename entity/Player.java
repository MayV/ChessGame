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
