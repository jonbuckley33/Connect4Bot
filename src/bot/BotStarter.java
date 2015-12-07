// // Copyright 2015 theaigames.com (developers@theaigames.com)

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
import java.util.ArrayList;

/**
 * BotStarter class
 * 
 * Magic happens here. You should edit this file, or more specifically
 * the makeTurn() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotStarter {	
	int myDisc;
	int oppDisc;
	
	public Field field;
	Heuristic h;

	public BotStarter(Heuristic h, int myD, int oppD) {
		this.h = h;
		this.myDisc = myD;
		this.oppDisc = oppD;
	}
	
     /**
      * Enumerate possible moves to make for this given field.
      * @param f the field on which to generate moves.
      * @return list of columns where we can put discs.
      */
     private ArrayList<Integer> generateMoves(Field f) {
    	 ArrayList<Integer> moves = new ArrayList<Integer>();
    	 // Check if each col is a valid column to place a disc.
    	 for (int col = 0; col < field.getNrColumns(); col ++) {
    		 if (field.isValidMove(col)) {
    			 moves.add(col);
    		 }
    	 }
    	 
    	 return moves;
     }

     
     private class Tuple {
    	 public HeuristicValue Value;
    	 public int Column;
     }
     
     private Tuple alphabeta(Field f, int depth, float alpha, float beta, 
    		 boolean isMaxiPlayer) {
    	 Tuple toRet = new Tuple();
    	 
    	 // Terminal node.
    	 if (depth == 0 || f.isTerminal()) {
    		 toRet.Value = h.EvaluateField(f);
    		 return toRet;
    	 }
    	 
    	 // Generate all moves we can make for the current board;
		 ArrayList<Integer> moves = generateMoves(f);

    	 if (isMaxiPlayer) {
    		 float v = -Float.MAX_VALUE;
    		 int bestMove = -1;
    		 
    		 for (Integer m : moves) {
    			 Field child = f.DeepCopy();
    			 
    			 if (child.ourMove) {
        			 child.addDisc(m, myDisc);
    			 } else {
    				 child.addDisc(m, oppDisc);
    			 }
    			 
    			 HeuristicValue val = alphabeta(child, depth - 1, alpha, beta, false).Value;    			 
    			 if (val.value * val.probability > v) {
    				 bestMove = m;
    				 v = val.value * val.probability;
    			 }
    			 
    			 alpha = Math.max(alpha, v);
    			 
    			 if (beta <= alpha) {
    				 break;
    			 }
    		 }
    		 
    		 toRet.Value = new HeuristicValue(1.0f, v);
    		 toRet.Column = bestMove;
    		 return toRet;
    	 } else {
    		 float v = Float.MAX_VALUE;
    		 int bestMove = -1;
    		 
    		 for (Integer m : moves) {
    			 Field child = f.DeepCopy();
    			 
    			 if (child.ourMove) {
        			 child.addDisc(m, myDisc);
    			 } else {
    				 child.addDisc(m, oppDisc);
    			 }
    			 
    			 HeuristicValue val = alphabeta(child, depth - 1, alpha, beta, true).Value;
    			 if (val.value * val.probability < v) {
    				 bestMove = m;
    				 v = val.value * val.probability;
    			 }
    			 
    			 beta = Math.min(beta, v);
    			 
    			 if (beta <= alpha) {
    				 break;
    			 }
    		 }
    		 
    		 toRet.Value = new HeuristicValue(1.0f, v);
    		 toRet.Column = bestMove;
    		 return toRet;
    	 }    	 
     }
     
     /**
      * Makes a turn. Edit this method to make your bot smarter.
      *
      * @return The column where the turn was made.
      */
     public int makeTurn() { 
         return alphabeta(field, 2, -Float.MAX_VALUE, Float.MAX_VALUE, true).Column;
     }

     public void setField(Field f) {
    	 this.field = f;
     }
}