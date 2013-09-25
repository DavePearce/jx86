package jx86.lang;

import java.util.ArrayList;
import java.util.List;

public class X86File {
	
	// ============================================
	// Classes
	// ============================================

	public interface Section {
		
	}
	
	public static class Code implements Section {
		public final List<Instruction> instructions;

		public Code() {
			this.instructions = new ArrayList<Instruction>();
		}

		public Code(Instruction... instructions) {
			this.instructions = new ArrayList<Instruction>();
			for(Instruction i : instructions) {
				this.instructions.add(i);
			}
		}
		
		public Code(List<Instruction> instructions) {
			this.instructions = new ArrayList<Instruction>(instructions);
		}
	}
	
	public static class Data implements Section {
		public final List<Constant> constants;

		public Data() {
			this.constants = new ArrayList<Constant>();
		}

		public Data(List<Constant> constants) {
			this.constants = new ArrayList<Constant>(constants);
		}
	}
	
	// ============================================
	// Fields
	// ============================================

	private ArrayList<Section> sections;
	
	public X86File(Section... sections) {
		this.sections = new ArrayList<Section>();
		for(int i=0;i!=sections.length;++i) {
			this.sections.add(sections[i]);
		}
	}

	public X86File(List<Section> sections) {
		this.sections = new ArrayList<Section>(sections);
	}
	
	public List<Section> sections() {
		return sections;
	}
}
