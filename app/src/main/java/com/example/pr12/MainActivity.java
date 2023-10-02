package com.example.pr12;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private void showGameOverDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fi de la partida");
        builder.setMessage("Has endevinat el número. Felicitats!");

        builder.setPositiveButton("Tornar a jugar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        textView.setText("");

        Button button = findViewById(R.id.button);
        button.setEnabled(false);

        EditText editText = findViewById(R.id.editText);
        editText.setMovementMethod(new ScrollingMovementMethod());

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    if (!editText.getText().toString().isEmpty()) {
                        button.setEnabled(true);
                    }
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            int randomNumber = (int) ((Math.random() * (100 - 1 + 1)) + 1);
            Toast toast;

            public void onClick(View v) {

                if (randomNumber > Integer.parseInt(String.valueOf(editText.getText()))) {
                    toast = Toast.makeText(getApplicationContext(), "El nombre que busques és major que " + editText.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                    textView.append(editText.getText() + "  ");
                }

                else if (randomNumber < Integer.parseInt(String.valueOf(editText.getText()))) {
                    toast = Toast.makeText(getApplicationContext(), "El nombre que busques és menor que " + editText.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                    textView.append(editText.getText() + "  ");
                }

                else {
                    showGameOverDialog();
                    textView.setText("");
                    randomNumber = (int) ((Math.random() * (100 - 1 + 1)) + 1);
                }
                editText.setText("");
                button.setEnabled(false);
            }
        });
    }
}
