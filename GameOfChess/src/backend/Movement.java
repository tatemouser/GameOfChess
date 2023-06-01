package backend;
/*	00 01 02 03 04 05 06 07
	10 11 12 13 14 15 16 17
	20 21 22 23 24 25 26 27
	30 31 32 33 34 35 36 37
	40 41 42 43 44 45 46 47
	50 51 52 53 54 55 56 57
	60 61 62 63 64 65 66 67
	70 71 72 73 74 75 76 77 Table to call square by 2D arary*/
public class Movement extends Index{
	private Piece piece;
	private String oldMove;
	private String newMove;
	private Piece[][] board;
    public String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private int newRow ;
    private int newCol;
    private int oldRow;
    private int oldCol;
    private Score score;
    
    //enter movement and board to create new board with new moves in account
	public Movement(Piece[][] board, Piece piece, String oldMove, String newMove) {
		this.board = board;
		this.piece = piece;
		this.oldMove = oldMove;
		this.newMove = newMove;
		this.newRow = findNewRow();
		this.newCol = findNewCol();
		this.oldRow = findOldRow();
		this.oldCol = findOldCol();
		this.score = new Score();
	}
	
	public Piece getPiece(){
		return piece;
	}
	
	public int getWhiteScore() {
		return score.getBlackScore();
	}
	
	public int getBlackScore() {
		return score.getWhiteScore();
	}
	
	//move___ methods move peice and call taking method if needed.
	public void movePawn() { 
		//Diagonal take
		if(board[newRow][newCol] != null && 	//Piece must exist in new square to take
		  (newRow == oldRow - 1 && newCol == oldCol - 1 || //Take left
		   newRow == oldRow - 1 && newCol == oldCol + 1)) { //Take right
			//Confirms different color
			if(!(board[newRow][newCol].getColor().equals(board[oldRow][oldCol].getColor()))) {
				score.take(board[newRow][newCol]);
				taking(); 
				move();
				endMoveMSG();
			} else System.out.println("Invalid Input: Must take opposite color");
			return;
		}

		//Forward one as black
		if(board[newRow][newCol] == null && newRow == oldRow+1) {
			move();
			endMoveMSG();
			return;
		}
		//Forward one as white
		if(board[newRow][newCol] == null && newRow == oldRow-1) {
			move();
			endMoveMSG();
			return;
		}
		//Foward two as white
		if((piece.getColor().equals("white") && //Find color
			oldRow == 6) && 	//Make sure its pawn first move
			board[oldRow-1][oldCol] == null ) { //Move sure one square foward is null
			
			move();
			endMoveMSG();
			return;
		}
		//Foward two as black
		if((piece.getColor().equals("black") && 
			oldRow == 1) && 
			board[oldRow+1][oldCol] == null ) {
			move();
			endMoveMSG();
			return;
		}
		System.out.println("Invalid Inputs");
	}
	
	
	public void moveRook() {
		//Moving right, check condition
		if(newCol > oldCol) {
			for(int i = oldCol+1; i < newCol; i++) {
				if(board[oldRow][i] != null) { //Piece in the way
					System.out.println("Invalid Input");
					return;
				}
			}
		}
		//Moving left, check condition
		if(newCol < oldCol) {
			for(int i = oldCol-1; i > newCol; i--) {
				if(board[oldRow][i] != null) { 
					System.out.println("Invalid Input");
					return;
				}
			}
		}
		//Moving down, check condition
		if(newRow > oldRow) {
			for(int i = oldRow+1; i < newRow; i++) {
				if(board[i][oldCol] != null) { 
					System.out.println("Invalid Input");
					return;
				}
			}
		}
		//Moving down, check condition
		if(newRow < oldRow) {
			for(int i = oldRow-1; i > newRow; i--) {
				if(board[i][oldCol] != null) { 
					System.out.println("Invalid Input");
					return;
				}
			}
		}
		//Passes other conditional branches to reach
		//Takes if needed, moves piece
		if(board[newRow][newCol] != null) {
			if(!(board[newRow][newCol].getColor().equals(board[oldRow][oldCol].getColor()))) {
				score.take(board[newRow][newCol]);
				taking(); 
				move();
				endMoveMSG();
			} else System.out.println("Invalid Input: Must take opposite color");
			return;
		} else {
			move();
			endMoveMSG();
		}

	}
	
	public void moveKnight() {
		if((newRow == oldRow-2 && newCol == oldCol+1) ||//Up 2 right one
		   (newRow == oldRow-2 && newCol == oldCol-1) ||//Up 2 left one
		   (newRow == oldRow+2 && newCol == oldCol+1) ||//Down 2 right one
		   (newRow == oldRow+2 && newCol == oldCol-1) ||//Down 2 left one
		   (newCol == oldCol+2 && newRow == oldRow-1) ||//Right 2 up one
		   (newCol == oldCol+2 && newRow == oldRow+1) ||//Right 2 down one
		   (newCol == oldCol-2 && newRow == oldRow-1) ||//Left 2 up one
		   (newCol == oldCol-2 && newRow == oldRow+1) ) {//Left 2 down one
			if(board[newRow][newCol] != null) {
				if(!(board[newRow][newCol].getColor().equals(board[oldRow][oldCol].getColor()))) {
					score.take(board[newRow][newCol]);
					taking(); 
					move();
					endMoveMSG();
				} else System.out.println("Invalid Input: Must take opposite color");
				return;
			}
			move();
			endMoveMSG();
			return;
		}
		System.out.println("Invalid Move");
	}
	
	public void moveBishop() {
		int i = 1;
		
		//Moving down-right
		if(newCol > oldCol && newRow > oldRow) {
			while((oldCol+i != newCol-1 && oldRow+i != newRow-1) && //Stops right before new move 
				  (newRow != oldRow+1 && newCol != oldCol+1)) { 	//If one square move, can be no piece blocking, break;
				if(board[oldRow+i][oldCol+i] != null) {
					System.out.println("Invalid Input: Bishop Down Right");
					return;
				}
				i++;
			}
		}
		//Moving up-right
		if(newCol > oldCol && newRow < oldRow) {
			while((oldCol+i != newCol-1 && oldRow-i != newRow+1) && 
				  (newRow != oldRow-1 && newCol != oldCol+1)) {
				if(board[oldRow-i][oldCol+i] != null) {
					System.out.println("Invalid Input: Bishop Up Right");
					return;
				}
				i++;
			}
		} 
		//Moving down-left
		if(newCol < oldCol && newRow > oldRow) {
			while((oldCol-i != newCol+1 && oldRow+i != newRow-1) && 
				  (newRow != oldRow+1 && newCol != oldCol-1)) {
				if(board[oldRow+i][oldCol-i] != null) {
					System.out.println("Invalid Input: Bisop Down Left");
					return;
				}
				i++;
			}
		}
		//Moving up-left
		if(newCol < oldCol && newRow < oldRow) {
			while((oldCol-i != newCol+1 && oldRow-i != newRow+1) && 
				  (newRow != oldRow-1 && newCol != oldCol-1)) {		
				if(board[oldRow-i][oldCol-i] != null) {
					System.out.println("Invalid Input: Bishop Up Left");
					return;
				}
				i++;
			}
		}
		//Passes other conditional branches to reach
		//Takes if needed, moves piece
		if(board[newRow][newCol] != null) {
			if(!(board[newRow][newCol].getColor().equals(board[oldRow][oldCol].getColor()))) {
				score.take(board[newRow][newCol]);
				taking(); 
				move();
				endMoveMSG();
			} else System.out.println("Invalid Input: Must take opposite color");
			return;
		} else {
			move();
			endMoveMSG();
		}
		
	}
	
	public void moveQueen() {
		boolean passedThrough = false;
		//SWITCH TO BISHOP MOVES
		int j = 1;
		
		//Moving down-right
		if(newCol > oldCol && newRow > oldRow) {
			while((oldCol+j != newCol-1 && oldRow+j != newRow-1) && //Stops right before new move 
				  (newRow != oldRow+1 && newCol != oldCol+1)) { 	//If one square move, can be no piece blocking, break;
				if(board[oldRow+j][oldCol+j] != null) {
					System.out.println("Invalid Input: Bishop Down Right");
					return;
				}
				j++;
			}
			passedThrough = true;
		}
		//Moving up-right
		if(newCol > oldCol && newRow < oldRow) {
			while((oldCol+j != newCol-1 && oldRow-j != newRow+1) && 
				  (newRow != oldRow-1 && newCol != oldCol+1)) {
				if(board[oldRow-j][oldCol+j] != null) {
					System.out.println("Invalid Input: Bishop Up Right");
					return;
				}
				j++;
			}
			passedThrough = true;
		} 
		//Moving down-left
		if(newCol < oldCol && newRow > oldRow) {
			while((oldCol-j != newCol+1 && oldRow+j != newRow-1) && 
				  (newRow != oldRow+1 && newCol != oldCol-1)) {
				if(board[oldRow+j][oldCol-j] != null) {
					System.out.println("Invalid Input: Bisop Down Left");
					return;
				}
				j++;
			}
			passedThrough = true;
		}
		//Moving up-left
		if(newCol < oldCol && newRow < oldRow) {
			while((oldCol-j != newCol+1 && oldRow-j != newRow+1) && 
				  (newRow != oldRow-1 && newCol != oldCol-1)) {		
				if(board[oldRow-j][oldCol-j] != null) {
					System.out.println("Invalid Input: Bishop Up Left");
					return;
				}
				j++;
			}
			passedThrough = true;
		}
		//ROOK MOVES
		//Moving right, check condition
		if(newCol > oldCol && newRow == oldRow) {
			for(int i = oldCol+1; i < newCol; i++) {
				if(board[oldRow][i] != null) { //Piece in the way
					System.out.println("Invalid Input: Queen Right");
					return;
				}
			}
			passedThrough = true;
		}
		//Moving left, check condition
		if(newCol < oldCol && newRow == oldRow) {
			for(int i = oldCol-1; i > newCol; i--) {
				if(board[oldRow][i] != null) { 
					System.out.println("Invalid Input: Queen Left");
					return;
				}
			}
			passedThrough = true;
		}
		//Moving down, check condition
		if(newRow > oldRow && newCol == oldCol) {
			for(int i = oldRow+1; i < newRow; i++) {
				if(board[i][oldCol] != null) { 
					System.out.println("Invalid Input: Queen Down");
					return;
				}
			}
			passedThrough = true;
		}
		//Moving down, check condition
		if(newRow < oldRow && oldCol == newCol) {
			for(int i = oldRow-1; i > newRow; i--) {
				if(board[i][oldCol] != null) { 
					System.out.println("Invalid Input: Queen Up");
					return;
				}
			}
			passedThrough = true;
		}
		
		/*Check to see if newMove fits movement parameters for conditional
		  branches above, if so move as fit. */
		if(passedThrough == true) {
			if(board[newRow][newCol] != null) {
				if(!(board[newRow][newCol].getColor().equals(board[oldRow][oldCol].getColor()))) {
					score.take(board[newRow][newCol]);
					taking(); 
					move();
					endMoveMSG();
				} else System.out.println("Invalid Input: Must take opposite color");
				return;
			}
			move();
			endMoveMSG();
			return;
		}
		System.out.println("Invalid Move");
	}
	
	public void moveKing() {
		//Castling white
		if((board[oldRow][oldCol].getColor().equals("white")) && (newRow == 7) && (board[7][4].getName().equals("king"))) {
			//Castling Right
			if(newCol == 6) {
				//Confirm Rook
				if(board[7][7].getName().equals("rook")) {
					//Confirm empty spaces
					if(board[7][6] == null && board[7][5] == null){
						board[7][5] = board[7][7];
						board[7][7] = null;
						move();
						endMoveMSG();
					}
				}
			}
			//Castling Left
			if(newCol == 2) {
				//Confirm Rook
				if(board[7][0].getName().equals("rook")) {
					//Confirm empty spaces
					if(board[7][1] == null && board[7][2] == null && board[7][3] == null){
						board[7][3] = board[7][0];
						board[7][0] = null;
						move();
						endMoveMSG();
					}
				}
			}
		}
		//Castling as black
		if((board[oldRow][oldCol].getColor().equals("black")) && (newRow == 0) && (board[0][4].getName().equals("king"))) {
			if(newCol == 6) {
				if(board[0][7].getName().equals("rook")) {
					if(board[0][6] == null && board[0][5] == null){
						board[0][5] = board[0][7];
						board[0][7] = null;
						move();
						endMoveMSG();
					}
				}
			}
			if(newCol == 2) {
				if(board[0][0].getName().equals("rook")) {
					if(board[0][1] == null && board[0][2] == null && board[0][3] == null){
						board[0][3] = board[0][0];
						board[0][0] = null;
						move();
						endMoveMSG();
					}
				}
			}
		}
		
		if((newRow == oldRow+1 && newCol == oldCol) || //right 
		   (newRow == oldRow-1 && newCol == oldCol) || //left
		   (newCol == oldCol+1 && newRow == oldRow) || //up
		   (newCol == oldCol-1 && newRow == oldRow) || //down
		   (newCol == oldCol+1 && newRow == oldRow+1) || //right-down
		   (newCol == oldCol-1 && newRow == oldRow-1) || //left-down
		   (newRow == oldRow-1 && newCol == oldCol-1) || //up-left
		   (newRow == oldRow-1 && newCol == oldCol+1) //up-right
		   ) {
			if(board[newRow][newCol] != null) {
				if(!(board[newRow][newCol].getColor().equals(board[oldRow][oldCol].getColor()))) {
					score.take(board[newRow][newCol]);
					taking(); 
					move();
					endMoveMSG();
				} else System.out.println("Invalid Input: Must take opposite color");
				return;
			}
			move();
			endMoveMSG();
			return;
		}
		System.out.println("Invalid Move");
	}
	
	//If newMove is null, call taking to delete piece from board
	public void taking() {
		int num = findNewRow();
		int letter = findNewCol();  
		board[8-num][letter] = null;
	}
	//Move to new square and set old square to null
	public void move() {
		board[newRow][newCol] = board[oldRow][oldCol];
		board[oldRow][oldCol] = null;
	}
	
	
	//Next four methods convert inputs to array inputs , both old nad new moves
	public int findNewCol() {
		char l = newMove.charAt(0);
		int letter = (l=='a' ? 0 : l=='b' ? 1 : l=='c' ? 2
				: l=='d' ? 3 : l=='e' ? 4 : l=='f' ? 5
				: l=='g' ? 6 : 7);  
		return letter;
	}
	public int findNewRow() {
		int num = newMove.charAt(1) - '0';
		return 8 - num;
	}
	public int findOldCol() {
		char l = oldMove.charAt(0);
		int letter = (l=='a' ? 0 : l=='b' ? 1 : l=='c' ? 2
				: l=='d' ? 3 : l=='e' ? 4 : l=='f' ? 5
				: l=='g' ? 6 : 7);  
		return letter;
	}
	public int findOldRow() {
		int num = oldMove.charAt(1) - '0';
		return 8 - num;
	}

	public void endMoveMSG() {
		System.out.println("Nice Move");
	}
}
