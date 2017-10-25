package boutlendj.salim.designpattern.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import boutlendj.salim.designpattern.MainActivity;
import boutlendj.salim.designpattern.R;
import boutlendj.salim.designpattern.model.User;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private User mGoogleUser;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private ProgressBar progressBar;
    private Button btnRegister, btnLogin;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users");
        if (auth.getCurrentUser() != null) {
            getUserFromDataBase(auth.getCurrentUser().getUid(), null , auth.getCurrentUser());
        }
        // set the view now
        setContentView(R.layout.activity_login);

        SignInButton signInButton = findViewById(R.id.sign_in_google);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.login_progress);
        btnLogin = findViewById(R.id.button_sign_in);
        btnRegister = findViewById(R.id.button_register);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.prompt_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_password), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    getUserFromDataBase(auth.getCurrentUser().getUid(), null, auth.getCurrentUser());
                                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    //intent.putExtra("User", mGoogleUser);
                                    //startActivity(intent);
                                    //finish();
                                }
                            }
                        });
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success" + auth.getCurrentUser().getEmail());
                            getUserFromDataBase(auth.getCurrentUser().getUid(), acct , auth.getCurrentUser());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("User", mGoogleUser);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void getUserFromDataBase(String ID, final GoogleSignInAccount account, final FirebaseUser mUser) {

        mRef.orderByChild("id").equalTo(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "HEEEEEEEEEEEEEEEEEEEEEEY");

                if (!dataSnapshot.exists() && account != null) {

                    mGoogleUser = new User.Builder(mUser.getUid(), account.getEmail())
                            .firstName(account.getFamilyName())
                            .lastName(account.getGivenName())
                            .build();

                    mRef.child(mGoogleUser.getmID()).setValue(mGoogleUser);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("User", mGoogleUser));

                } else if (!dataSnapshot.exists() && account == null){

                    mGoogleUser = new User.Builder(mUser.getUid(), mUser.getEmail())
                            .lastName(mUser.getDisplayName())
                            .build();

                    mRef.child(mGoogleUser.getmID()).setValue(mGoogleUser);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("User", mGoogleUser));
                } else {}

                Log.e(TAG, "END OF FUNC");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        mRef.orderByChild("id").equalTo(ID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "GREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAT");
                mGoogleUser = dataSnapshot.getValue(User.class);
                Log.e(TAG, ""+ mGoogleUser.getEmail());
                startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("User", mGoogleUser));
                finish();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "END OF Chaged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e(TAG, "END OF Removed");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "END OF Moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getDetails());
            }
        });



    }



}
