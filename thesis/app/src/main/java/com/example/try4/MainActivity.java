package com.example.try4;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    TextView pressure_upper,pressure_lower,Heartbeats,result;
    Button call,generate;

    String heart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calling help:*******************************************************************************************************************************************************

        call=findViewById(R.id.call);
        call.setOnClickListener(v -> {
            String number= "#112";
            Intent intentEmergency= new Intent(Intent.ACTION_CALL);
            intentEmergency.setData(Uri.parse("tel:"+number));

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intentEmergency);


        });



//generating heartbeat, lower blood pressure upper blood pressure***************************************************************************************************
        pressure_upper= findViewById(R.id.pressure_upper);
        pressure_lower= findViewById(R.id.pressure_lower);
        Heartbeats= findViewById(R.id.Heartbeats);
        result= findViewById(R.id.result);

        int minUpB= 100;
        int maxUpB=180;
        int minLoB=60;
        int MaxLoB=120;
        int MinHB=50;
        int MaxHB=230;


        int generatedUpB = (int) ((Math.random() * (maxUpB - minUpB)) + minUpB);
        int generatedloB = (int) ((Math.random() * (MaxLoB - minLoB)) + minLoB);
        int generatedHB = (int) ((Math.random() * (MaxHB - MinHB)) + MinHB);



        generate=findViewById(R.id.generate);
        generate.setOnClickListener(v -> {
            pressure_upper.setText(generatedUpB);
            pressure_lower.setText(generatedloB);
            Heartbeats.setText(generatedHB);
            Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            //generate numbers from
            //Normal: Less than 120/80 mm Hg.
           // Elevated: Systolic between 120-129 and diastolic less than 80.
           // Stage 1 hypertension: Systolic between 130-139 or diastolic between 80-89.
          //  Stage 2 hypertension: Systolic at least 140 or diastolic at least 90 mm Hg.
          //  Hypertensive crisis: Systolic over 180 and/or diastolic over 120, with patients needing prompt changes in medication if there are no other indications of problems, or immediate hospitalization if there are signs of organ damage.
            //generate random number between: 100-200
            if(generatedHB<60) heart = ", Heartrate: bradychardia";
            else if(generatedHB>60&& generatedHB<100) heart = ", Heartrate: normal";
            else if(generatedHB>100&&generatedHB<186){
                heart=", Heart rate: tachycardia or doing sport";
            }else{heart= "heart palpitation";}


            if(generatedUpB<120 ) {//normal
                if (generatedloB < 80){
                    result.setText(R.string.normal + Integer.parseInt(heart));

                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vibrate.vibrate(500);
                }
                    Toast.makeText(MainActivity.this, "You are well...", Toast.LENGTH_SHORT).show();

                }


            }else if(generatedUpB> 120 && generatedUpB<129 ){
                    if( generatedloB>80&& generatedloB<89){
                        String message = getString(R.string.elevated + Integer.parseInt(heart));
                        result.setText(message);
                        // Vibrate for 1000 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrate.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibrate.vibrate(1000);
                        }
                        Toast.makeText(MainActivity.this, "Elevated...", Toast.LENGTH_SHORT).show();

                    }
                //elevated}
            }else if(generatedUpB>130 && generatedUpB <139) {
                     if( generatedloB> 90 && generatedloB <99) {
                         String message = getString(R.string.stage_1_hypertension + Integer.parseInt(heart));
                         result.setText(message);
                         // Vibrate for 1500 milliseconds
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                             vibrate.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
                         } else {
                             //deprecated in API 26
                             vibrate.vibrate(1500);
                         }
                         Toast.makeText(MainActivity.this, "stage 1 hypertension...", Toast.LENGTH_SHORT).show();

                     }
            }else if(generatedUpB>140 &&generatedUpB<180 ){
                //stage 2 hypertension
                     if( generatedloB>100 && generatedloB<120 ) {
                         String message = getString( R.string.stage_2_hypertension+Integer.parseInt(heart));
                         result.setText(message);
                         // Vibrate for 500 milliseconds
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                             vibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                         } else {
                             //deprecated in API 26
                             vibrate.vibrate(500);
                         }
                         Toast.makeText(MainActivity.this, "Redirecting to video...", Toast.LENGTH_SHORT).show();
                         Uri webpage= Uri.parse("https://www.youtube.com/watch?v=Dkk9gvTmCXY");
                         Intent webIntent= new Intent(Intent.ACTION_VIEW,webpage);
                         try{
                         startActivity(webIntent);}catch (ActivityNotFoundException e){
                             Toast.makeText(MainActivity.this, "video didn't work.", Toast.LENGTH_SHORT).show();


                         }
                     }

            }else if(generatedUpB>180 ){
                    //emergency
                    if( generatedloB>120 ){
                        //call emergency automatically
                        result.setText(R.string.call_emergency);
                        call.setActivated(true);
                    }
            }


        });






 }
}

