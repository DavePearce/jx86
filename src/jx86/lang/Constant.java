package jx86.lang;

public abstract class Constant {
	public final int alignment;
	public final boolean global;
	public final String label;
	
	public Constant(String label, int alignment, boolean global) {
		this.label = label;
		this.alignment = alignment;
		this.global = global;
	}
}
