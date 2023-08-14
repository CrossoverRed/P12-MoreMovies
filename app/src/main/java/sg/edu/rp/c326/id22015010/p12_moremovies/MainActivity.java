package sg.edu.rp.c326.id22015010.p12_moremovies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText eMovie, eGenre, eYear;
    Spinner spinRating;
    Button ibtn, lbtn;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eMovie = findViewById(R.id.MovieEdit);
        eGenre = findViewById(R.id.GenreEdit);
        eYear = findViewById(R.id.YearEdit);
        spinRating = findViewById(R.id.RatingSpinner);
        ibtn = findViewById(R.id.Insertbtn);
        lbtn = findViewById(R.id.ShowListbtn);

        dbHelper = new DBHelper(getApplicationContext());

        ibtn.setOnClickListener(v -> saveMovie());
        lbtn.setOnClickListener(v -> showMovies());
    }

    public void saveMovie() {
        String title = eMovie.getText().toString().trim();
        String genre = eGenre.getText().toString().trim();
        String yearStr = eYear.getText().toString().trim();
        String ratings = spinRating.getSelectedItem().toString().trim();

        // Check if any required fields are empty
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(genre) || TextUtils.isEmpty(yearStr) || TextUtils.isEmpty(ratings)) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if year is a valid integer
        int year = 0;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid year", Toast.LENGTH_LONG).show();
            return;
        }

        // All fields are filled, proceed with saving the movie
        dbHelper.insertMovie(title, genre, year, ratings);

        Toast.makeText(this, "Movie saved successfully", Toast.LENGTH_LONG).show();
        Log.d("Insert", "New movie inserted"); // Add this line for logging
        clearFields();
    }

    private void clearFields() {
        eMovie.getText().clear();
        eGenre.getText().clear();
        eYear.getText().clear();
        spinRating.setSelection(0);
    }

    public void showMovies() {
        Intent intent = new Intent(MainActivity.this, moviesActivity.class);
        startActivity(intent);
    }
}