package com.merive.securepass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.merive.securepass.elements.TypingTextView;

public class CheckKeyActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TypingTextView title, key_hint;
    EditText key;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_key);

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        title = findViewById(R.id.title_check);
        typingAnimation(title, getResources().getString(R.string.welcome_to_securepass));
        key_hint = findViewById(R.id.key_hint);
        typingAnimation(key_hint, getResources().getString(R.string.enter_the_key_in_the_field));

        check = findViewById(R.id.check_button);
        key = findViewById(R.id.key);

        checkKeyNullable();
    }

    public void typingAnimation(TypingTextView view, String text) {
        view.setText("");
        view.setCharacterDelay(150);
        view.animateText(text);
    }

    @SuppressLint("SetTextI18n")
    public void checkKeyNullable() {
        if (sharedPreferences.getInt("key", 0) == 0) {
            typingAnimation(key_hint, "Create a key\nTip: Use more than 4 numbers.");
            check.setText("Create");
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkKey(View view) {
        if (!key.getText().toString().equals("")) {
            if (sharedPreferences.getInt("key", hashKey(0)) == hashKey(0)) {
                sharedPreferences.edit().putInt("key", hashKey(Integer.parseInt(key.getText().toString()))).apply();

                typingAnimation(key_hint, "Key set. Welcome!");

                new Handler().postDelayed(() -> {
                    startActivity(new Intent(CheckKeyActivity.this, MainActivity.class));
                    finish();
                }, 3500);
            } else {
                if (sharedPreferences.getInt("key", hashKey(0)) == hashKey(Integer.parseInt(key.getText().toString()))) {
                    typingAnimation(key_hint, "All right. Welcome!");

                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(CheckKeyActivity.this, MainActivity.class));
                        finish();
                    }, 3500);
                } else {
                    typingAnimation(key_hint, "Invalid key. Try again.");
                }
            }
        } else {
            typingAnimation(key_hint, "Enter key.");
        }
    }

    public int hashKey(int key) {
        return String.valueOf(key).hashCode();
    }
}
