package jx86.lang;

/**
 * Represents an x86 machine instruction.
 * 
 * @author David J. Pearce
 *
 */
public interface Instruction {
	
	/**
	 * Represents a label in an instruction sequence which could be a branch
	 * target, etc.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public final class Label implements Instruction {
		public final String label;
		public final int alignment;
		public final boolean global;
		
		public Label(String label) {
			this.label = label;
			this.alignment = 1;
			this.global = false;
		}
		
		public Label(String label, int alignment, boolean global) {
			this.label = label;
			this.alignment = 1;
			this.global = false;
		}
		
		public String toString() {
			return label + ":";
		}
	}

	// ============================================================
	// Unit Operationrs
	// ============================================================	

	public enum UnitOp {
		clc,   // Clear Carry flag
		cdc,   // Clear direction flag
		cli,   // Clear interrupt flag
		cmc,   // Complement carry flag
		cbw,   // Convert byte to word
		cwde,  // Convert word to double word
		cwd,   // Convert word to double word
		cwq,   // Convert double word to quad word
		cpuid, // CPU identification
		enter, // Make stack frame
		hlt,   // Halt
		invd,  // Invalidate internal caches
		iret,  // Interrupt return
		iretd, // Interrupt return (double word operand)
		lahf,  // Load Status Flags into AH Register
		lar,   // Load Access Rights Byte
		lds,   // Load Far Pointer
		les,   // Load Far Pointer
		lfs,   // Load Far Pointer
		lgs,   // Load Far Pointer
		lss,   // Load Far Pointer

		leave, // destroy stack frame		
		nop,   // no operation
		popa,  // Pop All General-Purpose Registers
		popf,  // Pop into flags
		pusha, // Push All General-Purpose Registers
		pushf, // Push EFLAGS Register onto the Stack
		ret    // return from function
	}
	
	/**
	 * Represents a unit instruction (e.g. <code>ret</code>, <code>nop</code>,
	 * <code>popf</code>, <code>clc</code>, etc) which has no operands.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public final class Unit implements Instruction {
		public final UnitOp operation;

		/**
		 * Create a unary instruction with a register operand.
		 * 
		 * @param operation
		 *            Operation to perform
		 */
		public Unit(UnitOp operation) {
			this.operation = operation;
		}

		public String toString() {
			return operation.toString();
		}
	}
	
	// ============================================================
	// Unary Operations
	// ============================================================	
	
	public enum UnaryOp {
		dec,    // Decrement by 1
		inc,    // Increment by 1
		in,
		Int,    // Call to interrupt
		invlpg, // Invalidate TLB entry
		neg,    // Two's Complement Negation
		not,    // One's Complement Negation
		out,    // Output to Port
		push,
		pop,
		rcl,     // Rotate carry left
		rcr,     // Rotate carry right
		rol,     // Rotate left
		ror,     // Rotate right
		sahf,    // Store AH into Flags
		sal,     // Shift Arithmetic Left
		sar,     // Shift Arithmetic Right
		shl,     // Shift Left
		shr,     // Shift Right
	}
	
	/**
	 * Represents a unary instruction (e.g. <code>push</code>, <code>pop</code>,
	 * etc) with a register operand. For example:
	 * 
	 * <pre>
	 * pushq % eax
	 * </pre>
	 * 
	 * This pushes the contents of the <code>%eax</code> register on to the
	 * stack.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public final class UnaryReg implements Instruction {
		public final UnaryOp operation;
		public final Register operand;

		/**
		 * Create a unary instruction with a register operand.
		 * 
		 * @param operation
		 *            Operation to perform
		 * @param operand
		 *            Register operand
		 */
		public UnaryReg(UnaryOp operation, Register operand) {
			this.operation = operation;
			this.operand = operand;
		}

		public String toString() {
			return operation.toString() + " "
					+ Register.suffix(operand.width()) + " %" + operand;
		}
	}
	
	// ============================================================
	// Binary Operations
	// ============================================================
	
	public enum BinaryOp {
		mov,
		adc,     // Add with Carry
		add,
		sub,
		mul,     // unsigned multiplication
		imul,    // signed multiplication
		div,     // unsigned divide
		idiv,    // signed division
		cmp,
		cmpsb,   // compare byte word
		cmpsw,   // compare word
		cmpsd,   // compare double word
		cmpxchg, // compare and exchange
		cmpxchg8b, // compare and exchange 8 bytes
		or,      // Logical Inclusive OR
		
		
	}
	
	/**
	 * Represents a binary instruction (e.g. <code>mov</code>, <code>add</code>,
	 * etc) with register operands. For example:
	 * 
	 * <pre>
	 * movl %eax, %ebx
	 * </pre>
	 * 
	 * This assigns the contents of the <code>%eax</code> register to the
	 * <code>%ebx</code> register.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public final class BinaryRegReg implements Instruction {
		public final BinaryOp operation;
		public final Register leftOperand;
		public final Register rightOperand;
		
		/**
		 * Create a binary instruction with two register operands. The width of
		 * registers must equal, or an exception is raised.
		 * 
		 * @param operation
		 *            Operation to perform
		 * @param leftOperand
		 *            Register operand on left-hand side
		 * @param rightOperand
		 *            Register operand on right-hand side
		 */
		public BinaryRegReg(BinaryOp operation, Register leftOperand, Register rightOperand) {
			if(leftOperand.width() != rightOperand.width()) {
				throw new IllegalArgumentException("Register operands must have identical width");
			}
			this.operation = operation;
			this.leftOperand = leftOperand;
			this.rightOperand = rightOperand;
		}	
		
		public String toString() {
			return operation.toString() + " " + Register.suffix(leftOperand.width())
					+ " %" + leftOperand + ", %" + rightOperand;
		}
	}
	
	/**
	 * Represents a binary instruction (e.g. <code>mov</code>, <code>add</code>,
	 * etc) with an immediate source operand and a register target operand. For
	 * example:
	 * 
	 * <pre>
	 * movl $3, %eax
	 * </pre>
	 * 
	 * This assigns the constant 3 to the <code>%eax</code> register.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public final class BinaryImmReg implements Instruction {
		public final BinaryOp operation;
		public final long leftOperand;
		public final Register rightOperand;
		
		/**
		 * Create a binary instruction from one register to another. The
		 * immediate operand must fit within the width of the target register,
		 * or an exception is raised.
		 * 
		 * @param leftOperand
		 *            Immediate operand on left-hand side. This is always
		 *            interpreted as a signed integer, regardless of width. For
		 *            example, if the <code>rhs</code> has byte width then the
		 *            accepted range for the immediate operand is -128 .. 127.
		 * @param rightOperand
		 *            Register operand on right-hand side.
		 */
		public BinaryImmReg(BinaryOp operation, long leftOperand, Register rightOperand) {
			switch(rightOperand.width()) {
			case Byte:
				if(leftOperand < Byte.MIN_VALUE || leftOperand > Byte.MAX_VALUE) {
					throw new IllegalArgumentException("immediate operand does not fit into byte");
				}
				break;
			case Word:
				if(leftOperand < Short.MIN_VALUE || leftOperand > Short.MAX_VALUE) {
					throw new IllegalArgumentException("immediate operand does not fit into word");
				}
				break;
			case Long:
				if(leftOperand < Integer.MIN_VALUE || leftOperand > Integer.MAX_VALUE) {
					throw new IllegalArgumentException("immediate operand does not fit into double word");
				}
				break;
			default:
				// this case is always true by construction
			}
			this.operation = operation;
			this.leftOperand = leftOperand;
			this.rightOperand = rightOperand;
		}	
		
		public String toString() {
			return operation.toString() + Register.suffix(rightOperand.width())
					+ "$" + leftOperand + ", %" + rightOperand;
		}
	}	
	
	// ============================================================
	// Branch Operations
	// ============================================================
	
	public enum RelativeOp {		
		ja,    // Jump if above (CF == 0 and ZF == 0)
		jae,   // Jump if above or equal (CF == 0)
		jb,    // Jump if below (CF == 1)
		jbe,   // Jump if below or equal (CF == 1 or ZF == 1)
		jc,    // Jump if carry (CF == 1)
		jcxz,  // Jump if cx == 0
		jecxz, // Jump if ecx == 0
		je,    // Jump if equal (ZF == 1)
		jg,    // Jump if greater (ZF == 0 and SF==OF)
		jge,   // Jump if greater or equal  (SF==OF)
		jl,    // Jump if less (SF<>OF)
		jle,   // Jump if less or equal (ZF == 1 or SF<>OF)
		jna,   // Jump if not above (CF == 1 or ZF == 1)
		jnae,  // Jump if not above or equals (CF==1)
		jmp,   // Unconditional Jump
		jnb,   // Jump if not below (CF=0)
		jnbe,  // Jump if not below or equal (CF=0 and ZF=0)
		jnc,   // Jump if not carry (CF=0)
		jne,   // Jump if not equal (ZF=0)
		jng,   // Jump if not greater (ZF=1 or SF<>OF)		 
		jnge,  // Jump if not greater or equal (SF<>OF)
		jnl,   // Jump if not less (SF=OF)
		jnle,  // Jump if not less or equal (ZF=0 and SF=OF)
		jno,   // Jump if not overflow (OF=0)
		jnp,   // Jump if not parity (PF=0)
		jns,   // Jump if not sign (SF=0)
		jnz,   // Jump if not zero (ZF=0)
		jo,    // Jump if overflow (OF=1)	
		jp,    // Jump if parity (PF=1)
		jpe,   // Jump if parity even (PF=1)
		jpo,   // Jump if parity odd (PF=0)
		js,    // Jump if sign (SF=1)
		jz,    // Jump if zero (ZF = 1)	
		lea,   // Load Effective Adddress
		loop,  // Loop according r/e/cx
		loope,  // Loop according r/e/cx
		loopz,  // Loop according r/e/cx
		loopne,  // Loop according r/e/cx
		loopnz,  // Loop according r/e/cx
	}
	
	/**
	 * Represents a unary branching instruction (e.g. <code>jmp</code>,
	 * <code>ja</code>, etc) with a label operand. For example:
	 * 
	 * <pre>
	 * cmp %eax,%ebx
	 * ja  target
	 * </pre>
	 * 
	 * This compares the <code>eax</code> and <code>ebx</code> registesr and
	 * branches to <code>target</code> if <code>eax</code> is above
	 * <code>ebx</code>.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public final class Relative implements Instruction {
		public final RelativeOp operation;
		public final String operand;

		/**
		 * Create a unary instruction with a register operand.
		 * 
		 * @param operation
		 *            Operation to perform
		 * @param operand
		 *            Register operand
		 */
		public Relative(RelativeOp operation, String operand) {
			this.operation = operation;
			this.operand = operand;
		}

		public String toString() {
			return operation.toString() + " " + operand;
		}
	}
}
