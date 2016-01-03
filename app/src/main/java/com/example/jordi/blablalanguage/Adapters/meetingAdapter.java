package com.example.jordi.blablalanguage.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jordi.blablalanguage.Models.Meeting;
import com.example.jordi.blablalanguage.R;

import java.util.List;

/**
 * Created by richengjin on 16/11/15.
 */
public class meetingAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<Meeting> meetings;


    @SuppressWarnings("static-access")
    public meetingAdapter(Context context, List<Meeting> meetings, int resource) {
        this.meetings = meetings;
        this.resource = resource;
        this.context = context;

    }

    static class ViewHolder {
        TextView nameMeeting;
        TextView nameEstablishment;
        TextView time;
        TextView date;
        ImageView image;
    }

    @Override
    public int getCount() {
        return meetings.size();
    }

    @Override
    public Object getItem(int position) {
        return this.meetings.get(position);
    }
    public Meeting getItemPressed(int position){
        return meetings.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, resource, null);
            viewHolder.nameMeeting = (TextView) convertView.findViewById(R.id.nameMeeting);
            viewHolder.nameEstablishment = (TextView) convertView.findViewById(R.id.nameEstablishment);
            viewHolder.time=(TextView)convertView.findViewById(R.id.time);
            viewHolder.date =(TextView)convertView.findViewById(R.id.date);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.languageImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Meeting meeting = meetings.get(position);

        if (meeting != null) {
            viewHolder.nameMeeting.setText(meeting.getName());
            viewHolder.nameEstablishment.setText(meeting.getEstablishment());
            if(meeting.getImageUrl().toLowerCase().equals("english")){
                viewHolder.image.setImageResource(R.drawable.english);
            }
            else if(meeting.getImageUrl().toLowerCase().equals("chinese")){
                viewHolder.image.setImageResource(R.drawable.chinese);
            }
            else if(meeting.getImageUrl().toLowerCase().equals("spanish")){
                viewHolder.image.setImageResource(R.drawable.spain);
            }
            else if(meeting.getImageUrl().toLowerCase().equals("french")){
                viewHolder.image.setImageResource(R.drawable.france);
            }
            else {
                viewHolder.image.setImageResource(R.drawable.international);
            }

            viewHolder.date.setText(meeting.getFechaString());
            viewHolder.time.setText(meeting.getTimeString());
        }
        return convertView;
    }
}