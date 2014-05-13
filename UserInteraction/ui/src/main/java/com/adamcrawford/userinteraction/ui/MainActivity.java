package com.adamcrawford.userinteraction.ui;

/**
 * Crated by Adam Crawford
 * Full Sail Online MDVBS
 * Java 1 Week 2 - User Interaction
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adamcrawford.userinteraction.ui.data.JsonHandler;

import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final JsonHandler jsonHandler = new JsonHandler();
        Button subBtn = (Button) findViewById(R.id.submitButton);
        final Button dispBtn = (Button) findViewById(R.id.dispButton);

        View.OnClickListener clicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.submitButton : {
                        JSONObject userData = new JSONObject();
                        dispBtn.setVisibility(View.VISIBLE);
                        jsonHandler.setJSON(userData);
                        break;
                    }
                    case R.id.dispButton : {
                        System.out.println("Get pressed");
                        view.setVisibility(View.GONE);
                        break;
                    }
                    default:
                        break;
                }
            }
        };

        subBtn.setOnClickListener(clicked);
        dispBtn.setOnClickListener(clicked);


        //jsonHandler.getJSON();

    }

}
