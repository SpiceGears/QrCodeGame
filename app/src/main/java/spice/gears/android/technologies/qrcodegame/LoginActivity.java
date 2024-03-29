package spice.gears.android.technologies.qrcodegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    ProgressBar progressBar;
    EditText userEmail;
    EditText userPass;
    Button userLogin;
    Button userForgotPass;
    Button userSignup;

    String weryficateEmail;
    String enterEmail;
    String enterPass;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.etUserEmail);
        userPass = findViewById(R.id.etUserPassword);
        userLogin = findViewById(R.id.btnUserLogin);
        userForgotPass = findViewById(R.id.btnResetPassword);
        userSignup = findViewById(R.id.btnRegister);

        weryficateEmail = getString(R.string.werificate_your_email);
        enterEmail = getString(R.string.enter_email);
        enterPass = getString(R.string.enter_password);

        firebaseAuth = FirebaseAuth.getInstance();

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userEmail.getText().toString().trim()) && !TextUtils.isEmpty(userPass.getText().toString().trim())) {

                    progressBar.setVisibility(View.VISIBLE);

                                        firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        progressBar.setVisibility(View.GONE);

                                                        if (task.isSuccessful()){
                                                            if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                                                startActivity(new Intent(LoginActivity.this, MainAppActivity.class));
                                                            }else {
                                                                Toast.makeText(LoginActivity.this, weryficateEmail,
                                                                        Toast.LENGTH_LONG).show();
                                                            }

                                    }else {
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }else if (TextUtils.isEmpty(userEmail.getText().toString().trim())){
                    Toast.makeText(LoginActivity.this, enterEmail,
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, enterPass,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        userForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                userPass.setText("");
            }
        });

        userSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                userPass.setText("");
                userEmail.setText("");
            }
        });

    }
}
