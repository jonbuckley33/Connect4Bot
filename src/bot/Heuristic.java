package bot;

public interface Heuristic {
	/**
	 * Evaluates a game state, f, and returns the value. Note
	 * that each HeuristicValue has a probability and a value
	 * associated with it.
	 * 
	 * @param f the game state to evaluate.
	 * @return the value of the given game state.
	 */
	public HeuristicValue EvaluateField(Field f);
}