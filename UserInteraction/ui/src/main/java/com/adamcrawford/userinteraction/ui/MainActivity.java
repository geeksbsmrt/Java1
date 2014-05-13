package com.adamcrawford.userinteraction.ui;

/**
 * Crated by Adam Crawford
 * Full Sail Online MDVBS
 * Java 1 Week 2 - User Interaction
 */
import android.app.Activity;
import android.os.Bundle;
import com.adamcrawford.userinteraction.ui.data.JsonHandler;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final JsonHandler jsonHandler = new JsonHandler();

        jsonHandler.getJSON();

    }

}
