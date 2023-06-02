package backend;

public class Index {	
    
	public static void main(String args[]) {

		//Scanner stdin = new Scanner(System.in);
		//Create array
		Piece[][] temp = new Piece[8][8];
		//Create board object to reference fill board,  create();
		NewBoard temp1 = new NewBoard(temp);
		//Fill array with board objects
		Piece[][] board = temp1.create();

		//Instantate visuals with board array parameter
		GameDisplay gameDisplay = new GameDisplay(board);
		//start visual display shell
		gameDisplay.run(board);

        System.out.println("Thanks for playing!");
	}
}
 