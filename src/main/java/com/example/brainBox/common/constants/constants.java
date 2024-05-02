package com.example.brainBox.common.constants;

public interface constants {
    interface ErrorMessages {
        String NULL_FILED = "Filed should not be null.";
        String BLANK_FILED = "Filed should not be blank.";
        String MOBILE_NUMBER_PATTERN = "Invalid mobile number format it should start with 5 followed by 8 digits.";
        String MIN_MAX_USERNAME_LENGTH = "Invalid username length it should be between 2 to 256 characters.";
        String EMAIL_PATTERN = "Invalid email format it should be like (test@test.com).";
        String MIN_MAX_PASSWORD_LENGTH = "Invalid username length it should be between 8 to 16 characters.";
        String MIN_MAX_TITLE_LENGTH = "Invalid title length it should be between 1 to 100 characters.";
        String MIN_MAX_BODY_LENGTH = "Invalid body length it should be between 1 to 500 characters.";
        String INTERNAL_SERVER_ERROR = "Internal Server Error, please contact the service provider.";
        String USER_EXISTS = "User already exist";
        String USER_NOT_EXISTS = "User doesn't exist";
        String PRIVILEGE_NOT_FOUND = "User privileges doesn't exist";
        String MIN_MAX_COMMENT_LENGTH = "Invalid comment length it should be less or equal to 100 characters.";
        String NOT_ALLOWED_TO_DELETE_ARTICLE = "You dont have permission to delete this article.";
        String NOT_ALLOWED_TO_POST_COMMENT = "You cant post comment on your own article";
        String NOT_ALLOWED_TO_ADD_INTERACTION = "You cant add interaction on your own article";
        String EMAIL_OR_PASSWORD_INCORRECT = "Email or password is incorrect";
        String NO_AUTHORITY = "You don't have the authority to access";

        static String ARTICLE_NOT_EXIST(Long articleId) {
            return "Article with ID " + articleId + " does not exist.";
        }
    }

    interface Endpoints {
        String BASE_URL = "/api/v1/";
        String BASE_AUTH_URL = "auth";
        String ARTICLE = "article";
        String USERS = "user";
    }

    interface Defaults {
        int MAX_FILE_SIZE = 500000;
        int MIN_TITLE_LENGTH = 1;
        int MAX_TITLE_LENGTH = 100;
        int MIN_BODY_LENGTH = 1;
        int MAX_BODY_LENGTH = 500;
        int MAX_COMMENT_LENGTH = 100;
        int MIN_USERNAME_LENGTH = 2;
        int MAX_USERNAME_LENGTH = 256;
        int PASSWORD_MIN_LENGTH = 8;
        int PASSWORD_MAX_LENGTH = 16;
    }

    interface Regex {
        String MOBIL_NUMBER_REGEX = "^5[0-9]{8}$";
    }
}
