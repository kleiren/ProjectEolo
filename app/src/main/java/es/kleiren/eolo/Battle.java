package es.kleiren.eolo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Battle extends AppCompatActivity {

    ImageView imagePlayer;
    Animation moveInLeft, moveInRight, attack, shrink;
    RelativeLayout layoutPlayer, layoutEnemy;
    TextView txtInfo, txtEnemy, txtPlayer;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        context = this;

        txtInfo = (TextView) findViewById(R.id.infotext);
        txtEnemy = (TextView) findViewById(R.id.enemyname);
        txtPlayer = (TextView) findViewById(R.id.playername);

        txtInfo.setText("A wild Asrobot appeared!");
        txtPlayer.setText("MYOD");
        txtEnemy.setText("ASROBOT");

        imagePlayer = (ImageView) findViewById(R.id.imagePlayer);
        layoutEnemy = (RelativeLayout) findViewById(R.id.layout_enemy);
        layoutPlayer = (RelativeLayout) findViewById(R.id.layout_player);

        moveInLeft = AnimationUtils.loadAnimation(this, R.anim.move_in_left);
        moveInRight = AnimationUtils.loadAnimation(this, R.anim.move_in_right);
        attack = AnimationUtils.loadAnimation(this, R.anim.attack);
        shrink = AnimationUtils.loadAnimation(this, R.anim.shrink);

        layoutPlayer.setAnimation(moveInLeft);
        layoutEnemy.setAnimation(moveInRight);

        attack.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtInfo.setText("It was very effective!");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();                    }
                }, 1000);            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public void attack(View v){
        txtInfo.setText("Attack MYOD!");
        imagePlayer.startAnimation(attack);
    }




}
