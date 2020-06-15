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
