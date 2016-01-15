package buttertext.com.buttertext;

/**
 * Created by Gabriel on 12/28/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buttertext.com.buttertext.helper.DatabaseHelper;


public class InboxFragment extends Fragment {

    //Android display variables and adapters
    private SimpleAdapter adapter = null;
    private ListView listview = null;
    private String[] from = { "sender", "messagePreview", "timeStamp"  };
    private int[] to = { R.id.sender, R.id.messagePreview, R.id.timeStamp };
    private SwipeRefreshLayout swipeContainer;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        listview = (ListView) rootView.findViewById(R.id.listView);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);

        TextMessage m1 = new TextMessage();
        TextMessage m2 = new TextMessage();
        TextMessage m3 = new TextMessage();

        m1.setSender("Jamison");
        m2.setSender("Mom");
        m3.setSender("Aidana");

        m1.setMessage("Yo what's good");
        m2.setMessage("How was your day?");
        m3.setMessage("Hello there");

        m1.setTimeStamp("9:52PM");
        m2.setTimeStamp("12:55PM");
        m3.setTimeStamp("7:15PM");

        // Do something with the result.
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        ArrayList<TextMessage> _list = new ArrayList<TextMessage>();
        _list.add(m1);
        _list.add(m2);
        _list.add(m3);

        for (TextMessage message : _list) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("sender", message.getSender());
            item.put("messagePreview", message.getMessage());
            item.put("timeStamp", message.getTimeStamp());
            list.add(item);
        }

        adapter = new SimpleAdapter( getActivity(), list, R.layout.fragment_inbox_item, from, to );
        listview.setAdapter(adapter);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                swipeContainer.setRefreshing(false);
            }
        });

        //Set up ClickListener for a click on a position in the list.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent( getActivity(), ConversationActivity.class );
                //Long listingId = idList.get(position);
                //intent.putExtra("position_id", listingId);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
