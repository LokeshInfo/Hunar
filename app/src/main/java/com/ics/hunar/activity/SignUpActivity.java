package com.ics.hunar.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.ics.hunar.Constant;
import com.ics.hunar.R;
import com.ics.hunar.helper.AlertDialogUtil;
import com.ics.hunar.helper.ApiClient;
import com.ics.hunar.helper.ApiInterface;
import com.ics.hunar.helper.NetworkUtils;
import com.ics.hunar.helper.Session;
import com.ics.hunar.helper.Utils;
import com.ics.hunar.helper.ValidationUtil;
import com.ics.hunar.model.Get_CatLang_Response;
import com.ics.hunar.model.Language_Response;
import com.ics.hunar.model.NormalSignUpResponse;
import com.ics.hunar.model.SendOtpResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    public EditText edtName, edtEmail, edtPassword,edtPassword2, edtMobile, edtOTP, eddob , edcity, edstate;
    public TextInputLayout inputName, inputEmail, inputPass, inputOtp, inputMobile;
    public ProgressDialog mProgressDialog;
    public static FirebaseAuth mAuth;
    private Spinner spspcategory,splanguage;
    private Button btnSendOTP;
    private ProgressBar progressBar;
    private ArrayList<String> city, state, category, language;
    String token, languageid="", categoryid="";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        edtName = findViewById(R.id.edtNameSignUp);
        edtEmail = findViewById(R.id.edtEmailSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtPassword2 = findViewById(R.id.edtcnfPasswordSignUp);
        edtMobile = findViewById(R.id.edtMobileSignUp);
        edtOTP = findViewById(R.id.edtOTPSignUp);
        eddob = findViewById(R.id.eddob);
        inputMobile = findViewById(R.id.inputMobile);
        inputOtp = findViewById(R.id.inputOtp);
        edcity = findViewById(R.id.edtcity);
        edstate = findViewById(R.id.edtstate);
        splanguage = findViewById(R.id.splanguage);
        spspcategory = findViewById(R.id.spspcategory);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);
        progressBar = findViewById(R.id.pbSignUp);
        token = Session.getDeviceToken(getApplicationContext());
        if (token == null) {
            token = "token";
        }
        mAuth = FirebaseAuth.getInstance();

        edtName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);
        edtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, 0, 0);
        edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_show, 0);
        edtPassword2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_show, 0);
        edtPassword.setTag("show");
        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (edtPassword.getTag().equals("show")) {
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_hide, 0);
                            edtPassword.setTransformationMethod(null);
                            edtPassword.setTag("hide");
                        } else {
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_show, 0);
                            edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                            edtPassword.setTag("show");
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        eddob.setOnClickListener(v->{   dobpopup(eddob);   });
        initspinner();

    }

    private void initspinner(){

        showProgressDialog();

        ApiClient.getApiService().get_languages(Constant.accessKeyValue,"1").enqueue(new Callback<Language_Response>() {
            @Override
            public void onResponse(Call<Language_Response> call, Response<Language_Response> response) {
                hideProgressDialog();
                Utils.retro_call_info(""+response.raw().request().url(),""+new Gson().toJson(response.body()));

                if (response.isSuccessful()){
                    if (response.body().error.equals("false")){

                        language = new ArrayList<>();
                        language.add("Select Language");
                        for (int i=0 ; i<response.body().data.size(); i++){
                            language.add(response.body().data.get(i).language);
                        }
                        ArrayAdapter<String> adap = new ArrayAdapter<>(SignUpActivity.this,
                                android.R.layout.simple_list_item_1, language);
                        splanguage.setAdapter(adap);

                        if (splanguage.getAdapter()!=null)
                        splanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int k, long l) {
                                String sel = adapterView.getItemAtPosition(k).toString();

                                if (sel.equals("Select Language")){
                                    init_spcategory();
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                }else {
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                    for (int i = 0; i < response.body().data.size(); i++) {
                                        if (sel.equals(response.body().data.get(i).language)) {
                                            get_category(response.body().data.get(i).id);
                                            break;
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onFailure(Call<Language_Response> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init_spcategory(){
        languageid = "";
        category = new ArrayList<>();
        category.add("Select Category");

        ArrayAdapter<String> adap = new ArrayAdapter<>(SignUpActivity.this,
                android.R.layout.simple_list_item_1, category);
        spspcategory.setAdapter(adap);
        spspcategory.setEnabled(false);
    }

    private void get_category(String id){

        languageid = id;

        showProgressDialog();

        ApiClient.getApiService().get_cat_languages(Constant.accessKeyValue,"1",id).enqueue(new Callback<Get_CatLang_Response>() {
            @Override
            public void onResponse(Call<Get_CatLang_Response> call, Response<Get_CatLang_Response> response) {
                hideProgressDialog();
                Utils.retro_call_info("" + response.raw().request().url(), "" + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {
                    if (response.body().getError().equals("false")) {
                        spspcategory.setEnabled(true);
                        category = new ArrayList<>();
                        category.add("Select Category");
                        for (int i=0 ; i<response.body().getData().size(); i++){
                            category.add(response.body().getData().get(i).getCategoryName());
                        }
                        ArrayAdapter<String> adap = new ArrayAdapter<>(SignUpActivity.this,
                                android.R.layout.simple_list_item_1, category);
                        spspcategory.setAdapter(adap);

                        if (spspcategory.getAdapter()!=null)
                        spspcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int k, long l) {
                                String sel = adapterView.getItemAtPosition(k).toString();

                                if (sel.equals("Select Category")){
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                    categoryid="";
                                }else{
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                    for (int i = 0; i < response.body().getData().size(); i++) {
                                        if (sel.equals(response.body().getData().get(i).getCategoryName())) {
                                            categoryid = response.body().getData().get(i).getId();
                                            break;
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<Get_CatLang_Response> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ForgotPassword(View view) {

        FirebaseAuth.getInstance().sendPasswordResetEmail("user@example.com")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        //String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            inputName.setError("Required.");
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.email_alert_1));
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            inputEmail.setError(getString(R.string.email_alert_1));
        } else {
            inputEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            inputPass.setError("Required.");
            valid = false;
        } else if (password.length() < 6) {
            inputPass.setError(getString(R.string.password_valid));
            valid = false;
        } else {
            inputPass.setError(null);
        }


        return valid;
    }

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        //findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            //String refer = edtRefer.getText().toString();
//                            if (!refer.isEmpty())
//                                Session.setFCode(refer, getApplicationContext());
                            Toast.makeText(SignUpActivity.this, getString(R.string.verify_email_sent) + user.getEmail(), Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        } else {

                            Toast.makeText(SignUpActivity.this, getString(R.string.verify_email_sent_f), Toast.LENGTH_LONG).show();
                            final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential authCredential = EmailAuthProvider.getCredential(edtEmail.getText().toString(), edtPassword.getText().toString());
                            user1.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    user1.delete();
                                }
                            });

                            //auth.getCurrentUser().delete();
                        }
                        // [END_EXCLUDE]
                    }
                });

        // [END send_email_verification]
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("please wait otp sending...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void SignUpWithNormal(View view) {
        if (NetworkUtils.isNetworkConnected(this)) {
                if (ValidationUtil.optionalEmailEditTextValidation(this,edtEmail)) {
                        if (ValidationUtil.editTextValidation(edtOTP)) {
                            if (validation()) {
                                signUpWithApi(edtName.getText().toString().trim(), edtEmail.getText().toString().trim(), edtMobile.getText().toString().trim(), edtPassword.getText().toString().trim(), edtOTP.getText().toString().trim());
                            }
                        }
                }
        } else {
            AlertDialogUtil.showAlertDialogBox(this, "Warning", "no internet available", false, "Ok", "", "response");
        }
    }

    private Boolean validation(){

        String name,mobile, email, pass, cnfpass;

        name = edtName.getText().toString();
        mobile = edtMobile.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPassword.getText().toString();
        cnfpass = edtPassword2.getText().toString();

        if (name.matches("")){
            Toast.makeText(this, "** Please Enter Name...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (mobile.matches("")){
            Toast.makeText(this, "** Please Enter Mobile Number...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (mobile.length()!=10){
            Toast.makeText(this, "** Please Enter Valid Mobile Number...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (languageid.matches("")){
            Toast.makeText(this, "** Please Select Language...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (categoryid.matches("")){
            Toast.makeText(this, "** Please Select Preferred Category...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (pass.matches("")){
            Toast.makeText(this, "** Please Enter Password...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (cnfpass.matches("")){
            Toast.makeText(this, "** Please Enter Confirm Password...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!pass.equals(cnfpass)){
            Toast.makeText(this, "** Password Does Not Match...", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void signUpWithApi(String name, String email, String mobile, String password, String otp) {

        String dob,city,state;
        dob = eddob.getText().toString();
        city = edcity.getText().toString();
        state = edstate.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getMainClient().create(ApiInterface.class);
        apiInterface.normalSignUpResponse(Constant.accessKeyValue, "1", name, email, mobile, password, otp,
                dob,languageid,categoryid, city, state).enqueue(new Callback<NormalSignUpResponse>() {
            @Override
            public void onResponse(Call<NormalSignUpResponse> call, Response<NormalSignUpResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getError().equalsIgnoreCase("false")) {
                            Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Info", response.body().getMessage(), false, "Ok", "", "response");
                        }
                    } else {
                        AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Info", "null", false, "Ok", "", "response");
                    }

                }
            }

            @Override
            public void onFailure(Call<NormalSignUpResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Error", t.getLocalizedMessage(), false, "Ok", "", "response");
            }
        });
    }

    private void sendOtp(String mobile) {
        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getMainClient().create(ApiInterface.class);
        apiInterface.sendOtpToUser("6808", "1", mobile).enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                hideProgressDialog();
                Utils.retro_call_info(""+response.raw().request().url(),""+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Info", "otp sent to: " + mobile, false, "Ok", "", "response");
                        } else {
                            AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Info", "otp not sent", false, "Ok", "", "response");
                        }
                    } else {
                        AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Info", "null", false, "Ok", "", "response");
                    }
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                hideProgressDialog();
                AlertDialogUtil.showAlertDialogBox(SignUpActivity.this, "Error", t.getLocalizedMessage(), false, "Ok", "", "response");
            }
        });
    }

    public void sendOTPToUser(View view) {
        if (NetworkUtils.isNetworkConnected(this)) {
            if (ValidationUtil.phoneEditTextValidation(edtMobile)) {
                sendOtp(edtMobile.getText().toString().trim());
            }
        } else {
            AlertDialogUtil.showAlertDialogBox(this, "Warning", "no internet available", false, "Ok", "", "response");
        }
    }

    public void dobpopup(EditText edtxt) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yyear, int mmonth, int dday) {
                mmonth = mmonth + 1;
                //Log.d(" DATE DIALOG ", "onDateSet: mm/dd/yyy: " + dday + "/" + mmonth + "/" + yyear);
                String date = yyear + "-" + mmonth + "-" + dday;
                edtxt.setText(""+date);
            }};

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
