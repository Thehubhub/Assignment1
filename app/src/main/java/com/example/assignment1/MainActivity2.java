package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
    private EditText myEmail;
    private EditText myPass;
    private EditText myRePass;

    private FirebaseAuth mAuth;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            mAuth = FirebaseAuth.getInstance();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            myEmail = findViewById(R.id.email_entry);
            myPass = findViewById(R.id.pass_create);
            myRePass = findViewById(R.id.pass_repeat);
            Button nxt_btn = findViewById(R.id.next_btn);

            myPass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
            myRePass.setTransformationMethod(new AsteriskPasswordTransformationMethod());


            nxt_btn.setOnClickListener(view -> {
                checkForData();
            });
        }



        //check email form
    boolean isEmail(EditText text) {
        CharSequence myEmail = text.getText().toString();
        return (!TextUtils.isEmpty(myEmail) && Patterns.EMAIL_ADDRESS.matcher(myEmail).matches());
    }



    // back button on action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }



    // hide password
    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }
        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*';
            }
            public int length() {
                return mSource.length();
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }
    };




        // password restriction validation
        public boolean isValidPassword(final String password) {

            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();
        }




        //password and email validation
    public void checkForData() {
        String pass = myPass.getText().toString();
        String repass = myRePass.getText().toString();
        String email = myEmail.getText().toString();


        if (!isEmail(myEmail)) {
            myEmail.setError("Not an valid email!");
            return;
        }
        if(email.isEmpty()){
            myEmail.setError("Email is missing.");
        }
        if (!isValidPassword(myPass.getText().toString())) {
            myPass.setError("Password does not follow qualification.");
            return;
        }
        if (!isValidPassword(myRePass.getText().toString())) {
            myRePass.setError("Password does not follow qualification.");
            return;
        }
        if (isValidPassword(myRePass.getText().toString()) != isValidPassword(myPass.getText().toString())){

            AlertDialog.Builder error_mes = new AlertDialog.Builder(this);
            error_mes.setMessage("Your passwords don't match!");
            error_mes.setTitle("Error message.");
            error_mes.setPositiveButton("OK", null);
            error_mes.setCancelable(true);
            error_mes.create().show();

            error_mes.setPositiveButton("OK", (dialogInterface, which) -> {
            });
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(myEmail, myPass);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText( MainActivity2.this, "Account has been created.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity2.this, "Fail to register. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(MainActivity2.this, "Fail to register. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}



