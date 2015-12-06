package bot;

public class SimpleHeuristic implements Heuristic {

	@Override
	public HeuristicValue EvaluateField(Field f) {
		if (f.isTerminal()) {
			if (!f.ourMove) {
				HeuristicValue val = new HeuristicValue(1.0f, 100.0f);
				return val;
			} else {
				HeuristicValue val = new HeuristicValue(1.0f, -100.0f);
				return val;
			}
		}
		
		return new HeuristicValue(1.0f, 0.0f);
	}

}
