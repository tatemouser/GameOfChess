package backend;
/* a8 b8 c8 d8 e8 f8 g8 h8 0 black
 * a7 b7 c7 d7 e7 f7 g7 h7 1
 * a6 b6 c6 d6 e6 f6 g6 h6 2
 * a5 b5 c5 d5 e5 f5 g5 h5 3
 * a4 b4 c4 d4 e4 f4 g4 h4 4
 * a3 b3 c3 d3 e3 f3 g3 h3 5
 * a2 b2 c2 d2 e2 f2 g2 h2 6
 * a1 b1 c1 d1 e1 f1 g1 h1 7 white
 */
public class NewBoard {
	public Piece[][] board;
	
	public NewBoard(Piece[][] board){
		this.board = board;
	}
	public Piece[][] create() {

		Piece[][] board = new Piece[8][8];
		
	    //The following adds Piece instances to board 2D array
	    String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};		
	    //Pawn's
		for(int i = 0; i < letters.length; i++) {
			board[6][i] = new Piece("pawn", "white", letters[i] + "2");
			board[1][i] = new Piece("pawn", "black", letters[i] + "7");
		}
		//Rook's, Knight's, and Bishops's
		for(int i = 0; i < 3; i++) {
			String var = "";
			switch(i) {
				case 0: var = "rook";
						break;
				case 1: var = "knight";
						break;
				case 2: var = "bishop";
						break; 
			}
			board[0][i] = new Piece(var, "black", letters[i] + "8");
			board[0][7-i] = new Piece(var, "black", letters[7-i] + "8");
			board[7][i] = new Piece(var, "white", letters[i] + "1");
			board[7][7-i] = new Piece(var, "white", letters[7-i] + "1");
		} 
		//King and Queen's
		for(int i = 0; i < 8; i+=7) {
			String var = (i == 0) ? "black" : "white";
			String num = (i == 0) ? "8" : "1";
			board[i][3] = new Piece("queen", var, letters[3] + num);
			board[i][4] = new Piece("king", var, letters[4] + num);
		}
		//System.out.println(board[7][4].getName());
		//System.out.println(board[7][4].getColor());
		//System.out.println(board[7][4].currentSquare());
		
		return board;
	}
}