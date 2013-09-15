package jx86.lang;

/**
 * Represents and provides information an an x86 register. Registers are grouped
 * by architecture.
 * 
 * @author David J. Pearce
 * 
 */
public class Register {
	
	// ============================================
	// Enums & Constants
	// ============================================
	
	public enum Width {
		Quad, // 64 bits
		Long, // 32 bits
		Word, // 16 bits
		Byte  // 8 bits
	}
	
	public static char suffix(Register.Width width) {
		switch(width) {
		case Byte:
			return 'b';
		case Word:
			return 'w';
		case Long:
			return 'l';
		default:
			return 'q';			
		}
	}
	
	// x86_8
	public static final Register AL = new Register("al", Width.Byte);
	
	// x86_16
	public static final Register AX = new Register("ax", Width.Word);
	
	// x86_32
	public static final Register EAX = new Register("eax", Width.Long);

	// x86_64
	public static final Register RAX = new Register("rax", Width.Quad);
	
	// ============================================
	// Fields
	// ============================================
	
	private final Width width;
	private final String name;

	// ============================================
	// Constructors
	// ============================================
	Register(String name, Width width) {
		this.name = name;
		this.width = width;
	}
	
	// ============================================
	// Accessors
	// ============================================

	/**
	 * Return the width of this register;
	 * 
	 * @return
	 */
	public Width width() {
		return width;
	}
	
	/**
	 * Return the name of this register;
	 * 
	 * @return
	 */
	public String name() {
		return name;
	}
	
	public String toString() {
		return name;
	}
}
