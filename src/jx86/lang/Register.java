package jx86.lang;

import java.util.Map;


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
		ScalarDouble, // 64bits
		Quad, // 64 bits
		ScalarSingle, // 32bits
		Long, // 32 bits
		Word, // 16 bits
		Byte;  // 8 bits		
	}
	

	/**
	 * Determine whether two registers are "compatible" with each other. That
	 * is, whether or not they can be used together in a given instruction.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	public static boolean areCompatiable(Width lhs, Width rhs) {
		return lhs == rhs || (lhs == Width.ScalarDouble && rhs == Width.Quad)
				|| (lhs == Width.Quad && rhs == Width.ScalarDouble)
				|| (lhs == Width.ScalarSingle && rhs == Width.Long)
				|| (lhs == Width.Long && rhs == Width.ScalarSingle);
	}
	
	private static Register.Width join(Register.Width lhs, Register.Width rhs) {
		if(lhs == rhs) { 
			return lhs;
		} else if(lhs == Width.ScalarDouble) {
			return join(Width.Quad,rhs);
		} else if(lhs == Width.ScalarSingle) {
			return join(Width.Long,rhs);
		} else if(rhs == Width.ScalarDouble) {
			return join(lhs,Width.Quad);
		} else if(rhs == Width.ScalarSingle) {
			return join(lhs,Width.Long);
		}  
		
		throw new IllegalArgumentException("incomparable register widths: " + lhs + ", " + rhs);
	}
	
	/**
	 * Return the appropriate suffix to associate with an instruction that
	 * operates on two registers (potentially of different width).
	 * 
	 * @param width
	 * @return
	 */
	public static String suffix(Register.Width lhs, Register.Width rhs) {
		return suffix(join(lhs,rhs));		
	}
	
	/**
	 * Return the suffix associated with a given register width.
	 * 
	 * @param width
	 * @return
	 */
	public static String suffix(Register.Width width) {
		switch(width) {
		case Byte:
			return "b";
		case Word:
			return "w";
		case Long:
			return "l";
		case ScalarSingle:
			return "ss";
		case Quad:
			return "q";
		case ScalarDouble:
			return "sd";
		default:
			throw new IllegalArgumentException("Invalid register width: " + width.name());			
		}
	}
	
	// x86_8
	public static final Register AL = new Register("al", Width.Byte);
	public static final Register AH = new Register("ah", Width.Byte);
	public static final Register BL = new Register("bl", Width.Byte);
	public static final Register BH = new Register("bh", Width.Byte);
	public static final Register CL = new Register("cl", Width.Byte);
	public static final Register CH = new Register("ch", Width.Byte);
	public static final Register DL = new Register("dl", Width.Byte);
	public static final Register DH = new Register("dh", Width.Byte);
	
	// x86_16
	public static final Register AX = new Register("ax", Width.Word);
	public static final Register BX = new Register("bx", Width.Word);
	public static final Register CX = new Register("cx", Width.Word);
	public static final Register DX = new Register("dx", Width.Word);
	public static final Register DI = new Register("di", Width.Word);
	public static final Register SI = new Register("si", Width.Word);
	public static final Register BP = new Register("bp", Width.Word);
	public static final Register SP = new Register("sp", Width.Word);
	public static final Register IP = new Register("ip", Width.Word);
		
	// x86_32
	public static final Register EAX = new Register("eax", Width.Long);
	public static final Register EBX = new Register("ebx", Width.Long);
	public static final Register ECX = new Register("ecx", Width.Long);
	public static final Register EDX = new Register("edx", Width.Long);
	public static final Register EDI = new Register("edi", Width.Long);
	public static final Register ESI = new Register("esi", Width.Long);
	public static final Register EBP = new Register("ebp", Width.Long);
	public static final Register ESP = new Register("esp", Width.Long);
	public static final Register EIP = new Register("eip", Width.Long);
	
	// x86_64
	public static final Register RAX = new Register("rax", Width.Quad);
	public static final Register RBX = new Register("rbx", Width.Quad);
	public static final Register RCX = new Register("rcx", Width.Quad);
	public static final Register RDX = new Register("rdx", Width.Quad);
	public static final Register RDI = new Register("rdi", Width.Quad);
	public static final Register RSI = new Register("rsi", Width.Quad);
	public static final Register RBP = new Register("rbp", Width.Quad);
	public static final Register RSP = new Register("rsp", Width.Quad);
	public static final Register RIP = new Register("rip", Width.Quad);
	
	// Streaming SIMD Extensions (SSE)
	public static final Register XMM0 = new Register("xmm0", Width.ScalarDouble);
	public static final Register XMM1 = new Register("xmm1", Width.ScalarDouble);
	public static final Register XMM2 = new Register("xmm2", Width.ScalarDouble);
	public static final Register XMM3 = new Register("xmm3", Width.ScalarDouble);
	public static final Register XMM4 = new Register("xmm4", Width.ScalarDouble);
	public static final Register XMM5 = new Register("xmm5", Width.ScalarDouble);
	public static final Register XMM6 = new Register("xmm6", Width.ScalarDouble);
	public static final Register XMM7 = new Register("xmm7", Width.ScalarDouble);
	
	// Families	
	public static final Register[] AX_FAMILY = {
			Register.AL,Register.AH,Register.AX,Register.EAX,Register.RAX
	};
	public static final Register[] BX_FAMILY = {
		Register.BL,Register.BH,Register.BX,Register.EBX,Register.RBX
	};
	public static final Register[] CX_FAMILY = {
		Register.CL,Register.CH,Register.CX,Register.ECX,Register.RCX
	};
	public static final Register[] DX_FAMILY = {
		Register.DL,Register.DH,Register.DX,Register.EDX,Register.RDX
	};
	public static final Register[] DI_FAMILY = {
		Register.DI,Register.EDI,Register.RDI
	};
	public static final Register[] SI_FAMILY = {
		Register.SI,Register.ESI,Register.RSI
	};
	public static final Register[] BP_FAMILY = {
		Register.BP,Register.EBP,Register.RBP
	};
	public static final Register[] SP_FAMILY = {
		Register.SP,Register.ESP,Register.RSP
	};
	public static final Register[] IP_FAMILY = {
		Register.IP,Register.EIP,Register.RIP
	};
	
	public static final Register[][] ALL_FAMILIES = {
		AX_FAMILY,
		BX_FAMILY,
		CX_FAMILY,
		DX_FAMILY,
		DI_FAMILY,
		SI_FAMILY,
		BP_FAMILY,
		SP_FAMILY,
		IP_FAMILY
	};
	
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
	
	/**
	 * Return the family this register is associated with.
	 * 
	 * @return
	 */
	public Register[] family() {
		for (int i = 0; i != ALL_FAMILIES.length; ++i) {
			Register[] candidate = ALL_FAMILIES[i];
			for (int j = 0; j != candidate.length; ++j) {
				if (candidate[j] == this) {
					return candidate;
				}
			}
		}
		throw new IllegalArgumentException("Register does not have family?");
	}
	

	/**
	 * <p>
	 * Determine the first sibling of a given width in this registers family.
	 * For example, the <code>Quad</code> width sibling of the <code>bx</code>
	 * register is the <code>rbx</code>. In contrast, the <code>Long</code>
	 * width sibling is <code>ebx</code>.
	 * </p>
	 * 
	 * <p>
	 * This function is useful for determining the head of a register family for
	 * a given architecture. For example, on <code>x86_64</code> the head of the
	 * <code>bx</code> family is <code>rbx</code>.
	 * </p>
	 * 
	 * @param width
	 *            Register width to match sibling with.
	 * @return The first sibling of the given width, or <code>null</code> if no
	 *         such sibling exists.
	 */
	public Register sibling(Width width) {
		Register[] family = family();
		for(int i=0;i!=family.length;++i) {
			Register sibling = family[i];
			if(sibling.width() == width) {
				// first match
				return sibling;
			}
		}

		return null; 
	}
	
	// ============================================
	// Helpers
	// ============================================
	

}
