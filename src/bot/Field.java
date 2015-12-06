// Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;
/**
 * Field class
 * 
 * Field class that contains the field status data and various helper functions.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class Field {	
	private int[][] mBoard;
	private int mCols = 0, mRows = 0;
	private String mLastError = "";
	public int mLastColumn = 0;
	public int mLastRow = 0;
	public boolean ourMove = false;
	
	public Field(int columns, int rows) {
		mBoard = new int[columns][rows];
		mCols = columns;
		mRows = rows;
		clearBoard();
	}
	
	public void setOurTurn() {
		ourMove = true;
	}
	
	/**
	 * Sets the number of columns (this clears the board)
	 * @param args : int cols
	 */
	public void setColumns(int cols) {
		mCols = cols;
		mBoard = new int[mCols][mRows];
	}

	/**
	 * Sets the number of rows (this clears the board)
	 * @param args : int rows
	 */
	public void setRows(int rows) {
		mRows = rows;
		mBoard = new int[mCols][mRows];
	}

	/**
	 * Clear the board
	 */
	public void clearBoard() {
		for (int x = 0; x < mCols; x++) {
			for (int y = 0; y < mRows; y++) {
				mBoard[x][y] = 0;
			}
		}
	}
	
	/**
	 * Adds a disc to the board
	 * @param args : command line arguments passed on running of application
	 * @return : true if disc fits, otherwise false
	 */
	public Boolean addDisc(int column, int disc) {
		mLastError = "";
		// Use opposite sign to denote opponent move.
		if (!ourMove) {
			disc = -disc;
		}
		// Switch turns.
		ourMove = !ourMove;
		if (column < mCols) {
			for (int y = mRows-1; y >= 0; y--) { // From bottom column up
				if (mBoard[column][y] == 0) {
					mBoard[column][y] = disc;
					mLastRow = y;
					mLastColumn = column;
					return true;
				}
			}
			mLastError = "Column is full.";
		} else {
			mLastError = "Move out of bounds.";
		}
		return false;
	}
	
	/**
	 * Initialise field from comma separated String
	 * @param String : 
	 */
	public void parseFromString(String s) {
		s = s.replace(';', ',');
		String[] r = s.split(",");
		int counter = 0;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mCols; x++) {
				mBoard[x][y] = Integer.parseInt(r[counter]); 
				counter++;
			}
		}
	}
	
	/**
	 * Returns the current piece on a given column and row
	 * @param args : int column, int row
	 * @return : int
	 */
	public int getDisc(int c, int r) {
		if (c >= 0 && c < mCols && r >= 0 && r < mRows) {
			return mBoard[c][r];
		}
		
		return -10;
	}
	
	/**
	 * Returns whether a slot is open at given column
	 * @param args : int column
	 * @return : Boolean
	 */
	public Boolean isValidMove(int column) {
		return (mBoard[column][0] == 0);
	}
	
	/**
	 * Returns reason why addDisc returns false
	 * @param args : 
	 * @return : reason why addDisc returns false
	 */
	public String getLastError() {
		return mLastError;
	}
	
	@Override
	/**
	 * Creates comma separated String with every cell.
	 * @param args : 
	 * @return : String
	 */
	public String toString() {
		String r = "";
		int counter = 0;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mCols; x++) {
				if (counter > 0) {
					r += ",";
				}
				r += mBoard[x][y];
				counter++;
			}
		}
		return r;
	}
	
	/**
	 * Checks whether the field is full
	 * @return : Returns true when field is full, otherwise returns false.
	 */
	public boolean isFull() {
		for (int x = 0; x < mCols; x++)
		  for (int y = 0; y < mRows; y++)
		    if (mBoard[x][y] == 0)
		      return false; // At least one cell is not filled
		// All cells are filled
		return true;
	}
	
	/**
	 * Checks whether the given column is full
	 * @return : Returns true when given column is full, otherwise returns false.
	 */
	public boolean isColumnFull(int column) {
		return (mBoard[column][0] != 0);
	}
	
	/**
	 * @return : Returns the number of columns in the field.
	 */
	public int getNrColumns() {
		return mCols;
	}
	
	/**
	 * @return : Returns the number of rows in the field.
	 */
	public int getNrRows() {
		return mRows;
	}
	
	public boolean isTerminal() {
		if (this.isFull()) {
			return true;
		}
		
		int disc = getDisc(mLastColumn, mLastRow);
		// Check horizontals.
		boolean horizontal = (getDisc(mLastColumn - 1, mLastRow) == disc
				&& getDisc(mLastColumn - 2, mLastRow) == disc
				&& getDisc(mLastColumn - 3, mLastRow) == disc) || 
				(getDisc(mLastColumn - 1, mLastRow) == disc
				&& getDisc(mLastColumn - 2, mLastRow) == disc
				&& getDisc(mLastColumn + 1, mLastRow) == disc) || 
				(getDisc(mLastColumn - 1, mLastRow) == disc
				&& getDisc(mLastColumn + 1, mLastRow) == disc
				&& getDisc(mLastColumn + 2, mLastRow) == disc) ||
				(getDisc(mLastColumn + 1, mLastRow) == disc
				&& getDisc(mLastColumn + 2, mLastRow) == disc
				&& getDisc(mLastColumn + 3, mLastRow) == disc);
		if (horizontal) {
			return true;
		}
		// Check verticals.
		boolean vertical = 
				(getDisc(mLastColumn, mLastRow + 1) == disc
				&& getDisc(mLastColumn, mLastRow + 2) == disc
				&& getDisc(mLastColumn, mLastRow + 3) == disc) ||
				(getDisc(mLastColumn, mLastRow + 1) == disc
				&& getDisc(mLastColumn, mLastRow + 2) == disc
				&& getDisc(mLastColumn, mLastRow - 1) == disc) ||
				(getDisc(mLastColumn, mLastRow + 1) == disc
				&& getDisc(mLastColumn, mLastRow - 1) == disc
				&& getDisc(mLastColumn, mLastRow - 2) == disc) ||
				(getDisc(mLastColumn, mLastRow - 1) == disc
				&& getDisc(mLastColumn, mLastRow - 2) == disc
				&& getDisc(mLastColumn, mLastRow - 3) == disc);
		if (vertical) {
			return true;
		}
		// Check top left to bottom right diagonal.
		boolean diagOne = (getDisc(mLastColumn - 1, mLastRow - 1) == disc
				&& getDisc(mLastColumn - 2, mLastRow - 2) == disc
				&& getDisc(mLastColumn - 3, mLastRow - 3) == disc) ||
				(getDisc(mLastColumn - 1, mLastRow - 1) == disc
				&& getDisc(mLastColumn - 2, mLastRow - 2) == disc
				&& getDisc(mLastColumn + 1, mLastRow + 1) == disc) ||
				(getDisc(mLastColumn - 1, mLastRow - 1) == disc
				&& getDisc(mLastColumn + 1, mLastRow + 1) == disc
				&& getDisc(mLastColumn + 2, mLastRow + 2) == disc) ||
				(getDisc(mLastColumn + 1, mLastRow + 1) == disc
				&& getDisc(mLastColumn + 2, mLastRow + 2) == disc
				&& getDisc(mLastColumn + 3, mLastRow + 3) == disc);
		if (diagOne) {
			return true;
		}
		// Check bottom left to top right diagonal.
		boolean diagTwo = (getDisc(mLastColumn + 1, mLastRow - 1) == disc
				&& getDisc(mLastColumn + 2, mLastRow - 2) == disc
				&& getDisc(mLastColumn + 3, mLastRow - 3) == disc) ||
				(getDisc(mLastColumn + 1, mLastRow - 1) == disc
				&& getDisc(mLastColumn + 2, mLastRow - 2) == disc
				&& getDisc(mLastColumn - 1, mLastRow + 1) == disc) ||
				(getDisc(mLastColumn + 1, mLastRow - 1) == disc
				&& getDisc(mLastColumn - 1, mLastRow + 1) == disc
				&& getDisc(mLastColumn - 2, mLastRow + 2) == disc) ||
				(getDisc(mLastColumn - 1, mLastRow + 1) == disc
				&& getDisc(mLastColumn - 2, mLastRow + 2) == disc
				&& getDisc(mLastColumn - 3, mLastRow + 3) == disc);
		return diagTwo;
	}
	
	public Field DeepCopy() {
		Field copy = new Field(mCols, mRows);
		copy.parseFromString(this.toString());
		
		return copy;
	}
}
