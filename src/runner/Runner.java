package runner;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import bot.BotParser;
import bot.Field;

public class Runner {
	static PipedOutputStream botInputIn;
	static PipedInputStream botInputOut;
	
	static PipedOutputStream botOutputIn;
	static PipedInputStream botOutputOut;
	
	static Scanner humanInput;
	
	static BotParser parser;
	static Field field;
	
	static int rows = 5, cols = 5;
	static int gameRound = 0;
	
	static int botDisc = 1, oppDisc = 2;
	
	public static void main(String[] args) {
		try {
			botInputOut = new PipedInputStream();
			botInputIn = new PipedOutputStream(botInputOut);
			botOutputOut = new PipedInputStream();
			botOutputIn = new PipedOutputStream(botOutputOut);
			
			humanInput = new Scanner(System.in);
			
			BotParser p = new BotParser(botInputOut, botOutputIn);
			Thread t = new Thread(p);
			t.start();
			
			parser = p;
			
			init();
			run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void init() {
		field = new Field(rows, cols);
		sendConfigInfo();
	}
	
	public static void writeOut(String s) {
		try {
			botInputIn.write(s.getBytes());
			botInputIn.write((byte) '\n');
			botInputIn.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendConfigInfo() {
		writeOut("settings timebank 10000");
		writeOut("settings time_per_move 500");
		writeOut("settings player_names player1,player2");
		writeOut("settings your_bot player1");
		writeOut("settings your_botid " + botDisc);
		writeOut("settings field_columns " + cols);
		writeOut("settings field_rows " + rows);
	}
	
	public static int getBotAction() {
		writeOut("action move 10000");
		byte[] buffer = new byte[1000];
		try {
			botOutputOut.read(buffer);
			String s = new String(buffer);
			// Parse for floats because can be negative and parseInt is dumb.
			float move = Float.parseFloat(s.split(" ")[1]);
			
			return (int) move;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static void println(String s) {
		System.out.println(s + "\n");
	}
	
	public static void updateBotField() {
		writeOut("update game round " + gameRound);
		writeOut("update game field " + field.toString());
	}
	
	public static void run() {
		System.out.println("Starting " + rows + " by " + cols + " game of connect 4. Board" +
				" starts as the following: \n" + field.toPrettyString());
		while (!field.isTerminal()) {
			updateBotField();
			int move = getBotAction();
			field.addDisc(move, botDisc);
			
			// Bot won.
			if (field.isTerminal()) {
				System.out.println("BOT WINS");
				break;
			}
			
			System.out.println(field.toPrettyString());
			
			System.out.println("\nWhat's your move?");
            String line = humanInput.nextLine();
            move = Integer.parseInt(line);
            
            field.addDisc(move, oppDisc);
            
            println("");
            
            // Human wins.
            if (field.isTerminal()) {
            	System.out.println("HUMAN WINS");
            	break;
            }
		}
	}

}
