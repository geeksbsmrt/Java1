/*
Adam Crawford
Full Sail Online
Java 1 - Week 1 - Linear Layout
Due 05/09/2014
 */

package com.adamcrawford.linearlayout;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    private LinearLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create LinearLayout view
        myLayout = new LinearLayout(this);
        //build parameters for layout
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //set parameters
        myLayout.setLayoutParams(lp);
        //enforce a vertical layout build
        myLayout.setOrientation(LinearLayout.VERTICAL);

        //create a text view
        TextView desc = new TextView(this);
        //set it's text
        desc.setText(R.string.appDesc);

        //create text input marked as final to pull data out of it later
        final EditText cash = new EditText(this);
        //set input type to accept only numbers
        cash.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //set hint
        cash.setHint(R.string.cash);

        //create text input marked as final to pull data out of it later
        final EditText price = new EditText(this);
        //set input type to accept only numbers
        price.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //set hint
        price.setHint(R.string.price);

        //create text input marked as final to pull data out of it later
        final EditText tax = new EditText(this);
        //set input type to accept only numbers
        tax.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //set hint
        tax.setHint(R.string.tax);

        //create button element
        final Button calc = new Button(this);
        //set text on button
        calc.setText(R.string.calc);
        //set and create onclick method at once
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("in button click");

                // Check for actual returned value, handle error gracefully
                try {
                    view.setEnabled(false);
                    final LinearLayout affordView = new LinearLayout(MainActivity.this);
                    //build parameters for layout
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //set parameters
                    affordView.setLayoutParams(lp);
                    //enforce a vertical layout build
                    affordView.setOrientation(LinearLayout.VERTICAL);

                    //Call calculate method using the input from both fields converted to Floats, will be returned as a float
                    Float calculated = calculate(Float.valueOf(price.getText().toString()),Float.valueOf(tax.getText().toString()));

                    System.out.println(String.format("Calculated is: %f", calculated));
                    //Call afford method to determine if it can be afforded
                    Boolean isAffordable = afford(calculated, Float.valueOf(cash.getText().toString()));

                    //convert to string
                    String cost = String.format("%.2f", calculated);
                    System.out.println(String.format("cost is: %s",cost));

                    //create text view
                    TextView total = new TextView(MainActivity.this);

                    //build output format
                    String totalStr = String.format("Total Cost: $%s. ", cost);

                    //output formatted text to view
                    total.setText(totalStr);
                    affordView.addView(total);

                    TextView affordableText = new TextView(MainActivity.this);
                    TextView discountText = new TextView(MainActivity.this);

                    if (isAffordable &! (calculated < 500f)) {
                        affordableText.setText(R.string.expensive);
                        affordView.addView(affordableText);
                    } else if (isAffordable && (calculated < 500f)) {
                        affordableText.setText(R.string.canAfford);
                        affordView.addView(affordableText);
                    } else {
                        affordableText.setText(R.string.notAfford);
                        affordView.addView(affordableText);
                        Integer neededDiscount = discount(calculated, Float.valueOf(cash.getText().toString()));
                        discountText.setText(String.format("You need a discount of %s%% to afford this item.", neededDiscount.toString()));
                        affordView.addView(discountText);
                    }

                    Button affordButton = new Button(MainActivity.this);
                    affordButton.setText("Clear");
                    affordButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myLayout.removeView(affordView);
                            cash.setText("");
                            price.setText("");
                            tax.setText("");
                            calc.setEnabled(true);
                        }
                    });
                    affordView.addView(affordButton);
                    myLayout.addView(affordView);

                } catch (Exception e) {
                    //Error handling
                    e.printStackTrace();
                }
            }
        });

        //add elements to view
        myLayout.addView(desc);
        myLayout.addView(cash);
        myLayout.addView(price);
        myLayout.addView(tax);
        myLayout.addView(calc);

        //push view to screen
        setContentView(myLayout);
    }

    private Float calculate (Float price, Float tax) {
        return (price + (price*(tax/100)));
    }

    private Boolean afford (Float cost, Float cash) {
        return (cost < cash);
    }

    private Integer discount (Float cost, Float cash) {
        //Create internal Integer var
        Integer discPer = 0;
        Float costTest = cost;

        do {
            discPer++;
            System.out.println(String.format("discPer is: %s",discPer.toString()));
            costTest -= (costTest * (discPer.floatValue() / 100));
            System.out.println(costTest.toString());
        } while (costTest > cash);

        System.out.println(discPer.toString());
        return discPer;
    }

}
