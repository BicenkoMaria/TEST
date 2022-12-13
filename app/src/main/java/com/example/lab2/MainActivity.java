package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab2.Db.AppDb;
import com.example.lab2.Db.User;
import com.example.lab2.Db.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    String valueEmail, valuePassword;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail.setTextColor(Color.BLACK);
        editTextPassword.setTextColor(Color.BLACK);

        AppDb db = AppDb.getInstance(this);
        UserDao userDao = db.userDao();

        User dbUser = new User();
        String[] emails = getResources().getStringArray(R.array.emails);
        String[] passwords = getResources().getStringArray(R.array.passwords);

        for (int i = 0; i < emails.length; i++) {
            dbUser.email = emails[i];
            dbUser.password = passwords[i];
            userDao.insert(dbUser);
        }

        userList = userDao.getAll();
    }

    public void login(View v){
        valueEmail = editTextEmail.getText().toString();
        valuePassword = editTextPassword.getText().toString();
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

        //Consult for login
        int emailIndexFound = findEmail(valueEmail);
        if (emailIndexFound != -1) {
            if (isPasswordCorrect(emailIndexFound, valuePassword)) {
                intent.putExtra("userEmail", valueEmail);
                startActivity(intent);
            } else {
                editTextEmail.setTextColor(Color.RED);
                editTextPassword.setTextColor(Color.RED);
                //Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        } else {
            editTextEmail.setTextColor(Color.RED);
            editTextPassword.setTextColor(Color.RED);
            //Toast.makeText(this, "Incorrect email", Toast.LENGTH_SHORT).show();
        }
    }

    private int findEmail(String emailSearch) {

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).email.equals(emailSearch)) {
                return i;
            };
        }

        return -1;
    }

    private boolean isPasswordCorrect(int emailIndexFound, String inputUserPassword) {
        return userList.get(emailIndexFound).password.equals(inputUserPassword);
    }


}