package in.co.op45.review;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by DELL on 2/22/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int type = sharedPref.getInt(getString(R.string.type), -1);
        if (type != -1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences.Editor editor = sharedPref.edit();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_faculty:
                if (checked) {
                    // Pirates are the best
                    editor.putInt(getString(R.string.type), 1);
                    editor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    break;
                }

            case R.id.radio_student:
                if (checked) {
                    // Ninjas rule
                    editor.putInt(getString(R.string.type), 0);
                    editor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
        }
    }
}
