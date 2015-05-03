package VectorEditor;

import java.util.ArrayList;
import java.util.Collections;

import Interface.Config;

public class VectorContainer {
	
	private static ArrayList<VectorShape> vectorShapes = new ArrayList<VectorShape>();
	private static ArrayList<ArrayList<VectorShape>> history = new ArrayList<ArrayList<VectorShape>>(); 
	
	@SuppressWarnings("unchecked")
	public static void add(VectorShape shape) {
		vectorShapes.add(shape);
		if(history.size()  < Config.maxHistory) {
			history.add((ArrayList<VectorShape>) vectorShapes.clone());
		} else {
			Collections.reverse(history);
			Collections.rotate(history, 1);
			Collections.reverse(history);
			history.set(Config.maxHistory - 1, vectorShapes);
		}
	}
	
	public static ArrayList<VectorShape> get() {
		return vectorShapes;
	}
	
	public static VectorShape get(int index) {
		return vectorShapes.get(index);
	}
	
	public static int size() {
		return vectorShapes.size();
	}
	public static int lastIndex() {
		return vectorShapes.size() - 1;
	}
	
	public static void set(ArrayList<VectorShape> shapes) {
		vectorShapes = shapes;
	}
	
	public static void set(int index, VectorShape shape) {
		vectorShapes.set(index, shape);
	}
	
	public static ArrayList<VectorShape> getStep(int step) {
		return history.get(step);
	}
}
