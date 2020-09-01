package error;

public class UserAlreadyWithSubjectException extends RuntimeException {
	
	private static final long serialVersionUID = -3764131649026267460L;

	public UserAlreadyWithSubjectException(String message) {
		super(message);
	}

}
