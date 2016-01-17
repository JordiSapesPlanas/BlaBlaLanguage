package com.example.jordi.blablalanguage.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import com.example.jordi.blablalanguage.Adapters.meetingAdapter;
import com.example.jordi.blablalanguage.Models.Meeting;
import com.example.jordi.blablalanguage.Models.Utils;
import com.example.jordi.blablalanguage.R;

import java.util.Date;
import java.util.List;

;

public class    MeetingsListActivity extends AppCompatActivity {

    private List<Meeting> met;
//    private String[] nameMeetings;
//    private String[] nameEstablishments;
//    private String[] imageName;
    private Meeting meetingSelected;
    private ListView listView;
    String idEventExtra;
    private Utils utils = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        idEventExtra = getIntent().getExtras().getString("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My meeting list");
        setSupportActionBar(toolbar);

        Meeting m = new Meeting(this);
        //met = listOfMeetings.getList();
        //datosDePrueba();
        met = m.getAll(null);
        listView = (ListView) findViewById(R.id.listView_my_meetings);
        meetingAdapter myAdapter = new meetingAdapter(MeetingsListActivity.this, met, R.layout.customer_meeting_list);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                meetingSelected = met.get(position);
                Intent intent = new Intent(MeetingsListActivity.this, MeatingDetailActivity.class);
                intent.putExtra("estabName", meetingSelected.getEstablishment());
                intent.putExtra("language", meetingSelected.getLanguage());
                intent.putExtra("date", meetingSelected.getDateMeeting().toString());
                startActivity(intent);
                // final int idEvent = searchIdEvent(met, m.getName(), m.getEstablishment(), m.getDateMeeting());
                //int idEvent = meetingSelected.getIdM();

//                AlertDialog.Builder builder = new AlertDialog.Builder(MeetingsListActivity.this);
//                builder.setTitle("Delete");
//                builder.setMessage("Do you want to delete this meeting?");
//
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing but close the dialog
//                        m.DeleteById(MeetingsListActivity.this, idEvent);
//                        ListView listView = (ListView) findViewById(R.id.listView_my_meetings);
//                        met=m.getAll(MeetingsListActivity.this);
//                        meetingAdapter myAdapter = new meetingAdapter(MeetingsListActivity.this, met, R.layout.customer_meeting_list);
//                        listView.setAdapter(myAdapter);
//                        myAdapter.notifyDataSetChanged();
//                        dialog.dismiss();
//
//
//                    }
//
//                });
//
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing
//                        dialog.dismiss();
//                        Intent intent = new Intent(MeetingsListActivity.this,MeatingDetailActivity.class);
//                        intent.putExtra("estabName",m.getEstablishment());
//                        intent.putExtra("language",m.getLanguage());
//                        intent.putExtra("date",m.getDateMeeting().toString());
//
//
//
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                meetingSelected = met.get(position);
                registerForContextMenu(listView);
                openContextMenu(listView);
                return true;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(meetingSelected.getName());
        Log.e("-----*-----", meetingSelected.getUserEmail());
        String userMail = utils.getKey(this, "USER_LOGGED");
        String meetingUserMail = meetingSelected.getUserEmail();
        Log.e("-----*1-----", utils.getKey(this, "USER_LOGGED"));
        if(userMail.equals(meetingUserMail)){
            menu.add(0, 1, 0, "View details");
            menu.add(0, 2, 0, "EDIT");
            menu.add(0, 3, 0, "DELETE");
        }else{
            menu.add(0, 1, 0, "View details");
        }

    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case 1: //details
                Intent intent = new Intent(MeetingsListActivity.this,MeatingDetailActivity.class);
                    intent.putExtra("estabName",meetingSelected.getEstablishment());
                    intent.putExtra("language",meetingSelected.getLanguage());
                    intent.putExtra("date",meetingSelected.getDateMeeting().toString());
                startActivity(intent);
                break;
            case 2:
                Bundle b  = new Bundle();
                b.putSerializable("meeting", meetingSelected);
                startActivity(new Intent(MeetingsListActivity.this, CreateMeeting.class).putExtras(b));
                break;
            case 3:
                meetingSelected.DeleteById(MeetingsListActivity.this, meetingSelected.getIdM());
                met.remove(meetingSelected);
                meetingAdapter myAdapter = new meetingAdapter(MeetingsListActivity.this, met, R.layout.customer_meeting_list);
                listView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
                break;



        }
        return true;
    }




    private int searchIdBBDD (List<Meeting> list, String name, String estab, Date date){

        boolean found = false;
        int id = 0;
        int i = 0;
        while (i<list.size() && !found){
            if (list.get(i).getName().equals(name.trim()) && list.get(i).getEstablishment().equals(estab.trim()))
                    {
                found=true;
                id = list.get(i).getExtraId();

            }
            Log.d("SEARCH",list.get(i).getName()+" " +name+" " +i +" "+list.get(i).getExtraId()+" "+list.get(i).getIdM());
            i++;
        }
        Log.d("SEARCH",list.get(i).getName()+" " +name+" " +i );
        return id;
    }

    private int searchIdEvent (List<Meeting> list, String name, String estab, Date date){

        boolean found = false;
        int id = 0;
        int i = 0;
        while (i<list.size() && !found){
            if (list.get(i).getName().equals(name) && list.get(i).getEstablishment().equals(estab)
                    ){
                found=true;
                id = list.get(i).getId();
            }
            i++;
        }
        return id;
    }

//    private void datosDePrueba(){
//
//        for(int i=0;i<nameMeetings.length;i++){
//            Meeting m= new Meeting();
//            m.setName(nameMeetings[i]);
//            m.setEstablishment(nameEstablishments[i]);
//            m.setImageUrl(imageName[i]);
//            met.add(m);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
