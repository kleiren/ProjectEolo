package es.kleiren.eolo;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonsterList extends Fragment {
    ListView list;
    String[] monster = {
            "MYOD",
            "ASROBOT"
    } ;
    Integer[] imageId = {
            R.drawable.ic_myod_front,
            R.drawable.ic_asrobot_pix,
    };


    public MonsterList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View monsterListView =  inflater.inflate(R.layout.fragment_monster_list, container, false);

        CustomList adapter = new CustomList(getActivity(), monster, imageId);
        list=(ListView) monsterListView.findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), monster[+ position], Toast.LENGTH_SHORT).show();

            }
        });


        return monsterListView;
    }

}



class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.list_item, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtListItem);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgListItem);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
