package backend;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GameDisplay {
    public static boolean turn;
    public static Label[][] boardLabels;
    public static Piece[][] board;
    
	public GameDisplay(Piece[][] b) {
		board = b;
	    boardLabels = new Label[8][8];
	    turn = true;
	}

    public void run(Piece[][] board) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Chess Board");
        shell.setLayout(new FillLayout());
        createBoard(shell, display);
        createInputSection(shell);
        shell.pack();
        shell.open();
        
        //chessBackend = new Index();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
    
    public void createBoard(Shell shell, Display display) {
        GridLayout gridLayout = new GridLayout(9, true);
        gridLayout.horizontalSpacing = 0;
        gridLayout.verticalSpacing = 0;
        shell.setLayout(gridLayout);

        // Create the top black line
        Composite topLineComposite = new Composite(shell, SWT.NONE);
        topLineComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 9, 1));
        topLineComposite.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
        GridLayout topLineLayout = new GridLayout(9, true);
        topLineLayout.marginWidth = 0;
        topLineLayout.marginHeight = 0;
        topLineComposite.setLayout(topLineLayout);

        // Create labels for the top line boxes
        for (int i = 0; i < 9; i++) {
            Label boxLabel = new Label(topLineComposite, SWT.CENTER);
            boxLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            boxLabel.setFont(new Font(display, "Helvetica", 45, SWT.BOLD));
            if (i > 0) {
                boxLabel.setText(String.valueOf((char) ('a' + i - 1)));
                boxLabel.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
                boxLabel.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
            }
        }    
        
        // Create labels for the rows and columns
        for (int i = 0; i < 8; i++) {
            char rowChar = (char) ('8' - i);
            Label rowLabel = new Label(shell, SWT.CENTER);
            rowLabel.setText(String.valueOf(rowChar));
            rowLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
            rowLabel.setFont(new Font(display, "Helvetica", 45, SWT.BOLD));
            rowLabel.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
            rowLabel.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
            
            for (int j = 0; j < 8; j++) {
                Label label = new Label(shell, SWT.CENTER);
                label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

                if (i % 2 == j % 2) {
                    label.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
                } else {
                    label.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
                }

                boardLabels[i][j] = label;
            }
        }
    }

    
    public static void createInputSection(Shell shell) {
    	Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));

        Label inputLabel = new Label(composite, SWT.NONE);
        inputLabel.setText("Enter move: \nExample: \"a2 a3\"");

        Text inputText = new Text(composite, SWT.BORDER);
        inputText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        GridData textGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        textGridData.horizontalSpan = 2; // Span across two columns
        inputText.setLayoutData(textGridData);

        Button enterButton = new Button(composite, SWT.PUSH);
        enterButton.setText("Enter");
        enterButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        //board on start
        updateBoardVisuals();

	        enterButton.addSelectionListener(new SelectionAdapter() {
	            
	        	@Override
	            public void widgetSelected(SelectionEvent e) {
	                String input = inputText.getText();
	                while (true) {
		                //UPDATE BOARD AND SEND TO UPDATEBOARDVISUALS
		        		//Used to take in inputs.
		        		String from = "";
		        		String to = ""; 
		        		
		        		//If true, white must move / If false, black must move
		                
	                    input = input.trim();
	                    String[] words = input.split("\\s+");
	                    
	                    //Check for end game command
	                    if(words.length == 1 && words[0].equals("done")) {
	                        System.exit(0);
	                    	return;
	                	//To many or to little words
	            		} else if(words.length != 2) {
	            			System.out.println("Invalid Input: To many / To few words");
	            			return;
	            		//Correct format
	            		} else {
	                        from = words[0];
	                        to = words[1];
	                        
	            			if(checkFormat(from, to)) {
	            				
	        	            	//Inputs are correct and game continues
	                    		//Converts from input values to 2D board values, for board object
	                    		char l = from.charAt(0);
	                    		int col = (l=='a' ? 0 : l=='b' ? 1 : l=='c' ? 2
	                    				: l=='d' ? 3 : l=='e' ? 4 : l=='f' ? 5
	                    				: l=='g' ? 6 : 7); 
	                    		int row = from.charAt(1) - '0';
	                    		row = 8 - row;
	                    		//Make sure player isnt moving empty square
	                    		if(board[row][col] != null) {
	        		        		Piece square = board[row][col];
	        		        		
	        		        		//Check colors turn
	        		        		if((turn == true && square.getColor().equals("white")) || 
	        		        		   (turn == false && square.getColor().equals("black"))) {
	        		        			//Upon entered movement, call 1 of 6 methods for piece movement
	        			        		Movement move = new Movement(board, square, from, to);
	        			        		//call method to see if move is even possible
	        			        		switch(move.getPiece().getName()) {
	        			        			case "pawn": move.movePawn();
	        			        				break;
	        			        			case "rook": move.moveRook();
	        			        				break;
	        			        			case "knight": move.moveKnight();
	        			        				break;
	        			        			case "bishop": move.moveBishop();
	        			        				break;
	        			        			case "queen": move.moveQueen();
	        			        				break;
	        			        			case "king": move.moveKing();
	        			        				break;
	        			        			default: System.out.println("Invalid move");
	        			        			//End of move
	        			        		}
	        			        		turn = !turn;
	        			                updateBoardVisuals();
	        			        		return;
	        		        		} else {
	        		        			System.out.println("Invalid Input: Other color turn");
	        	            			return;
	        		        		}
	        		        	} else {
	        		        		System.out.println("Invalid move: Square empty or wrong turn");
	    	            			return;
	                    		}
	            			} else System.out.println("Invalid Move: Formatting error");
	            				return;
	               		}         
		        	}
	        	}
	        });    
    }

    private static void updateBoardVisuals() {
        Display display = Display.getDefault();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Label label = boardLabels[i][j];

                // Clear any existing image
                label.setImage(null);
                
                // Assign image based on the piece value
                Image image = null;
                
                if(board[i][j] != null) {
	                // Get the piece value from the board array
	                String name = board[i][j].getName();
	                String color = board[i][j].getColor();
	                
	                if(color.equals("white")) {
		                switch (board[i][j].getName()) {
		                    case "pawn":
		                        image = new Image(display, Index.class.getResourceAsStream("/piecepng/whitepawn.png"));
		                        break;
		                    case "rook":
		                        image = new Image(display, Index.class.getResourceAsStream("/piecepng/whiterook.png"));
		                        break;
		                    case "knight":
		                        image = new Image(display, Index.class.getResourceAsStream("/piecepng/whiteknight.png"));
		                        break;
		                    case "king":
		                        image = new Image(display, Index.class.getResourceAsStream("/piecepng/whiteking.png"));
		                        break;
		                    case "queen":
		                        image = new Image(display, Index.class.getResourceAsStream("/piecepng/whitequeen.png"));
		                        break;
		                    case "bishop":
		                        image = new Image(display, Index.class.getResourceAsStream("/piecepng/whitebishop.png"));
		                        break;
		                    default:
		                        // No piece, empty square
		                        break;
		                }
	                } else {
	                	switch(name) {
			                case "pawn":
			                    image = new Image(display, Index.class.getResourceAsStream("/piecepng/blackpawn.png"));
			                    break;
			                case "rook":
			                    image = new Image(display, Index.class.getResourceAsStream("/piecepng/blackrook.png"));
			                    break;
			                case "knight":
			                    image = new Image(display, Index.class.getResourceAsStream("/piecepng/blackknight.png"));
			                    break;
			                case "queen":
			                    image = new Image(display, Index.class.getResourceAsStream("/piecepng/blackqueen.png"));
			                    break;
			                case "king":
			                    image = new Image(display, Index.class.getResourceAsStream("/piecepng/blackking.png"));
			                    break;
			                case "bishop":
			                    image = new Image(display, Index.class.getResourceAsStream("/piecepng/blackbishop.png"));
			                    break;
			                default:
			                	break;
	                	}
	                }
                }
                // Set the image on the label
                label.setImage(image);
            }
        }
    }
    
	public static boolean checkFormat(String str1, String str2) {
		char ch1 = str1.charAt(0);
		char ch2 = str1.charAt(1);
		char ch3 = str2.charAt(0);
		char ch4 = str2.charAt(1);
		//Check for two numbers and 2 char length
		if((Character.isDigit(ch2) && Character.isDigit(ch4)) &&
			(str1.length() == 2 && str2.length() == 2)) {
			//Check for two letters
			if(Character.isLetter(ch1) && Character.isLetter(ch3)) {
				return true;
			}
			return false;
		} 
		return false;
	}
}
