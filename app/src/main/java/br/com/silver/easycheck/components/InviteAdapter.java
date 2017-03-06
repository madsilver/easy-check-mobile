package br.com.silver.easycheck.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.silver.easycheck.R;
import br.com.silver.easycheck.model.Invite;

/**
 * Created by silver on 28/04/16.
 */
public class InviteAdapter extends BaseAdapter {

    Context ctx;
    List<Invite> invites;

    public InviteAdapter(Context ctx, List<Invite> invites){
        this.ctx = ctx;
        this.invites = invites;
    }

    @Override
    public int getCount() {
        return invites.size();
    }

    @Override
    public Object getItem(int position) {
        return invites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Invite invite = invites.get(position);
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_invite, null);

        ImageView imgStatus = (ImageView) view.findViewById(R.id.img_invite_status);
        if(invite.status == 1){
            imgStatus.setBackgroundResource(R.drawable.ic_allow_48dp);
            view.setBackgroundColor(this.ctx.getResources().getColor(R.color.colorScannerAllow));
        } else {
            imgStatus.setBackgroundResource(R.drawable.ic_denied_48dp);
            view.setBackgroundColor(this.ctx.getResources().getColor(R.color.colorScannerDenied));
        }

        TextView txtName = (TextView) view.findViewById(R.id.invite_name);
        txtName.setText(invite.name);
        TextView txtDate = (TextView) view.findViewById(R.id.invite_date);
        txtDate.setText(invite.date);
        return view;
    }
}
