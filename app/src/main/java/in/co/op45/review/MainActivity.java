package in.co.op45.review;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_session;
    private EditText et_paper;
    private EditText et_comment;
    private EditText et_score;
    private EditText et_rollno;
    private Button bn_save;
    private InputStream stream;
    private NestedScrollView scrollview;
    private ProgressDialog dialog;



    SharedPreferences sharedPref;


    String url = "https://students.iitm.ac.in/studentsapp/put_review.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref =this.getPreferences(Context.MODE_PRIVATE);

        et_session = findViewById(R.id.session);
        et_paper =  findViewById(R.id.paper);
        et_comment =  findViewById(R.id.comment);
        et_score = findViewById(R.id.score);
        et_rollno =  findViewById(R.id.rollno);
        bn_save= findViewById(R.id.save);
        scrollview = findViewById(R.id.scroll);


/*
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score), et_rollno);
        editor.apply();
*/


        dialog = new ProgressDialog(this);
        bn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        final String session = et_session.getText().toString();
        final String paper = et_paper.getText().toString();
        final String comment = et_comment.getText().toString();
        final String score = et_score.getText().toString();
        final String rollno = et_rollno.getText().toString();

        if (session.equals("") || paper.equals("")|| comment.equals("")|| score.equals("")|| rollno.equals("")) makeSnackbar("Empty field");
        else if (!(Integer.parseInt(session)==1 || Integer.parseInt(session)==2 || Integer.parseInt(session)==3))  makeSnackbar("Incorrect session number");
        else if (!(Integer.parseInt(paper)>0 && Integer.parseInt(paper)<6))  makeSnackbar("Incorrect paper number");
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
                                    et_comment.setText("");
                                    et_paper.setText("");
                                    et_rollno.setText("");
                                    et_score.setText("");
                                    et_session.setText("");

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
                    Map<String, String> params = new HashMap<>();

                    params.put("SESSION", session);
                    params.put("PAPER", paper);
                    params.put("ROLL_NO", rollno);
                    params.put("COMMENT", comment);
                    params.put("SCORE", score);
                    return params;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

    }

    private void makeSnackbar(String msg) {

        Snackbar snackbar = Snackbar
                .make(scrollview, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
