package es.kleiren.eolo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Battle extends AppCompatActivity {

    ImageView imagePlayer;
    Animation moveInLeft, moveInRight, attack;
    RelativeLayout layoutPlayer, layoutEnemy;
    View battleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

       // overridePendingTransition(R.anim.move_in_left, R.anim.move_in_left);

        imagePlayer = (ImageView) findViewById(R.id.imagePlayer);

        layoutEnemy = (RelativeLayout) findViewById(R.id.layout_enemy);
        layoutPlayer = (RelativeLayout) findViewById(R.id.layout_player);


        moveInLeft = AnimationUtils.loadAnimation(this, R.anim.move_in_left);
        moveInRight = AnimationUtils.loadAnimation(this, R.anim.move_in_right);
        attack = AnimationUtils.loadAnimation(this, R.anim.attack);


        layoutPlayer.setAnimation(moveInLeft);
        layoutEnemy.setAnimation(moveInRight);

    }

    public void attack(View v){
        Toast.makeText(this, "attack", Toast.LENGTH_SHORT).show();
        imagePlayer.startAnimation(attack);
    }
}
