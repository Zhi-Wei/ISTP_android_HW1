package com.example.user.myandroidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EditText.OnEditorActionListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    static final String[] pokemonNames = {"小火龍", "傑尼龜", "妙蛙種子"};
    TextView infoText;
    EditText nameEditText;
    RadioGroup optionsGroup;
    Button confirmBtn;
    //add a checkbox member variable
    CheckBox hideCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        //find UIs by their ids
        infoText = (TextView) findViewById(R.id.infoText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        nameEditText.setOnEditorActionListener(this);
        nameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        optionsGroup = (RadioGroup) findViewById(R.id.optionsGroup);
        optionsGroup.setOnCheckedChangeListener(MainActivity.this);

        confirmBtn = (Button) findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(MainActivity.this);

        //find the checkBox UI here
        hideCheckBox = (CheckBox) findViewById(R.id.hideCheckBox);
        hideCheckBox.setOnCheckedChangeListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.confirmButton) {
            //confirmButton was clicked
            Log.d("buttonTest", "confirm-button was clicked");

            showWelcomeMessage(hideCheckBox.isChecked() == false);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //dismiss virtual keyboard
            InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            //simulate button clicked
            confirmBtn.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (nameEditText.getText().toString().isEmpty() == false) {
            showWelcomeMessage(isChecked == false);
        } else {
            infoText.setText(getResources().getText(R.string.please_enter_your_personal_info));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (nameEditText.getText().toString().isEmpty() == false) {
            showWelcomeMessage(hideCheckBox.isChecked() == false);
        } else {
            infoText.setText(getResources().getText(R.string.please_enter_your_personal_info));
        }
    }

    private void showWelcomeMessage(boolean hasTrainerName) {
        int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
        View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);

        int selectedIndex = optionsGroup.indexOfChild(selectedRadioButtonView);
        String pokemonName = pokemonNames[selectedIndex];

        String name = hasTrainerName
                ? String.format("訓練家%s ", nameEditText.getText().toString().trim())
                : "";

        String welcomeMessage = String.format(
                "你好, %s歡迎來到神奇寶貝的世界 你的第一個夥伴是%s",
                name,
                pokemonName
        );
        infoText.setText(welcomeMessage);
    }
}
