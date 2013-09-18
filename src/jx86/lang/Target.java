package jx86.lang;

/**
 * Provides an abstraction of a compilation target. This is useful for
 * abstracting away details of compilation targets.
 * 
 * @author David J. Pearce
 * 
 */
public final class Target {
	
	// ============================================
	// Enums & Constants
	// ============================================

	public static final Target MACOS_X86_64 = new Target(OS.MACOS,Arch.X86_64); 
	
	/**
	 * The set of supported operating systems.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public enum OS {
		LINUX, MACOS
	}
	
	/**
	 * The set of support x86 architectures.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Arch {
		X86_32, X86_64
	}
	
	// ============================================
	// Fields
	// ============================================
			
	public final OS os;
	public final Arch arch;
	
	// ============================================
	// Constructors
	// ============================================

	private Target(OS os, Arch arch) {
		this.os = os;
		this.arch = arch;
	}
}
