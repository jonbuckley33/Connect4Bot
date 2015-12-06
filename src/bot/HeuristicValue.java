package bot;

/**
 * The value of a game state, as defined by a Heuristic.
 * @author jonbuckley
 *
 */
public class HeuristicValue {
	public float probability;
	public float value;
	
	public HeuristicValue(float prob, float v) {
		this.probability = prob;
		this.value = v;
	}
}
