package model;

public class Result_Event {
	private final boolean win;

	public Result_Event(boolean win) {
		this.win = win;
	}

	public boolean isGanhou() {
		return win;
	}
}