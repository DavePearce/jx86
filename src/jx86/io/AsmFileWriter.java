package jx86.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import jx86.lang.Constant;
import jx86.lang.Instruction;
import jx86.lang.Register;
import jx86.lang.X86File;

/**
 * Responsible for writing <code>X86File</code>s to a character stream in a
 * format compatible with the GNU Assembler (GAS). Such files can then be
 * compiled into binary machine code using the GNU Assembler.
 * 
 * @author David J. Pearce
 * 
 */
public class AsmFileWriter {
	private PrintStream out;

	public AsmFileWriter(File file) throws IOException {
		this.out = new PrintStream(new FileOutputStream(file));
	}

	public AsmFileWriter(OutputStream output) throws IOException {
		this.out = new PrintStream(output);
	}
	
	public void close() {
		out.close();
	}

	public void write(X86File file) {
		for (X86File.Section s : file.sections()) {
			write(s);
		}
	}

	public void write(X86File.Section section) {		
		if (section instanceof X86File.Code) {
			X86File.Code code = (X86File.Code) section;
			out.println();
			out.println("\t.text");
			for(Instruction insn : code.instructions) {
				write(insn);
			}
		} else if (section instanceof X86File.Data) {
			X86File.Data code = (X86File.Data) section;
			out.println();
			out.println("\t.data");
			for(Constant constant : code.constants) {
				write(constant);
			}
		} else {
			throw new IllegalArgumentException("unknown section encountered");
		}
	}
	
	public void write(Instruction insn) {
		if(insn instanceof Instruction.Label) {
			write((Instruction.Label) insn);
		} else if(insn instanceof Instruction.Unit) {
			write((Instruction.Unit) insn);
		} else if(insn instanceof Instruction.Reg) {
			write((Instruction.Reg) insn);
		} else if(insn instanceof Instruction.RegReg) {
			write((Instruction.RegReg) insn);
		} else if(insn instanceof Instruction.ImmReg) {
			write((Instruction.ImmReg) insn);
		} else if(insn instanceof Instruction.Addr) {
			write((Instruction.Addr) insn);
		} else if(insn instanceof Instruction.AddrReg) {
			write((Instruction.AddrReg) insn);
		} else if(insn instanceof Instruction.AddrRegReg) {
			write((Instruction.AddrRegReg) insn);
		} else if(insn instanceof Instruction.ImmIndReg) {
			write((Instruction.ImmIndReg) insn);
		} else if(insn instanceof Instruction.RegImmInd) {
			write((Instruction.RegImmInd) insn);
		} else {
			throw new IllegalArgumentException("unknown instruction encountered: " + insn);
		}
	}
	
	public void write(Instruction.Label insn) {
		if(insn.global) {
			out.println("\t.globl " + insn.label);
		}
		if(insn.alignment != 1) {
			out.println("\t.align " + insn.alignment);
		}
		out.println(insn.label + ":");		
	}
	
	public void write(Instruction.Unit insn) {
		out.println("\t" + insn.operation);
	}
	
	public void write(Instruction.Reg insn) {
		out.println("\t" + insn.operation 
				+ Register.suffix(insn.operand.width()) + " %" + insn.operand);
	}
	
	public void write(Instruction.RegReg insn) {
		out.println("\t" + insn.operation 
				+ Register.suffix(insn.leftOperand.width(),insn.rightOperand.width()) + " %"
				+ insn.leftOperand + ", %" + insn.rightOperand);
	}
	
	public void write(Instruction.ImmReg insn) {
		out.println("\t" + insn.operation 
				+ Register.suffix(insn.rightOperand.width()) + " $"
				+ insn.leftOperand + ", %" + insn.rightOperand);
	}
	
	public void write(Instruction.ImmIndReg insn) {
		out.println("\t" + insn.operation 
				+ Register.suffix(insn.targetOperand.width()) + " "
				+ insn.immediateOffset + "(%" + insn.baseOperand + "), %" + insn.targetOperand);
	}
	
	public void write(Instruction.RegImmInd insn) {
		out.println("\t" + insn.operation 
				+ Register.suffix(insn.sourceOperand.width()) + " %"
				+ insn.sourceOperand + ", " + insn.immediateOffset + "(%"
				+ insn.baseOperand + ")");
	}
	
	public void write(Instruction.Addr insn) {
		out.println("\t" + insn.operation + " " + insn.operand);
	}
	
	public void write(Instruction.AddrReg insn) {
		out.println("\t" + insn.operation
				+ Register.suffix(insn.rightOperand.width()) + " "
				+ insn.leftOperand + ", %" + insn.rightOperand);
	}
	
	public void write(Instruction.AddrRegReg insn) {
		out.println("\t" + insn.operation
				+ Register.suffix(insn.rightOperand.width()) + " "
				+ insn.leftOperand_1 + "(%" + insn.leftOperand_2 + "), %"
				+ insn.rightOperand);
	}
	
	public void write(Constant constant) {
		if(constant.global) {
			out.println("\t.globl " + constant.label);
		}
		if(constant.alignment != 1) {
			out.println("\t.align " + constant.alignment);
		}
		if(constant.label != null) {
			out.println(constant.label + ":");
		}
		if(constant instanceof Constant.String) {
			Constant.String cs = (Constant.String) constant;
			// FIXME: probably should be doing some kind of escaping here.
			out.println("\t.asciz \"" + cs.value + "\"");
		} else if(constant instanceof Constant.Word) {
			Constant.Word cw = (Constant.Word) constant;
			out.println("\t.word " + cw.value);
		} else if(constant instanceof Constant.Long) {
			Constant.Long cw = (Constant.Long) constant;
			out.println("\t.long " + cw.value);
		} else if(constant instanceof Constant.Quad) {
			Constant.Quad cw = (Constant.Quad) constant;
			out.println("\t.quad " + cw.value);
		}
	}
}
