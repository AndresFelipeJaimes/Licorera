package com.licorera.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.licorera.R;
import com.licorera.models.UserModel;

public class RegistrationActivity extends AppCompatActivity {
    Button signIn;
    EditText name, email, password;
    TextView signUp;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        signUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.name_reg);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_login);
        signIn = findViewById(R.id.sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Debes cambiar "startActivities" por "startActivity" y usar Intent de LoginActivity.
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Debes cambiar "startActivities" por "startActivity" y usar Intent de RegistrationActivity.
                startActivity(new Intent(RegistrationActivity.this, RegistrationActivity.class));
                createUser();
            }
        });
    }

    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Nombre esta vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email esta vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Contraseña esta vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserModel userModel = new UserModel(userName,userEmail,userPassword);
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);

                            Toast.makeText(RegistrationActivity.this,"Registration Succesful",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistrationActivity.this,"Paila error"+task.getException(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        // Aquí puedes agregar código para crear el usuario en Firebase Auth
        // Por ejemplo, puedes usar el objeto "auth" que ya has inicializado.

        // auth.createUserWithEmailAndPassword(userEmail, userPassword)
        //     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        //         @Override
        //         public void onComplete(@NonNull Task<AuthResult> task) {
        //             if (task.isSuccessful()) {
        //                 // El usuario se ha creado correctamente
        //             } else {
        //                 // Ocurrió un error al crear el usuario
        //             }
        //         }
        //     });
    }
}
