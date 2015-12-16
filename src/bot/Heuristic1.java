package bot;

// Simply weight rows of coins
public class Heuristic1 implements Heuristic {

    private float CountRows(Field f) {
        float value = 0.0f;
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
                        delta = 5.0f;
                    } else {
                        delta = 30.0f;
                    }
                    if (curr == 0) {
                        delta = 0.0f;
                    } else if (curr == 2) {
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
                delta = 5.0f;
            } else {
                delta = 30.0f;
            }
            if (curr == 0) {
                delta = 0.0f;
            } else if (curr == 2) {
                delta = -delta;
            } else {
                delta += 1.0f;
            }
            value += delta;
        }

        return value;
    }

    private float CountCols(Field f) {
        float value = 0.0f;
        for (int j=0; j<f.getNrColumns(); j++) {
            int curr = 0;
            int run = 0;
            for (int i=0; i<f.getNrRows(); i++) {
                int disc = f.getDisc(j,i);
                if (disc == curr) {
                    run++;
                } else {
                    Float delta;
                    if (run == 1) {
                        delta = 1.0f;
                    } else if (run == 2) {
                        delta = 5.0f;
                    } else {
                        delta = 30.0f;
                    }
                    if (curr == 0) {
                        delta = 0.0f;
                    } else if (curr == 2) {
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
                delta = 5.0f;
            } else {
                delta = 30.0f;
            }
            if (curr == 0) {
                delta = 0.0f;
            } else if (curr == 2) {
                delta = -delta;
            } else {
                delta += 1.0f;
            }
            value += delta;
        }

        return value;
    }

    private float CountRDiags(Field f) {
        float value = 0.0f;
        for (int i=0; i<f.getNrColumns()+f.getNrRows()-1; i++) {
            int curr = 0;
            int run = 0;
            for (int j=0; j<Math.max(f.getNrColumns(),f.getNrRows()); j++) {
                if (j<f.getNrColumns() && i-j<f.getNrRows() && i-j>=0) {
                    int disc = f.getDisc(j,i-j);
                    if (disc == curr) {
                        run++;
                    } else {
                        Float delta;
                        if (run == 1) {
                            delta = 1.0f;
                        } else if (run == 2) {
                            delta = 5.0f;
                        } else {
                            delta = 30.0f;
                        }
                        if (curr == 0) {
                            delta = 0.0f;
                        } else if (curr == 2) {
                            delta = -delta;
                        }
                        value += delta;

                        curr = disc;
                        run = 1;
                    }
                }
            }
            Float delta;
            if (run == 1) {
                delta = 1.0f;
            } else if (run == 2) {
                delta = 5.0f;
            } else {
                delta = 30.0f;
            }
            if (curr == 0) {
                delta = 0.0f;
            } else if (curr == 2) {
                delta = -delta;
            } else {
                delta += 1.0f;
            }
            value += delta;
        }

        return value;
    }

    private float CountLDiags(Field f) {
        float value = 0.0f;
        for (int i=0; i<f.getNrColumns()+f.getNrRows()-1; i++) {
            int curr = 0;
            int run = 0;
            for (int j=0; j<Math.max(f.getNrColumns(),f.getNrRows()); j++) {
                if (j<f.getNrColumns() && i-j<f.getNrRows() && i-j>=0) {
                    int disc = f.getDisc(f.getNrColumns()-1-j,i-j);
                    if (disc == curr) {
                        run++;
                    } else {
                        Float delta;
                        if (run == 1) {
                            delta = 1.0f;
                        } else if (run == 2) {
                            delta = 5.0f;
                        } else {
                            delta = 30.0f;
                        }
                        if (curr == 0) {
                            delta = 0.0f;
                        } else if (curr == 2) {
                            delta = -delta;
                        }
                        value += delta;

                        curr = disc;
                        run = 1;
                    }
                }
            }
            Float delta;
            if (run == 1) {
                delta = 1.0f;
            } else if (run == 2) {
                delta = 5.0f;
            } else {
                delta = 30.0f;
            }
            if (curr == 0) {
                delta = 0.0f;
            } else if (curr == 2) {
                delta = -delta;
            } else {
                delta += 1.0f;
            }
            value += delta;
        }

        return value;
    }

	@Override
	public HeuristicValue EvaluateField(Field f) {
		if (f.isTerminal()) {
			if (!f.ourMove) {
				HeuristicValue val = new HeuristicValue(1.0f, 1000000.0f);
				return val;
			} else if (f.isFull()) {
                return new HeuristicValue(1.0f, 0.0f);
            } else {
				HeuristicValue val = new HeuristicValue(1.0f, -1000000.0f);
				return val;
			}
		}
        Float value = 0.0f;

        value += CountRows(f);
        value += CountCols(f);
        value += CountRDiags(f);
        value += CountLDiags(f);
		
		return new HeuristicValue(1.0f, value);
	}

}
