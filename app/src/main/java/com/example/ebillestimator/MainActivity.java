package com.example.ebillestimator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Spinner monthSpinner;
    EditText yearInput, editMonthlyUsage, editRate;
    RadioGroup radioRebateGroup;
    Button btnCalculate;
    TextView txtResult, txtFinalCost;

    String selectedMonth, year;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        // Link UI components
        monthSpinner = findViewById(R.id.monthSpinner);
        yearInput = findViewById(R.id.yearInput);
        editMonthlyUsage = findViewById(R.id.editMonthlyUsage);
        editRate = findViewById(R.id.editRate);
        radioRebateGroup = findViewById(R.id.radioRebateGroup);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);
        txtFinalCost = findViewById(R.id.txtFinalCost);

        // Spinner setup
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = parent.getItemAtPosition(position).toString();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {
                selectedMonth = "";
            }
        });

        btnCalculate.setOnClickListener(v -> calculateBill());
    }

    private void calculateBill() {
        String usageStr = editMonthlyUsage.getText().toString().trim();
        String rateStr = editRate.getText().toString().trim();
        year = yearInput.getText().toString().trim();

        // Get selected rebate percentage
        int selectedRebateId = radioRebateGroup.getCheckedRadioButtonId();
        if (selectedRebateId == -1) {
            Toast.makeText(this, "Please select a rebate percentage!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadio = findViewById(selectedRebateId);
        String rebateText = selectedRadio.getText().toString().replace("%", "").trim();
        double rebatePercent = Double.parseDouble(rebateText);

        // Input validation
        if (TextUtils.isEmpty(usageStr) || TextUtils.isEmpty(rateStr) || TextUtils.isEmpty(year)) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double kwh = Double.parseDouble(usageStr);
            double rate = Double.parseDouble(rateStr);

            double totalCharge = kwh * rate;
            double rebateAmount = totalCharge * (rebatePercent / 100);
            double finalCost = totalCharge - rebateAmount;

            String resultText = "📊 Total Charges: RM " + String.format("%.2f", totalCharge);
            String finalText = "💸 Final Cost After Rebate: RM " + String.format("%.2f", finalCost);

            txtResult.setText(resultText);
            txtFinalCost.setText(finalText);

            // Save to SQLite
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("month", selectedMonth);
            values.put("year", year);
            values.put("kwh", kwh);
            values.put("rate", rate);
            values.put("rebate", rebatePercent);
            values.put("total", totalCharge);
            values.put("final_cost", finalCost);
            db.insert("bills", null, values);

            Toast.makeText(this, "✅ Bill saved locally!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
        }
    }
}
