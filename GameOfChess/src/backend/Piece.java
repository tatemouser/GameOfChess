package backend;

public class Piece {
	private String name;
	private String color;
	private String currentSquare;
	
	public Piece(String name, String color, String currentSquare) {
		this.name = name;
		this.color = color;
		this.currentSquare = currentSquare;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public String currentSquare() {
		return currentSquare;
	}
}