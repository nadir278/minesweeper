package minesweeper;

import java.util.Scanner;

public class MinesMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean didFinish = false;
		MineField game = new MineField(9, 9, 20);
		game.printField();

		Scanner scanner = new Scanner(System.in);
		while (!game.isWon()) {
			System.out.println("select cell: ");
			String select = scanner.next();
			//set that the player wants to place a flag
			if (select.equalsIgnoreCase("flag")) {
				if(!game.isFlag())
					game.setFlag(true);
				else
					game.setFlag(false);
			}
			else if(select.equalsIgnoreCase("finish")) {
				didFinish = game.endGame();
				if(didFinish)
					break;
			}
			else
				game.press(select.charAt(0), select.charAt(1));
		}
	}
	
	private void printStart() {
		System.out.println("WELCOME TO MINESWEEPAER");
		System.out.println("");
	}

}
