package com.example.rouge.anem.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.Entity.Message;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0b on 04/01/17.
 */

public class MessageAdapter extends BaseAdapter {
    private List<Message> listMessage;
    private List<Message> initialListMessage;
    private LayoutInflater layoutInflater;

    public MessageAdapter(Context context, List<Message> vListMessage) {
        layoutInflater = LayoutInflater.from(context);
        setInitialListMessage(vListMessage);
    }

    @Override
    public int getCount() {
        return getListMessage().size();
    }

    @Override
    public Object getItem(int position) {
        return getListMessage().get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.liste_message, null);
            holder.textViewNom = (TextView) convertView.findViewById(R.id.vueNom);
            holder.textViewAdresse = (TextView) convertView.findViewById(R.id.vueAdresse);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.vueDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewNom.setText(getListMessage().get(position).getUtilisateur().getPrenom());
        holder.textViewAdresse.setText(getListMessage().get(position).getMessage());
        holder.textViewDate.setText(Util.getStringFromDate(getListMessage().get(position).getDate()));
        return convertView;
    }

    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = new ArrayList<>();
        this.listMessage.addAll(listMessage);
        notifyDataSetChanged();
    }

    public List<Message> getInitialListMessage() {
        return initialListMessage;
    }

    public void setInitialListMessage(List<Message> initialListMessage) {
        this.initialListMessage = initialListMessage;
        setListMessage(initialListMessage);
    }

    private class ViewHolder {
        TextView textViewNom;
        TextView textViewAdresse;
        TextView textViewDate;
    }
}
