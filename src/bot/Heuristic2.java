package bot;

// Weight only rows of coins which can potentially 4-in-row
public class Heuristic2 implements Heuristic {

    private boolean IsPossible(Field f, int i, int j, int dir) {
        int disc = f.getDisc(j,i);
        int possible = 1;
        int j2,j3,i2,i3;

        switch (dir) {
            case 1:
                j2 = j+1;
                while (f.getDisc(j2,i) != -10 && (f.getDisc(j2,i) == 0 || f.getDisc(j2,i) == disc)) {
                    possible++;
                    j2++;
                }
                j3 = j-1;
                while (f.getDisc(j3,i) != -10 && (f.getDisc(j3,i) == 0 || f.getDisc(j3,i) == disc)) {
                    possible++;
                    j3--;
                }
                break;
            
            case 2:
                i2 = i+1;
                while (f.getDisc(j,i2) != -10 && (f.getDisc(j,i2) == 0 || f.getDisc(j,i2) == disc)) {
                    possible++;
                    i2++;
                }
                i3 = i-1;
                while (f.getDisc(j,i3) != -10 && (f.getDisc(j,i3) == 0 || f.getDisc(j,i3) == disc)) {
                    possible++;
                    i3--;
                }
               break;

            case 3:
                j2 = j+1;
                i2 = i-1;
                while (f.getDisc(j2,i2) != -10 && (f.getDisc(j2,i2) == 0 || f.getDisc(j2,i2) == disc)) {
                    possible++;
                    j2++;
                    i2--;
                }
                j3 = j-1;
                i3 = i+1;
                while (f.getDisc(j3,i3) != -10 && (f.getDisc(j3,i3) == 0 || f.getDisc(j3,i3) == disc)) {
                    possible++;
                    j3--;
                    i3++;
                }
               break;

            case 4:
                j2 = j+1;
                i2 = i+1;
                while (f.getDisc(j2,i2) != -10 && (f.getDisc(j2,i2) == 0 || f.getDisc(j2,i2) == disc)) {
                    possible++;
                    j2++;
                    i2++;
                }
                j3 = j-1;
                i3 = i-1;
                while (f.getDisc(j3,i3) != -10 && (f.getDisc(j3,i3) == 0 || f.getDisc(j3,i3) == disc)) {
                    possible++;
                    j3--;
                    i3--;
                }
               break;
        }

        return possible >= 4;
    }

    private float CountRows(Field f) {
        float value = 0.0f;
        for (int i=0; i<f.getNrRows(); i++) {
            int curr = 0;
            int run = 0;
            int previ = 0;
            int prevj = 0;
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
                    if (curr > 0 && IsPossible(f,previ,prevj,1))
                        value += delta;

                    curr = disc;
                    previ = i;
                    prevj = j;
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
            if (curr > 0 && IsPossible(f,previ,prevj,1))
                value += delta;
        }

        return value;
    }

    private float CountCols(Field f) {
        float value = 0.0f;
        for (int j=0; j<f.getNrColumns(); j++) {
            int curr = 0;
            int run = 0;
            int previ = 0;
            int prevj = 0;
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
                    if (curr > 0 && IsPossible(f,previ,prevj,2))
                        value += delta;

                    previ = i;
                    prevj = j;
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
            if (curr > 0 && IsPossible(f,previ,prevj,2))
                value += delta;
        }

        return value;
    }

    private float CountRDiags(Field f) {
        float value = 0.0f;
        for (int i=0; i<f.getNrColumns()+f.getNrRows()-1; i++) {
            int curr = 0;
            int run = 0;
            int previ = 0;
            int prevj = 0;
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
                        if (curr > 0 && IsPossible(f,previ,prevj,3))
                            value += delta;

                        curr = disc;
                        previ = i-j;
                        prevj = j;
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
            if (curr > 0 && IsPossible(f,previ,prevj,3))
                value += delta;
        }

        return value;
    }

    private float CountLDiags(Field f) {
        float value = 0.0f;
        for (int i=0; i<f.getNrColumns()+f.getNrRows()-1; i++) {
            int curr = 0;
            int run = 0;
            int previ = 0;
            int prevj = 0;
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
                        if (curr > 0 && IsPossible(f,previ,prevj,4))
                             value += delta;

                        curr = disc;
                        previ = i-j;
                        prevj = f.getNrColumns()-1-j;
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
            if (curr > 0 && IsPossible(f,previ,prevj,4))
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
