package amuz.todo_back.dto.response;

public interface ResponseMessage {

    String SUCCESS = "Success.";
    String DATABASE_ERROR = "Database error.";

    String AUTHENTICATION_FAIL = "Authentication failed.";
    String DUPLICATED_USER_ID = "Duplicated user id.";
    String SIGN_IN_FAIL = "Sign in failed.";
    String NO_EXIST_USER_ID = "No exist user id.";
    String NO_EXIST_TO_DO = "No exist to do.";
    String NO_EXIST_DATA = "No exist data.";
    String NO_PERMISSION = "No permission.";
    String TOKEN_CREATE_FAIL = "Token creation failed.";

}
