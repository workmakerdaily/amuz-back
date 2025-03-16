package amuz.todo_back.dto.response;

public interface ResponseCode {

    String SUCCESS = "SU";
    String DATABASE_ERROR = "DBE";

    String AUTHENTICATION_FAIL = "AF";
    String DUPLICATED_USER_ID = "DI";
    String SIGN_IN_FAIL = "SF";
    String NO_EXIST_USER_ID = "NI";
    String NO_EXIST_TO_DO = "NET";
    String NO_PERMISSION = "NP";
    String TOKEN_CREATE_FAIL = "TCF";
}
