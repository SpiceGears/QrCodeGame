package spice.gears.android.technologies.qrcodegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView userEmail;
    Button userSendPass;

    FirebaseAuth firebaseAuth;

    String forgot_pass_button;
    String enterEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.etUserEmail);
        userSendPass = findViewById(R.id.btnUserForgotPassword);

        enterEmailBtn = getString(R.string.enter_email);
        forgot_pass_button = getString(R.string.forgot_pass_email_send);

        firebaseAuth = FirebaseAuth.getInstance();

        userSendPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userEmail.getText().toString().trim())){
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()){
                                        Toast.makeText(ForgotPasswordActivity.this,
                                                forgot_pass_button, Toast.LENGTH_LONG).show();

                                        userEmail.setText("");

                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                    }else {
                                        Toast.makeText(ForgotPasswordActivity.this,
                                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(ForgotPasswordActivity.this,
                            enterEmailBtn, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
