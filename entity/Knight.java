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
