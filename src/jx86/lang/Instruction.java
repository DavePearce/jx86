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
	// Unary Operations
	// ============================================================	
	

	public enum UnaryOp {
		push,
		pop
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
		add,
		sub,
		mul,
		idiv,
		cmp
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
			return operation.toString() + " " + Register.suffix(rightOperand.width())
					+ "$" + leftOperand + ", %" + rightOperand;
		}
	}	
}
