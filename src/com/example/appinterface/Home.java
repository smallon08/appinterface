package com.example.appinterface;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Home extends ListActivity
{
    private String[] menuItems = { "View Record", "View Graph", "View Comments", "New Comment" };
    private String[] menuClassNames = { ViewRecord.class.getName(), Graph.class.getName(), ViewComment.class.getName(), NewComment.class.getName() };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        try
        {
            Intent intent = new Intent(this, Class.forName(menuClassNames[position]));
            startActivity(intent);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
