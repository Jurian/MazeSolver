package knmi.msolve.model;

public class Maze {

	
	private final Boolean[][] structure;
	
	
	public Maze(Boolean[][] structure) {
		this.structure = structure;
	}


	public Boolean[][] getStructure() {
		return structure;
	}
	
}
