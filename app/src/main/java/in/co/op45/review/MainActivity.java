package in.co.op45.review;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    SharedPreferences sharedPref;
    String url = "https://students.iitm.ac.in/studentsapp/put_review.php";
    String sess = "-1", speaker = "-1";
    private EditText et_score;
    private Button bn_save;
    private InputStream stream;
    private CoordinatorLayout coordinator;
    private ProgressDialog dialog;
    private RadioGroup rg_speaker;
    private RadioButton rb_sp1, rb_sp2, rb_sp3, rb_sp4, rb_sp5;
    private TextView tv1, tv2, tv3, tv4, tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref =this.getPreferences(Context.MODE_PRIVATE);

        et_score = findViewById(R.id.score);
        bn_save= findViewById(R.id.save);
        coordinator = findViewById(R.id.coordinator);
        dialog = new ProgressDialog(this);
        bn_save.setOnClickListener(this);
        rg_speaker = findViewById(R.id.rg_speaker);

        rb_sp1 = findViewById(R.id.radio_sp_1);
        rb_sp2 = findViewById(R.id.radio_sp_2);
        rb_sp3 = findViewById(R.id.radio_sp_3);
        rb_sp4 = findViewById(R.id.radio_sp_4);
        rb_sp5 = findViewById(R.id.radio_sp_5);

        tv1 = findViewById(R.id.topic_1);
        tv2 = findViewById(R.id.topic_2);
        tv3 = findViewById(R.id.topic_3);
        tv4 = findViewById(R.id.topic_4);
        tv5 = findViewById(R.id.topic_5);

        Spinner spinner = findViewById(R.id.session_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sessions_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences.Editor editor = sharedPref.edit();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_sp_1:
                if (checked) {
                    // Pirates are the best
                    speaker = "1";
                    break;
                }
            case R.id.radio_sp_2:
                if (checked) {
                    // Pirates are the best
                    speaker = "2";
                    break;
                }
            case R.id.radio_sp_3:
                if (checked) {
                    // Pirates are the best
                    speaker = "3";
                    break;
                }
            case R.id.radio_sp_4:
                if (checked) {
                    // Pirates are the best
                    speaker = "4";
                    break;
                }
            case R.id.radio_sp_5:
                if (checked) {
                    // Pirates are the best
                    speaker = "5";
                    break;
                }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String session = parent.getItemAtPosition(pos).toString();
        if (session.equalsIgnoreCase("1")) {
            rb_sp1.setText(R.string.ses1_sp1);
            rb_sp2.setText(R.string.ses1_sp2);
            rb_sp3.setText(R.string.ses1_sp3);
            rb_sp4.setText(R.string.ses1_sp4);
            rb_sp5.setVisibility(View.GONE);
            rb_sp5.setClickable(false);

            tv1.setText(R.string.ses1_t1);
            tv2.setText(R.string.ses1_t2);
            tv3.setText(R.string.ses1_t3);
            tv4.setText(R.string.ses1_t4);
            tv5.setVisibility(View.GONE);

            sess = "1";
        } else if (session.equalsIgnoreCase("2")) {

            rb_sp5.setVisibility(View.VISIBLE);
            rb_sp5.setClickable(true);
            tv5.setVisibility(View.VISIBLE);

            rb_sp1.setText(R.string.ses2_sp1);
            rb_sp2.setText(R.string.ses2_sp2);
            rb_sp3.setText(R.string.ses2_sp3);
            rb_sp4.setText(R.string.ses2_sp4);
            rb_sp5.setText(R.string.ses2_sp5);

            tv1.setText(R.string.ses2_t1);
            tv2.setText(R.string.ses2_t2);
            tv3.setText(R.string.ses2_t3);
            tv4.setText(R.string.ses2_t4);
            tv5.setText(R.string.ses2_t5);

            sess = "2";
        } else if (session.equalsIgnoreCase("3")) {

            rb_sp5.setVisibility(View.VISIBLE);
            rb_sp5.setClickable(true);
            tv5.setVisibility(View.VISIBLE);

            rb_sp1.setText(R.string.ses3_sp1);
            rb_sp2.setText(R.string.ses3_sp2);
            rb_sp3.setText(R.string.ses3_sp3);
            rb_sp4.setText(R.string.ses3_sp4);
            rb_sp5.setText(R.string.ses3_sp5);

            tv1.setText(R.string.ses3_t1);
            tv2.setText(R.string.ses3_t2);
            tv3.setText(R.string.ses3_t3);
            tv4.setText(R.string.ses3_t4);
            tv5.setText(R.string.ses3_t5);

            sess = "3";
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        rg_speaker.setVisibility(View.GONE);
        rg_speaker.setClickable(false);
    }


    @Override
    public void onClick(View view) {

        final String session = sess;
        final String score = et_score.getText().toString();

        if (session.equals("-1") || score.equals("") || speaker.equalsIgnoreCase("-1"))
            makeSnackbar("Empty field");
        else if (!(Integer.parseInt(score)>0 && Integer.parseInt(score)<11))  makeSnackbar("Overall score should be between 1 and 10");
        else {

            dialog.setMessage("Saving your review.");
            dialog.setCancelable(false);
            dialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("dood", response);

                    stream = new ByteArrayInputStream(response.getBytes(Charset.forName("UTF-8")));
                    JsonReader reader = null;
                    try {
                        reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
                        reader.setLenient(true);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    try {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String name = reader.nextName();
                            Log.e("name", name);
                            if (name.equals("status")) {
                                String status = reader.nextString();
                                if (status.equals("1")) {
                                    makeSnackbar("Review saved");
                                    et_score.setText("");


                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }

                                } else if (status.equals("0")) {
                                    makeSnackbar("Error Saving Review");
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            } else if (name.equals("error")) {
                                reader.nextString();
                                makeSnackbar("Error Saving Review");
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            } else {
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                        makeSnackbar("Error Saving Review");
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } finally {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            makeSnackbar("Error Saving Review");
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    makeSnackbar("Error Saving Review");
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                    int type = sharedPref.getInt(getString(R.string.type), 0);
                    Map<String, String> params = new HashMap<>();

                    params.put("SESSION", session);
                    params.put("PAPER", speaker);
                    params.put("SCORE", score);
                    params.put("TYPE", "" + type);
                    return params;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

    }

    private void makeSnackbar(String msg) {

        Snackbar snackbar = Snackbar
                .make(coordinator, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
