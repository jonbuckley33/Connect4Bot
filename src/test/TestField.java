package test;

import bot.Field;

public class TestField {

	public static void main(String[] args) {
		if (testEmptyField()) {
			System.out.println("PASSED testEmptyField");
		} else {
			System.out.println("FAILED testEmptyField");
		}
		
		if (testHorizontalWin()) {
			System.out.println("PASSED testHorizontalWin");
		} else {
			System.out.println("FAILED testHorizontalWin");
		}
		
		if (testVerticalWin()) {
			System.out.println("PASSED testVerticalWin");
		} else {
			System.out.println("FAILED testVerticalWin");
		}
		
		if (testDiagonalOne()) {
			System.out.println("PASSED testDiagonalOne");
		} else {
			System.out.println("FAILED testDiagonalOne");
		}
		
		if (testDiagonalTwo()) {
			System.out.println("PASSED testDiagonalTwo");
		} else {
			System.out.println("FAILED testDiagonalTwo");
		}
		
		if (testFull()) {
			System.out.println("PASSED testFull");
		} else {
			System.out.println("FAILED testFull");
		}
	}
	
	
	public static boolean testEmptyField() {
		Field f = new Field(4, 4);
		f.parseFromString("0,0,0,0;0,0,0,0;0,0,0,0;0,0,0,0");
		f.addDisc(2, 1);
		boolean passed = (f.isTerminal() == false);
		return passed;
	}
	
	public static boolean testHorizontalWin() {
		Field f = new Field(4, 4);
		f.parseFromString("0,0,0,0;0,0,0,0;1,1,0,1;1,2,2,1");
		f.addDisc(2, 1);
		boolean passed = (f.isTerminal() == true);
		return passed;
	}
	
	public static boolean testVerticalWin() {
		Field f = new Field(4, 4);
		f.parseFromString("0,0,0,2;2,2,0,1;1,2,2,1;1,2,2,1");
		f.addDisc(1, 2);
		boolean passed = (f.isTerminal() == true);
		return passed;
	}
	
	public static boolean testDiagonalOne() {
		Field f = new Field(4, 4);
		f.parseFromString("0,0,0,0;2,1,2,1;1,2,1,1;2,1,1,1");
		f.addDisc(3, 2);
		boolean passed = (f.isTerminal() == true);
		return passed;
	}
	
	public static boolean testDiagonalTwo() {
		Field f = new Field(4, 4);
		f.parseFromString("0,0,0,0;2,1,2,1;1,2,1,1;1,1,2,1");
		f.addDisc(0, 1);
		boolean passed = (f.isTerminal() == true);
		return passed;
	}
	
	public static boolean testFull() {
		Field f = new Field(4, 4);
		f.parseFromString("1,2,2,1;1,2,2,1;2,2,1,1;2,1,2,2");
		boolean passed = (f.isTerminal() == true);
		return passed;
	}

}
