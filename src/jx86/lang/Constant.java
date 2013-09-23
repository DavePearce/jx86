package jx86.lang;

/**
 * Represents a labeled data item found within the data segment of an x86 file.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class Constant {
	public final int alignment;
	public final boolean global;
	public final java.lang.String label;
	
	public Constant(java.lang.String label, int alignment, boolean global) {
		this.label = label;
		this.alignment = alignment;
		this.global = global;
	}
	
	/**
	 * Construct a string constant.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public static final class String extends Constant {
		public final java.lang.String value;
		
		public String(java.lang.String label, java.lang.String value) {
			super(label,1,false);
			this.value = value;
		}
		
		public String(java.lang.String label, int alignment, boolean global, java.lang.String value) {
			super(label,alignment,global);
			this.value = value;
		}
	}
}
