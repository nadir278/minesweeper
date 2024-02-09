package minesweeper;

public class Cell {
	private boolean shown = false, flag = false, mine = false;
	private int x, y, num;
	private Cell topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight;

	public Cell(int x, int y, boolean mine) {
		this.x = x;
		this.y = y;
		this.mine = mine;
	}

	public boolean isMine() {return mine;}

	public void setMine(boolean mine) {this.mine = mine;}

	public boolean isShown() {return shown;}

	public void setShown(boolean shown) {this.shown = shown;}

	public boolean isFlag() {return flag;}

	public void setFlag(boolean flag) {this.flag = flag;}

	public int getX() {return x;}

	public int getY() {return y;}

	public int getNum() {return num;}

	public void setNum(int num) {this.num = num;}

	public void setTop(Cell top) {this.top = top;}

	public void setLeft(Cell left) {this.left = left;}

	public void setRight(Cell right) {this.right = right;}

	public void setBottom(Cell bottom) {this.bottom = bottom;}

	public void setTopLeft(Cell topLeft) {this.topLeft = topLeft;}

	public void setTopRight(Cell topRight) {this.topRight = topRight;}

	public void setBottomLeft(Cell bottomLeft) {this.bottomLeft = bottomLeft;}

	public void setBottomRight(Cell bottomRight) {this.bottomRight = bottomRight;}

	// reveals the cells
	public void reveal() {
		if(shown || mine)
			return;
		if(num!=0 && !mine) {
			shown = true;
			return;
		}
		shown = true;
		if (bottom != null) {
			bottom.reveal();
		}

		
		if (bottom != null)
			bottom.reveal();
		if (bottomLeft != null)
			bottomLeft.reveal();
		if (bottomRight != null)
			bottomRight.reveal();
		if (top != null)
			top.reveal();
		if (topRight != null)
			topRight.reveal();
		if (topLeft != null)
			topLeft.reveal();
		if (left != null)
			left.reveal();
		if (right != null)
			right.reveal();
		
	}

	//return the value that needs to be shown for the cell
	public String toString() {
		if (!shown && !flag) {
			return "•";
		} else if (flag) {
			return "F";
		} else if (mine) {
			return "X";
		} else if (num == 0) {
			return " ";
		} else {
			return Integer.toString(num);
		}

	}
}
