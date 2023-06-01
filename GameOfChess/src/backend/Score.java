package backend;

public class Score {
	private int whiteScore;
	private int blackScore;
	Piece point;
	
	public Score(Piece point, int whiteScore, int blackScore) {
		this.point = point;
		this.whiteScore = 0;
		this.blackScore = 0;
	}
	
	public Score() {
	}
	
	public void take(Piece point) {
		if(point.getColor().equals("white")) {
			switch(point.getName()) {
				case "pawn": whiteScore+=1;
					break;
				case "knight": whiteScore+=3;
					break;
				case "bishop": whiteScore+=3;
					break;
				case "rook": whiteScore+=5;
					break;
				case "queen": whiteScore+=9;
					break;
				case "king": whiteScore+=100;
					break;
			}
		}
		if(point.getColor().equals("black")) {
			switch(point.getName()) {
				case "pawn": blackScore+=1;
					break;
				case "knight": blackScore+=3;
					break;
				case "bishop": blackScore+=3;
					break;
				case "rook": blackScore+=5;
					break;
				case "queen": blackScore+=9;
					break;
				case "king": blackScore+=100;
					break;
			}
		}
	}
	
	public int getWhiteScore() {
		return whiteScore;
	}
	
	public int getBlackScore() {
		return blackScore;
	}
	
}
