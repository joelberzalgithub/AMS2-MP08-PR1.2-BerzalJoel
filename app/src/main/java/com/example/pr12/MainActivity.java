package com.example.pr12;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private Intent intent;
    private void showGameOverDialog(final int numIntents) {

        intent = new Intent(MainActivity.this, HallOfFame.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fi de la partida");
        builder.setMessage("Has endevinat el número. Felicitats!");
        builder.setPositiveButton("Tornar a jugar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Mostrar taula de rècords", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                showAddRecordDialog(numIntents);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void showAddRecordDialog(final int numIntents) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hall Of Fame");
        builder.setMessage("Abans d'anar a la taula, t'agradaria afegir el teu propi rècord?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                showNewRecordDialog(numIntents);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void showNewRecordDialog(final int numIntents) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Afegir nou rècord");
        builder.setMessage("Per afegir el teu nou rècord has d'introduir el teu nom.");

        EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                intent.putExtra("nom", String.valueOf(input.getText()));
                intent.putExtra("intents", numIntents);
                startActivity(intent);
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
                    button.setEnabled(!editText.getText().toString().isEmpty() && Integer.parseInt(editText.getText().toString()) < 101);
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            int randomNumber = (int) ((Math.random() * (100 - 1 + 1)) + 1);
            int numIntents = 0;
            public void onClick(View v) {

                if (randomNumber > Integer.parseInt(String.valueOf(editText.getText()))) {
                    Toast.makeText(getApplicationContext(), "El nombre que busques és major que " + editText.getText(), Toast.LENGTH_SHORT).show();
                    textView.append(editText.getText() + "\n");
                }

                else if (randomNumber < Integer.parseInt(String.valueOf(editText.getText()))) {
                    Toast.makeText(getApplicationContext(), "El nombre que busques és menor que " + editText.getText(), Toast.LENGTH_SHORT).show();
                    textView.append(editText.getText() + "\n");
                }

                else {
                    showGameOverDialog(numIntents);
                    textView.setText("");
                    randomNumber = (int) ((Math.random() * (100 - 1 + 1)) + 1);
                }
                editText.setText("");
                button.setEnabled(false);
                numIntents++;
            }
        });
    }
}
