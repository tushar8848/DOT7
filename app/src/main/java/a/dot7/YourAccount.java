package a.dot7;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YourAccount extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String Name,Email,Contact;
    TextView name,email,contact;
    AlertDialog CustomDialog;
    LinearLayout logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);
        intent = getIntent();
        Name = intent.getStringExtra("Name");
        Email = intent.getStringExtra("Email");
        Contact = intent.getStringExtra("Contact");
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.mobNo);
        name.setText(Name);
        email.setText(Email);
        contact.setText(Contact);
        logout = findViewById(R.id.Btn_logOut);
        logout.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.your_account_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    finish();
                    return true;
            default:
                return true;
        }
    }
    private void Logout()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UserName", null);
                editor.putString("Password",null);
                editor.commit();
                startActivity(new Intent(YourAccount.this, ScreenSlideActivity.class));
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        CustomDialog=builder.create();
        CustomDialog.show();
    }

    @Override
    public void onClick(View v) {
        Logout();
    }
}
