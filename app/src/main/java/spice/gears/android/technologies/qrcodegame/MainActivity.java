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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar;
    EditText email;
    EditText teamName;
    EditText nickname;
    EditText password;
    EditText repetPassword;
    Button signup;
    Button login;
    Button forgotPass;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String weryficationEmailSend;
    String passwordNotSame;
    String enterNickname;
    String enterEmailPasswordAndNickname;
    String enterEmailAndPassword;
    String enterEmail;
    String enterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.etEmail);
        password= findViewById(R.id.etPassword);
        teamName = findViewById(R.id.etTeam);
        nickname = findViewById(R.id.etNickname);
        repetPassword = findViewById(R.id.etRepetPassword);
        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.btnUserForgotPassword);

        weryficationEmailSend = getString(R.string.werification_email_send);
        passwordNotSame = getString(R.string.passwords_are_not_the_same);
        enterNickname = getString(R.string.enter_nickname);
        enterEmailPasswordAndNickname = getString(R.string.enter_email_password_and_nickname);
        enterEmailAndPassword = getString(R.string.enter_email_and_password);
        enterEmail = getString(R.string.enter_email);
        enterPassword = getString(R.string.enter_password);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Teams");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(email.getText().toString().trim()) && !TextUtils.isEmpty(password.getText().toString().trim()) && !TextUtils.isEmpty(nickname.getText().toString().trim())){

                    if (password.getText().toString().equals(repetPassword.getText().toString())) {

                        progressBar.setVisibility(View.VISIBLE);

                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                                password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()){

                                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                addTeam();
                                                                if (task.isSuccessful()){
                                                                    progressBar.setVisibility(View.GONE);

                                                                    Toast.makeText(MainActivity.this, weryficationEmailSend,
                                                                            Toast.LENGTH_LONG).show();
                                                                    email.setText("");
                                                                    teamName.setText("");
                                                                    nickname.setText("");
                                                                    password.setText("");
                                                                    repetPassword.setText("");
                                                                }
                                                            }else {
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                                                        Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });

                                        }else{
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(MainActivity.this,task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }else {
                        Toast.makeText(MainActivity.this, passwordNotSame,
                                Toast.LENGTH_LONG).show();
                    }

                }else if (TextUtils.isEmpty(email.getText().toString().trim()) && TextUtils.isEmpty(password.getText().toString().trim()) && TextUtils.isEmpty(nickname.getText().toString().trim())){
                    Toast.makeText(MainActivity.this, enterEmailPasswordAndNickname,
                            Toast.LENGTH_LONG).show();

                }else if (TextUtils.isEmpty(nickname.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, enterNickname,
                            Toast.LENGTH_LONG).show();

                }else if (TextUtils.isEmpty(email.getText().toString().trim())){
                    Toast.makeText(MainActivity.this, enterEmail,
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, enterPassword,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                email.setText("");
                teamName.setText("");
                nickname.setText("");
                password.setText("");
                repetPassword.setText("");
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
                teamName.setText("");
                nickname.setText("");
                password.setText("");
                repetPassword.setText("");
            }
        });

   }
   private void addTeam(){
        String Team = teamName.getText().toString().trim();


        if (!TextUtils.isEmpty(Team)){

            String id = databaseReference.push().getKey();

            AddTeam addTeam = new AddTeam(Team);

            databaseReference.child(id).setValue(addTeam);


       }else {
            Toast.makeText(MainActivity.this, enterNickname,
                    Toast.LENGTH_LONG).show();
        }
   }
}
