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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * MyBot class
 * 
 * Main class that will keep reading output from the engine.
 * Will either update the bot state or get actions.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotParser implements Runnable {
    
	final Scanner scan;
    static BotStarter bot;
    
    private Field mField;
    public static int mBotId = 0;
    public boolean toldBotWhoStarts = false;
    public static OutputStream out;

    
    public BotParser(InputStream s, OutputStream o) {
		this.scan = new Scanner(s);
		out = o;
	}
    
    public static void writeOut(String s) {
    	try {
			out.write(s.getBytes());
			out.write((byte)'\n');
	    	out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void run() {
        mField = new Field(0, 0);
        while(scan.hasNextLine()) {
            String line = scan.nextLine();

            if(line.length() == 0) {
                continue;
            }

            String[] parts = line.split(" ");
            
            if(parts[0].equals("settings")) {
                if (parts[1].equals("field_columns")) {
                    mField.setColumns(Integer.parseInt(parts[2]));
                }
                if (parts[1].equals("field_rows")) {
                    mField.setRows(Integer.parseInt(parts[2]));
                }
                if (parts[1].equals("your_botid")) {
                    mBotId = Integer.parseInt(parts[2]);
                    int oppId = mBotId == 1 ? 2 : 1;
                    this.bot = new BotStarter(new SimpleHeuristic(), mBotId, oppId);
                }
            } else if(parts[0].equals("update")) { /* new field data */
                if (parts[2].equals("field")) {
                    String data = parts[3];
                    mField.parseFromString(data); /* Parse Field with data */
                    bot.setField(mField);
                }
            } else if(parts[0].equals("action")) {
            	if (!toldBotWhoStarts) {
            		toldBotWhoStarts = true;
            		bot.field.setOurTurn();
            	}
                if (parts[1].equals("move")) { /* move requested */
                    int column = bot.makeTurn();
                    writeOut("place_disc " + column);
                }
            }
            else { 
            	writeOut("unknown command");
            }
        }
    }

    public static void println(String s) {
    	System.out.println("[BOT]: " + s + "\n");
    }
    
    public static void main(String[] args) {
   	 BotParser parser = new BotParser(System.in, System.out);
   	 parser.run();
    }
}