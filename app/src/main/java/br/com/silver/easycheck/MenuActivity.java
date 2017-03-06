package br.com.silver.easycheck;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import br.com.silver.easycheck.repository.InviteRepository;
import br.com.silver.easycheck.util.SilverHttp;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    InviteRepository mRepository;

    @Bind(R.id.count_invite)
    TextView mTextCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        mRepository = new InviteRepository(this);
    }

    protected void onStart(){
        super.onStart();

        int countInvites = mRepository.total();
        mTextCount.setText(String.valueOf(countInvites));
    }

    @Override
    public void onBackPressed(){
        exit();
    }

    public void initScan(View v){
        if(SilverHttp.hasConnection(this)){
            rotateView(v);
            startActivity(new Intent(this, ScannerActivity.class));
        } else {
            Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_LONG);
        }
    }

    public void history(View v){
        rotateView(v);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, HistoryActivity.class);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    public void preferences(View v){
        rotateView(v);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, ConfigActivity.class);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @OnClick(R.id.img_exit)
    public void exit(){
        SilverDialog dialog = new SilverDialog();
        dialog.show(getSupportFragmentManager(), "exit");
    }

    public void rotateView(final View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        v.startAnimation(animation);
    }

    /**
     *
     */
    public static class SilverDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            };
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.app_name)
                    .setMessage("Deseja sair do aplicativo?")
                    .setPositiveButton("Sim", listener)
                    .setNegativeButton("Não", null)
                    .create();

            return dialog;
        }
    }


}
