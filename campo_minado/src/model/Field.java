package model;

import java.util.ArrayList;
import java.util.List;


public class Field {
	
	private final int line;
	private final int column;
	
	private boolean open = false;
	private boolean mine = false;
	private boolean marked = false;
	
	private List<Field> neighbors = new ArrayList<>();
	private List<Field_Observe> observes = new ArrayList<>();
	
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	public void registerObserve(Field_Observe observe) {
		observes.add(observe);
	}
	
	private void feedObserve(Field_Event event) {
		observes.stream()
			.forEach(o -> o.eventOccurred(this, event));
	}
	
	boolean addNeighbor(Field neighbor) {
		boolean lineDifferent = line != neighbor.line;
		boolean columnDifferent = column != neighbor.column;
		boolean diagonal = lineDifferent && columnDifferent;
		
		
		int  deltaLine = Math.abs(line - neighbor.line);
		int  deltaCollumn = Math.abs(column - neighbor.column);
		int  deltaGerenal = deltaLine + deltaCollumn;
		
		if(deltaGerenal == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if(deltaGerenal == 2 && diagonal ) {
			neighbors.add(neighbor);
			return true;	
		}else {
			return false;
		}
	}
	public void changeMarked() {
		 if(!open) {
			 marked = !marked;
			 if(marked) {
				feedObserve(Field_Event.MARK);
			} else {
				feedObserve(Field_Event.UNMARK);			
			} 
		 }
	 }
	 

	public boolean open() {
		 if(!open && !marked) {
			if(mine) {
				feedObserve(Field_Event.EXPLODE);
				return true;
			}				
			setOpen(true);		
			if(safeNeighbors()) {
				 neighbors.forEach(v -> v.open());
			}
			return true;
		 }else {
			 return false;			 
		 }
	 }
	 
	 public boolean safeNeighbors() {
		 return neighbors.stream().noneMatch(v -> v.mine);
	 }
	 
	 void mine() {
		 mine = true;
	 }
	 
	 public boolean isMine() {
		 return mine;
	 }
	 public boolean isMarked() {
		 return marked;
	 }
	 
	 void setOpen (boolean open) {
		this.open = open;			
		if(open) {
		 	feedObserve(Field_Event.OPEN);
	    }
	 }
	
	 public boolean isOpen() {
		return open;
	 }
	 public boolean isOff() {
		 return !isOpen();
	 }
	 
	 boolean objAccomplished() {
		 boolean unveiled = !mine && open;
		 boolean protectedd = mine && marked;
		 return protectedd || unveiled;
	 }
	 
	 public int mineNeighbor() {
		 return (int) neighbors.stream().filter(v ->  v.mine).count();
	 }
	 
	 void restart(){
		 open = false;
		 mine = false;
		 marked = false;
		feedObserve(Field_Event.RESET);
	 }
 
	 public int getColumn() {

		 return column;
	 }

	 public int getLine() {

		 return line;
	 }

}