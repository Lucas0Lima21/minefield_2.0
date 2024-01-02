package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Game_Board implements Field_Observe {
	
	private final int lines;
	private final int columns;
	private final int mines;
	
	private final List<Field> fields = new ArrayList<>();
	private final List<Consumer<Result_Event>> observes = new ArrayList<>();


	public Game_Board(int lines, int columns, int mine) {
		super();
		this.lines = lines;
		this.columns = columns;
		this.mines = mine;
		
		generateField();
		associateNeighbor();
		giveMine();
	}
	public void toEachField(Consumer<Field> function) {
		fields.forEach(function);
	}
	
	private void feedObserve(boolean result) {
		observes.stream()
			.forEach(o -> o.accept(new Result_Event(result)));
	}	

	public void registerObserve(Consumer<Result_Event> observe) {
		observes.add(observe);
	}

	public void open(int line, int column) {
		fields.parallelStream().filter(c -> c.getLine() == line && c.getColumn() == column).findFirst()
				.ifPresent(c -> c.open());
	}
	public void changeMarked(int line, int column) {
		fields.parallelStream()
		.filter( c -> c.getLine() == line && c.getColumn() == column)
		.findFirst()
		.ifPresent(c -> c.changeMarked());
	}

	private void generateField() {
		for (int l = 0; l < lines; l++) {
			for (int c = 0; c < columns; c++) {
				Field field = new Field(l, c);
				field.registerObserve(this);
				fields.add(field);
			}
		}
	}

	private void associateNeighbor() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbor(f2);                 
			}
		}
	}

	private void giveMine() {
		long mineField = 0;
		Predicate<Field> minee = c ->c.isMine();
		
		do {
			int random = (int) (Math.random() * fields.size());
			fields.get(random).mine();;
			mineField = fields.stream().filter(minee).count();
		}while(mineField < mines);
	}
	
	public boolean objFinal() {
		return fields.stream().allMatch(c -> c.objAccomplished());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		giveMine();
	}
	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}

	@Override
	public void eventOccurred(Field field, Field_Event event) {
		if (event == Field_Event.EXPLODE) {
			ShowMines();
			feedObserve(false);
		} else if (objFinal()) {
			feedObserve(true);
		}
	}
	private void ShowMines() {
		fields.stream()
			.filter(c -> c.isMine())
			.filter(c -> !c.isMarked())
			.forEach(c -> c.setOpen(true));
	}
}