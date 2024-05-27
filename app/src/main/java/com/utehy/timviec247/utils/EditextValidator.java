package com.utehy.timviec247.utils;

import android.widget.EditText;

public class EditextValidator {

    public static boolean validateAllEditTexts(EditText... editTexts) {
        boolean isValid = true;

        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Yêu cầu nhập thông tin");
                isValid = false;
            } else {
                editText.setError(null);
            }
        }

        return isValid;
    }
    public static String[] getTextFromEditTexts(EditText... editTexts) {
        String[] texts = new String[editTexts.length];

        for (int i = 0; i < editTexts.length; i++) {
            texts[i] = editTexts[i].getText().toString();
        }

        return texts;
    }
}
