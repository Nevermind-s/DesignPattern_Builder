package boutlendj.salim.designpattern.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import boutlendj.salim.designpattern.MainActivity;
import boutlendj.salim.designpattern.R;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnRegister;
    private ProgressBar mProgressBar;
    private EditText mLastName, mFirstName, mAge, mPhone, mAddress, mEmail, mPassword, mGender;
    private Spinner spinner;
    private Washer mWasher;
    private FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnRegister = findViewById(R.id.button_register1);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLastName = findViewById(R.id.last_name);
        mFirstName = findViewById(R.id.first_name);
        mAge = findViewById(R.id.age);
        mPhone = findViewById(R.id.phone_number);
        mAddress = findViewById(R.id.address);
        mProgressBar = findViewById(R.id.register_progress);
        spinner = findViewById(R.id.gender_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Washers");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmailValid(mEmail.getText().toString())) {
                    mEmail.setError(getString(R.string.error_invalid_email));
                }else {
                    if (!isPasswordValid(mPassword.getText().toString())) {
                        mPassword.setError(getString(R.string.error_invalid_password));
                    }else {
                        mWasher = new Washer(mFirstName.getText().toString(), mLastName.getText().toString(), mEmail.getText().toString(),
                                mAge.getText().toString(), mAddress.getText().toString(), mPhone.getText().toString(), spinner.getSelectedItem().toString());
                        Log.e("Login", "HEEEEEEEEEEEEEEEEEEEEy" + mWasher.getEmail());
                        mAuth.createUserWithEmailAndPassword(mWasher.getEmail(), mPassword.getText().toString())
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("Login", "" + task.getException());
                                        } else {
                                            mWasher.setID(mAuth.getCurrentUser().getUid());
                                            mRef.child(mWasher.getID()).setValue(mWasher);
                                            connectUser(mWasher.getEmail(), mPassword.getText().toString());
                                        }
                                    }
                                });
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    public boolean isEmailValid (String mEmail) {
        return (mEmail.contains("@"));
    }

    public boolean isPasswordValid (String mPassword) {
        return (mPassword.length() > 6);
    }

    public void connectUser (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){

                        } else {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("Washer", mWasher);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}


