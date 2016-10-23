package es.kleiren.eolo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends AppCompatActivity {

    private ImageView imagePlayer;
    private Animation moveInLeft, moveInRight, attack, shrink;
    private RelativeLayout layoutPlayer, layoutEnemy;
    private TextView txtInfo, txtEnemy, txtPlayer;
    private Context context;
    private Button btnAttack, btnFlee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        //Connection with layout and resources
        txtInfo = (TextView) findViewById(R.id.infotext);
        txtEnemy = (TextView) findViewById(R.id.enemyname);
        txtPlayer = (TextView) findViewById(R.id.playername);
        btnAttack = (Button) findViewById(R.id.btn_attack);
        btnFlee = (Button) findViewById(R.id.btn_flee);
        imagePlayer = (ImageView) findViewById(R.id.imagePlayer);
        layoutEnemy = (RelativeLayout) findViewById(R.id.layout_enemy);
        layoutPlayer = (RelativeLayout) findViewById(R.id.layout_player);
        moveInLeft = AnimationUtils.loadAnimation(this, R.anim.move_in_left);
        moveInRight = AnimationUtils.loadAnimation(this, R.anim.move_in_right);
        attack = AnimationUtils.loadAnimation(this, R.anim.attack);
        shrink = AnimationUtils.loadAnimation(this, R.anim.shrink);

        //Setting the animations
        layoutPlayer.setAnimation(moveInLeft);
        layoutEnemy.setAnimation(moveInRight);

        //Initial textSetting
        txtInfo.setText("A wild Asrobot appeared!");
        txtPlayer.setText("MYOD");
        txtEnemy.setText("ASROBOT");

        btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtInfo.setText("Attack MYOD!");
                imagePlayer.startAnimation(attack);
            }
        });

        //What happens when the animations end or start
        attack.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtInfo.setText("It was very effective!");
                //Waiting one second for the user to readl
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();                    }
                }, 1000);}

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

}
