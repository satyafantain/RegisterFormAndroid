package com.mytway.validation;

import android.content.Context;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mytway.pojo.Position;
import com.mytway.pojo.registration.CheckboxModel;
import com.mytway.properties.PropertiesValues;
import com.mytway.utility.EthernetConnectivity;

import java.util.regex.Pattern;

public class Validation {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required, String errorMessage) {
        return isValid(editText, EMAIL_REGEX, errorMessage, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(TextView textView, String regex, String errMsg, boolean required) {

        String text = textView.getText().toString().trim();
        textView.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(textView, errMsg) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            textView.setError(errMsg);
            return false;
        }
        return true;
    }

    // return true if it contains text otherwise false
    public static boolean hasText(TextView textView, String errorMessage) {

        String text = textView.getText().toString().trim();
        textView.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            textView.setError(errorMessage);
            return false;
        }

        return true;
    }

    public static boolean hasSetStartWorkTime(TextView textView, String errorMessage, String startWorkButtonTitlePattern) {

        String text = textView.getText().toString().trim();
        textView.setError(null);

        if(text.length() == 0 || text.equals(startWorkButtonTitlePattern)){
            textView.setError(errorMessage);
            return false;
        }
        return true;
    }

    public static boolean hasSetLengthWorkTime(TextView textView, String errorMessage, String lengthWorkButtonTitlePattern) {

        String text = textView.getText().toString().trim();
        textView.setError(null);

        if(text.length() == 0 || text.equals(lengthWorkButtonTitlePattern)){
            textView.setError(errorMessage);
            return false;
        }

        return true;
    }

    public static boolean isAnyRadioGroupSelected(RadioGroup... radioGroups) {
        boolean isRadioButtonChecked = false;

        for(RadioGroup radioGroup : radioGroups){
            if(radioGroup.getCheckedRadioButtonId() == -1){
                //radio is no checked in group
                isRadioButtonChecked = false;
            }else{
                isRadioButtonChecked = true;
                break;
            }
        }
        return isRadioButtonChecked;
    }

    // validating password with retype password
    public static boolean isValidPassword(EditText editText, String errorMessage) {
        if (editText.getText().toString() != null && editText.getText().toString().length() > PropertiesValues.PASSWORD_LENGTH_REQUIRED) {
            return true;
        }
        editText.setError(errorMessage);
        return false;
    }

    public static boolean homePositionIsNotTheSameWorkPosition(Position homePosition, Position workPosition, TextView textView, String errorMessage) {
        if(homePosition.equals(workPosition)){
            textView.setError(errorMessage);
            return false;
        }else{
            return true;
        }
    }

    public static boolean hasSelectedWorkDays(ListView mWorkDaysInWeeklistView) {
        boolean isSelectedAnyCheckbox = false;

        if(mWorkDaysInWeeklistView.getAdapter() != null){
            int count = mWorkDaysInWeeklistView.getAdapter().getCount();

            for(int x = 0; x <= count-1; x++){
                CheckboxModel checkboxModel = (CheckboxModel) mWorkDaysInWeeklistView.getAdapter().getItem(x);
                if(checkboxModel.getValue() == 1){
                    isSelectedAnyCheckbox = true;
                }
            }
        }

        return isSelectedAnyCheckbox;
    }
}
