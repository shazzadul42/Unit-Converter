package com.example.unitconverter;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private EditText inputValue;
    private Button convertBtn;
    private TextView resultText;

    private final String[] weightUnits = {"Kilograms", "Grams", "Pounds"};
    private final String[] lengthUnits = {"Meters", "Kilometers", "Feet"};
    private final String[] volumeUnits = {"Liters", "Milliliters", "Gallons"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputValue = findViewById(R.id.inputValue);
        convertBtn = findViewById(R.id.convertBtn);
        resultText = findViewById(R.id.resultText);

        String[] categories = {"Weight", "Length", "Volume"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setUnitSpinners(weightUnits);
                        break;
                    case 1:
                        setUnitSpinners(lengthUnits);
                        break;
                    case 2:
                        setUnitSpinners(volumeUnits);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        convertBtn.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            double input;
            try {
                input = Double.parseDouble(inputStr);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                return;
            }

            String category = categorySpinner.getSelectedItem().toString();
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();

            double result = convertUnits(category, fromUnit, toUnit, input);
            resultText.setText("Result: " + result);
        });
    }

    private void setUnitSpinners(String[] units) {
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, units);
        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);
    }

    private double convertUnits(String category, String from, String to, double value) {
        switch (category) {
            case "Weight":
                if (from.equals("Kilograms") && to.equals("Grams")) return value * 1000;
                if (from.equals("Grams") && to.equals("Kilograms")) return value / 1000;
                if (from.equals("Kilograms") && to.equals("Pounds")) return value * 2.20462;
                if (from.equals("Pounds") && to.equals("Kilograms")) return value / 2.20462;
                if (from.equals("Pounds") && to.equals("Grams")) return value * 453.592;
                if (from.equals("Grams") && to.equals("Pounds")) return value / 453.592;
                break;

            case "Length":
                if (from.equals("Meters") && to.equals("Kilometers")) return value / 1000;
                if (from.equals("Kilometers") && to.equals("Meters")) return value * 1000;
                if (from.equals("Meters") && to.equals("Feet")) return value * 3.28084;
                if (from.equals("Feet") && to.equals("Meters")) return value / 3.28084;
                if (from.equals("Kilometers") && to.equals("Feet")) return value * 3280.84;
                if (from.equals("Feet") && to.equals("Kilometers")) return value / 3280.84;
                break;

            case "Volume":
                if (from.equals("Liters") && to.equals("Milliliters")) return value * 1000;
                if (from.equals("Milliliters") && to.equals("Liters")) return value / 1000;
                if (from.equals("Liters") && to.equals("Gallons")) return value * 0.264172;
                if (from.equals("Gallons") && to.equals("Liters")) return value / 0.264172;
                if (from.equals("Gallons") && to.equals("Milliliters")) return value * 3785.41;
                if (from.equals("Milliliters") && to.equals("Gallons")) return value / 3785.41;
                break;
        }
        return value; // Return unchanged if units are the same or conversion not defined
    }
}