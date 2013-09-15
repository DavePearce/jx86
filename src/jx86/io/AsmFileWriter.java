package jx86.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
			out.println("\t.text");
			for(Instruction insn : code.instructions) {
				write(insn);
			}
		} else {
			throw new IllegalArgumentException("unknown section encountered");
		}
	}
	
	public void write(Instruction insn) {
		if(insn instanceof Instruction.Label) {
			write((Instruction.Label) insn);
		} else if(insn instanceof Instruction.BinaryRegReg) {
			write((Instruction.BinaryRegReg) insn);
		} else {
			throw new IllegalArgumentException("unknown instruction encountered: " + insn);
		}
	}
	
	public void write(Instruction.Label insn) {
		throw new IllegalArgumentException("unknown instruction encountered: " + insn);
	}
	
	public void write(Instruction.BinaryRegReg insn) {
		out.println("\tmov" + Register.suffix(insn.leftOperand.width()) + " %" + insn.leftOperand + ", %" + insn.rightOperand);
	}
}
