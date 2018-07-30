package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.CategoryTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;

import java.util.UUID;

public class CategoryCursorWrapper extends CursorWrapper {


    public CategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Category getCategory() {
        String categoryId = getString(getColumnIndex(CategoryTable.Cols.UUID));
        String categogryName = getString(getColumnIndex(CategoryTable.Cols.CATEGORY_NAME));
        Category category = new Category(UUID.fromString(categoryId), categogryName);
        category.setSerie(getInt(getColumnIndex(CategoryTable.Cols.Serie)));
        return category;
    }
}
