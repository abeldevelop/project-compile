package com.abeldevelop.compile.exception;

public class CyclicalProjectCompileException extends RuntimeException {
	
	private static final long serialVersionUID = -8460356990632230194L;
	
	private int code;
	
	public CyclicalProjectCompileException(int code) {
        super();
        this.code = code;
    }
	
	public CyclicalProjectCompileException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
	
	public CyclicalProjectCompileException(String message, Throwable cause) {
        super(message, cause);
    }
	
    public CyclicalProjectCompileException(String message, int code) {
        super(message);
        this.code = code;
    }
    public CyclicalProjectCompileException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
}
