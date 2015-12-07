package bot;

public class Heuristic1 implements Heuristic {

	@Override
	public HeuristicValue EvaluateField(Field f) {
		if (f.isTerminal()) {
			if (!f.ourMove) {
				HeuristicValue val = new HeuristicValue(1.0f, 1000.0f);
				return val;
			} else {
				HeuristicValue val = new HeuristicValue(1.0f, -1000.0f);
				return val;
			}
		}
        Float value = 0.0f;

        // Rows
        for (int i=0; i<f.getNrRows(); i++) {
            int curr = 0;
            int run = 0;
            for (int j=0; j<f.getNrColumns(); j++) {
                int disc = f.getDisc(j,i);
                if (disc == curr) {
                    run++;
                } else {
                    Float delta;
                    if (run == 1) {
                        delta = 1.0f;
                    } else if (run == 2) {
                        delta = 3.0f;
                    } else {
                        delta = 10.0f;
                    }
                    if (curr == 0) {
                        delta = 0.0f;
                    }
                    if (curr == 2) {
                        delta = -delta;
                    }
                    value += delta;

                    curr = disc;
                    run = 1;
                }
            }
            Float delta;
            if (run == 1) {
                delta = 1.0f;
            } else if (run == 2) {
                delta = 3.0f;
            } else {
                delta = 10.0f;
            }
            if (curr == 0) {
                delta = 0.0f;
            }
            if (curr == 2) {
                delta = -delta;
            }
            value += delta;
        }
		
		return new HeuristicValue(1.0f, value);
	}

}
