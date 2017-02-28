package com.example.zouxuan.calorieconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner equaltoSpinner;
    private EditText amountText;
    private RadioType type = RadioType.REPS;
    private Button convertButton;
    private TextView convertResult;
    private TextView equalResult;
    private String exercise = "";
    private RadioGroup radioGroup;
    Converter converter = new Converter();
    private String[] exerciseTable = new String[]{"pushup", "situp", "jumping", "jogging"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountText = (EditText) findViewById(R.id.amount);
        convertButton = (Button) findViewById(R.id.convert);
        convertResult = (TextView) findViewById(R.id.convert_result);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        equalResult = (TextView) findViewById(R.id.equaltoResult);
        createSpinner();
        createEqualtoSpinner();
        equalResult.setText("");
        convertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double amount = 0;
                RadioType type = RadioType.REPS;
                String s = amountText.getText().toString();
                System.out.println("on click");
                try {
                    amount = Double.parseDouble(s);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                    amountText.setText("");
                    return;
                }
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(radioId);

                if (radioButton.getText().toString().equalsIgnoreCase("reps")) {
                    type = RadioType.REPS;
                } else {
                    type = RadioType.MINUTES;
                }
                int calories = converter.convertFromExercise(exercise, amount, type);
                System.out.println("call converter");
                if (calories > 0) {
                    convertResult.setText(calories + " calories");
                } else {
                    convertResult.setText("");
                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                }
                equalResult.setText("");
            }
        });

    }

    private void createSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exercise = exerciseTable[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                exercise = exerciseTable[0];
            }
        });
    }

    private void createEqualtoSpinner() {
        equaltoSpinner = (Spinner) findViewById(R.id.equaltoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equaltoSpinner.setAdapter(adapter);
        equaltoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String targetExercise = exerciseTable[position];

                double amount = 0;
                String s = amountText.getText().toString();
                System.out.println("In Spinner");
                try {
                    amount = Double.parseDouble(s);
                } catch (Exception e) {
                    amountText.setText("");
                }
                int targetAmount = 0;
                String typeString = "";
                if (targetExercise.equalsIgnoreCase(exercise)) {
                    targetAmount = (int) amount;
                } else {
                    targetAmount = converter.convertToAnotherExercise(exercise, amount, targetExercise);
                }
                typeString = converter.getTypeString(targetExercise);
                equalResult.setText(targetAmount + " " + typeString);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
