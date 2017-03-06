package br.com.silver.easycheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.silver.easycheck.databinding.ActivityScannerBinding;
import br.com.silver.easycheck.model.Invite;
import br.com.silver.easycheck.model.InviteRequest;
import br.com.silver.easycheck.repository.InviteRepository;
import br.com.silver.easycheck.util.SilverHttp;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ScannerActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;
    ActivityScannerBinding binding;

    @Bind(R.id.layout_allow)
    LinearLayout mLayoutAllow;
    @Bind(R.id.layout_denied)
    LinearLayout mLayoutDenied;
    @Bind(R.id.layout_fail)
    LinearLayout mLayoutFail;

    InviteRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scanner);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean scanExt = prefs.getBoolean(getString(R.string.pref_scanner), false);

        if(scanExt){
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, REQUEST_CODE);
        }
        else {
            Intent intent = new Intent(ScannerActivity.this, com.google.zxing.client.android.CaptureActivity.class);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, REQUEST_CODE);
        }

        ButterKnife.bind(this);

        mRepository = new InviteRepository(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            String code = data.getStringExtra("SCAN_RESULT");
            SendTask st = new SendTask(code);
            st.execute();
        } else {
            Toast.makeText(this, "Leitura cancelada ou interrompida", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void close(View view){
        finish();
    }

    public class SendTask extends AsyncTask<String, Void, Invite> {

        private final String code;

        SendTask(String c){
            this.code = c;
        }

        @Override
        protected Invite doInBackground(String... params) {
            final String resp = SilverHttp.authInvite(ScannerActivity.this, this.code);

            Gson gson = new Gson();
            InviteRequest inviteRequest =  gson.fromJson(resp, InviteRequest.class);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Invite invite = new Invite(inviteRequest);
            invite.date = sdf.format(new Date());
            invite.code = this.code;

            return invite;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Invite invite) {
            if(invite != null){
                if(invite.status == 1)
                    mLayoutAllow.setVisibility(View.VISIBLE);
                else
                    mLayoutDenied.setVisibility(View.VISIBLE);

                mRepository.insert(invite);

            } else {
                mLayoutFail.setVisibility(View.VISIBLE);
                invite = new Invite();
                invite.code = this.code;
            }

            //envia os dados do convite para o layout
            binding.setInvite(invite);
        }
    }
}
