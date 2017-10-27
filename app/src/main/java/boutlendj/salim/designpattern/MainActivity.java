package boutlendj.salim.designpattern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import boutlendj.salim.designpattern.authentication.LoginActivity;
import boutlendj.salim.designpattern.model.User;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private FirebaseAuth mAuth;
    EditText firstNameTxt, lastNameTxt, ageTxt, phoneTxt, addressTxt, email, id;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.log_out);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        User mUser = getIntent().getExtras().getParcelable("User");

        updateUI(mUser);

    }

    public void logOut (View view) {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }

    public void updateUI(User user) {

        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        firstNameTxt = findViewById(R.id.first_name);
        lastNameTxt = findViewById(R.id.last_name);
        ageTxt = findViewById(R.id.age);
        addressTxt = findViewById(R.id.address);
        phoneTxt = findViewById(R.id.phone_number);
        spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        email.setText(user.getmEmail());
        id.setText(user.getmID());
        firstNameTxt.setText(user.getmFirstName());
        lastNameTxt.setText(user.getmLastName());
        ageTxt.setText(user.getmAge());
        addressTxt.setText(user.getmAddress());
        phoneTxt.setText(user.getmPhone());
    }
}
