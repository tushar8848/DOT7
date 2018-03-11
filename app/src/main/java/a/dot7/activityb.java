/*package com.example.suman.dot7pro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class activityB extends AppCompatActivity {

    EditText name,email,password,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        name=findViewById(R.id.enter_name);
        email=findViewById(R.id.enter_email);
        contact=findViewById(R.id.enter_contact);
        password=findViewById(R.id.enter_password);

    }

    public void store(View view)
    {
        String c=contact.getText().toString()+" ";
        String p=password.getText().toString();
        File file=getCacheDir();
        File myfile=new File(file.getAbsolutePath(),"logdetails.txt");
        FileOutputStream outputStream;
        try {
            outputStream=new FileOutputStream(myfile);
            outputStream.write(c.getBytes());
            message(file.getAbsolutePath().toString()+"/sample.txt");

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

    private void message(String s) {
        Toast.makeText(this,"Data is written successfully"+s,Toast.LENGTH_SHORT).show();
    }
}*/
