package br.com.silver.easycheck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.silver.easycheck.components.InviteAdapter;
import br.com.silver.easycheck.model.Invite;
import br.com.silver.easycheck.repository.InviteRepository;

/**
 * Created by silver on 28/04/16.
 */
public class HistoryActivity extends AppCompatActivity {

    List<Invite> mInvites;
    InviteAdapter mAdapter;
    ListView mListView;
    InviteRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListView = new ListView(this);
        setContentView(mListView);

        mRepository = new InviteRepository(this);
        mInvites = new ArrayList<>(mRepository.getAll());
        mAdapter = new InviteAdapter(this, mInvites);
        mListView.setAdapter(mAdapter);
    }
}
