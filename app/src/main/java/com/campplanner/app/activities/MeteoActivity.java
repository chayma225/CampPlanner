package com.campplanner.app.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.campplanner.app.R;

public class MeteoActivity extends AppCompatActivity {

    private TextView weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        weatherInfo = findViewById(R.id.weather_info);

        // Affichage des informations météo
        weatherInfo.setText("Météo : Ensoleillé, 25°C\n\n" +
                "Prévisions à 5 jours :\n" +
                "Lundi : Ensoleillé, 26°C\n" +
                "Mardi : Nuageux, 23°C\n" +
                "Mercredi : Pluie, 20°C\n" +
                "Jeudi : Ensoleillé, 24°C\n" +
                "Vendredi : Ensoleillé, 27°C");
    }
}
