package kr.co.itcen.mysite.exception;

public class UserDaoException extends RuntimeException {	// runtime때만 발생하는 예외
	public UserDaoException() {
		super("UserDaoException Occurs");
	}
	
	public UserDaoException(String message) {
		super(message);
	}
}
