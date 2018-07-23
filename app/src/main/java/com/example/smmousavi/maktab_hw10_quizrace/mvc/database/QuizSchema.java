package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

public class QuizSchema {

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "quiz_db";

  public static final class UserTable {
    public static final String NAME = "users";

    public static final class Cols {
      public static final String COLUMN_ID = "id";
      public static final String UUID = "uuid";
      public static final String USER_NAME = "user_name";
      public static final String PASSWORD = "password";
    }
  }


  public static final class QuestionTable {
    public static final String NAME = "questions";

    public static final class Cols {
      public static final String COLUMN_ID = "id";
      public static final String UUID = "uuid";
      public static final String QUESTION_TEXT = "question_text";
      public static final String CATEGORY_ID = "category_id";
      public static final String LEVEL = "level";

    }
  }


  public static final class CategoryTable {
    public static final String NAME = "categories";

    public static final class Cols {
      public static final String COLUMN_ID = "id";
      public static final String UUID = "uuid";
      public static final String CATEGORY_NAME = "category_name";
    }
  }


  public static final class AnswerTable {
    public static final String NAME = "answers";

    public static final class Cols {
      public static final String COLUMN_ID = "id";
      public static final String UUID = "uuid";
      public static final String ANSWER_TEXT = "answer_text";
      public static final String IS_TRUE = "is_true";
      public static final String QUESTION_ID = "question_id";
    }
  }


  public static final class UserAnswerTable {
    public static final String NAME = "users_questions";

    public static final class Cols {
      public static final String COLUMN_ID = "id";
      public static final String USER_ID = "user_id";
      public static final String QUESTION_ID = "question_id";
      public static final String ANSWER_ID = "answeer_id";
    }
  }

}
