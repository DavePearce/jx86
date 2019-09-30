package jx86.lang;

/**
 * Provides an abstraction of a compilation target which is a combination of
 * operating system and x86 architecture. This is useful for abstracting away
 * details of compilation targets.
 * 
 * @author David J. Pearce
 * 
 */
public final class Target {
	
	// ============================================
	// Enums & Constants
	// ============================================

	public static final Target MACOS_X86_64 = new Target(OS.MACOS,Arch.X86_64); 
	
	public static final Target LINUX_X86_64 = new Target(OS.LINUX,Arch.X86_64); 
	
	public static final Target WINDOWS_X86_64 = new Target(OS.WINDOWS, Arch.X86_64);
	/**
	 * The set of supported operating systems.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public enum OS {
		LINUX, MACOS, WINDOWS
	}
	
	/**
	 * The set of supported x86 architectures.
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
	
	/**
	 * Return the "natural" size of this architecture in bytes.
	 * 
	 * @return
	 */
	public int widthInBytes() {
		switch (arch) {
		case X86_32:
			return 4; // 4 * 8 = 32
		case X86_64:
			return 8; // 8 * 8 = 64
		}
		throw new IllegalArgumentException("Unknown architecture encountered: "
				+ arch);
	}
}
