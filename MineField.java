package minesweeper;

import java.util.Random;

public class MineField {
	private int hight, width, minesPrecent;
	private Cell[][] field;
	private boolean won, flag;

	public MineField(int hight, int width, int minesPrecent) {
		this.hight = hight;
		this.width = width;
		this.minesPrecent = minesPrecent;
		field = new Cell[hight][width];

		Random rand = new Random();
		boolean[] mineArr = new boolean[hight * width];
		// generate randomly true or false by a given percentage
		while (areAllFalse(mineArr)) {
			for (int i = 0; i < hight * width; i++)
				mineArr[i] = (rand.nextInt(100) < this.minesPrecent);
		}

		// create cells for the field and giving them x,y and true or false of if there
		// is a mine
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				field[i][j] = new Cell(i, j, mineArr[i * width + j]);
			}
		}
		// calls for the function that updates the number of mines for each cell
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				updateAndConnect(i, j);
			}
		}
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	// checks and updates the number of mines around each cell
	// connects each cell with its neighbor
	private void updateAndConnect(int x, int y) {
		// updates only if you are not a mine
		// top left cell
		if (x - 1 >= 0 && y - 1 >= 0) {
			if (field[x - 1][y - 1].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setTopLeft(field[x - 1][y - 1]);
		}

		// top cell
		if (y - 1 >= 0) {
			if (field[x][y - 1].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setTop(field[x][y - 1]);
		}

		// top right cell
		if (x + 1 < hight && y - 1 >= 0) {
			if (field[x + 1][y - 1].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setTopRight(field[x + 1][y - 1]);
		}

		// left cell
		if (x - 1 >= 0) {
			if (field[x - 1][y].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setLeft(field[x - 1][y]);
		}

		// right cell
		if (x + 1 < width) {
			if (field[x + 1][y].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setRight(field[x + 1][y]);
		}

		// bottom left cell
		if (x - 1 >= 0 && y + 1 < hight) {
			if (field[x - 1][y + 1].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setBottomLeft(field[x - 1][y + 1]);
		}

		// bottom cell
		if (y + 1 < hight) {
			if (field[x][y + 1].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setBottom(field[x][y + 1]);
		}

		// bottom right cell
		if (x + 1 < width && y + 1 < hight) {
			if (field[x + 1][y + 1].isMine())
				field[x][y].setNum(field[x][y].getNum() + 1);
			field[x][y].setBottomRight(field[x + 1][y + 1]);
		}

		// sets a mines number to 9 for debugging
		if (field[x][y].isMine())
			field[x][y].setNum(9);
	}

	// Used to tell if there were no mines generated
	private boolean areAllFalse(boolean[] array) {
		for (boolean b : array)
			if (b)
				return false;
		return true;
	}

	// prints the mine field
	public void printField() {
		int topGuide = 1, sideGuide = 65;

		// printing the top guide for the field
		System.out.print("  ");
		for (int i = 0; i < width; i++) {
			System.out.print(topGuide++);
		}
		System.out.println();
		for (int i = 0; i < width + 2; i++) {
			System.out.print("-");
		}
		System.out.println();

		// printing the field itself and the side guide
		for (int i = 0; i < hight; i++) {
			System.out.print((char) sideGuide++ + "|");
			for (int j = 0; j < width; j++) {
				System.out.print(field[i][j]);
			}
			System.out.println();
		}
		
		
	topGuide = 1;
			sideGuide = 65;

		// printing the top guide for the field
		System.out.print("  ");
		for (int i = 0; i < width; i++)
			System.out.print(topGuide++);
		System.out.println();
		for (int i = 0; i < width + 2; i++)
			System.out.print("-");
		System.out.println();

		// printing the field itself and the side guide
		for (int i = 0; i < hight; i++) {
			System.out.print((char) sideGuide++ + "|");
			for (int j = 0; j < width; j++)
				System.out.print(field[i][j].getNum());
			System.out.println();
		}
	}

	//pressing to reveal cells in the game
	public void press(char x, char y) {
		
		x = Character.toUpperCase(x);
		int cellX = Character.getNumericValue(x-17);
		int cellY = Character.getNumericValue(y-1);
		
		//if flag is true then place a flag on the selected cell
		if(flag) {
			//if there is already a flag then remove it
			if(!field[cellX][cellY].isFlag())
				field[cellX][cellY].setFlag(true);
			else
				field[cellX][cellY].setFlag(false);
			printField();
			return;
		}
		//calling a recursive function that reveals the needed cells
		field[cellX][cellY].reveal();
		printField();
	}
	
	public boolean endGame() {
		//checking that the player didn't forget a cell
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				if(!field[i][j].isShown() && !field[i][j].isFlag()) {
					System.out.println("OOPS looks like you missed a cell");
					System.out.println("please cheack that each cell is reveald or with a flag");
					return false;
				}
			}
		}
		
		//check if the player placed a flag not on a mine
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				if(field[i][j].isFlag() && !field[i][j].isMine()) {
					System.out.println("=====GAME LOST=====");
					System.out.println("looks like to placed a flag not on a mine");
					return false;
				}
			}
		}
		System.out.println("=====GAME WON=====");
		return true;
	}
	
}
