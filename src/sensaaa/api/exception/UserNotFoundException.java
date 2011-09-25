package sensaaa.api.exception;

public class UserNotFoundException extends Exception {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super();
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
