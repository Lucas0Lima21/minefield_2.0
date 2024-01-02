package model;

@FunctionalInterface
public interface Field_Observe {
	
	public void eventOccurred(Field field, Field_Event event);
	
}
