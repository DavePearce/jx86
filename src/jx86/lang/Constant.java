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
	
	/**
	 * Construct a single word constant.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public static final class Word extends Constant {
		public final int value;
		
		public Word(java.lang.String label, int value) {
			super(label,1,false);
			this.value = value;
		}
		
		public Word(java.lang.String label, int alignment, boolean global, int value) {
			super(label,alignment,global);
			this.value = value;
		}
	}
	
	/**
	 * Construct a long (i.e. double) word constant.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public static final class Long extends Constant {
		public final long value;
		
		public Long(java.lang.String label, long value) {
			super(label,1,false);
			this.value = value;
		}
		
		public Long(java.lang.String label, int alignment, boolean global, long value) {
			super(label,alignment,global);
			this.value = value;
		}
	}
	
	/**
	 * Construct a quad word constant.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public static final class Quad extends Constant {
		public final long value;
		
		public Quad(java.lang.String label, long value) {
			super(label,1,false);
			this.value = value;
		}
		
		public Quad(java.lang.String label, int alignment, boolean global, long value) {
			super(label,alignment,global);
			this.value = value;
		}
	}
}
