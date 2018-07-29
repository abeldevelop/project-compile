package com.abeldevelop.compile.exception;

public class ProjectCompileException extends RuntimeException {
	
	private static final long serialVersionUID = -8460356990632230194L;
	
	private int code;
	
	public ProjectCompileException(int code) {
        super();
        this.code = code;
    }
	
	public ProjectCompileException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
	
	public ProjectCompileException(String message, Throwable cause) {
        super(message, cause);
    }
	
    public ProjectCompileException(String message, int code) {
        super(message);
        this.code = code;
    }
    public ProjectCompileException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
}
