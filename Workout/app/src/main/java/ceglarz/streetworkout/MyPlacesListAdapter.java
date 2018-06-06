package ceglarz.streetworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class MyPlacesListAdapter extends BaseAdapter implements android.widget.ListAdapter {
  private List<Place> places;
  private int layoutResId;
  private Context context;

  MyPlacesListAdapter(List<Place> places, int layoutResId, Context context) {
    this.places = places;
    this.layoutResId = layoutResId;
    this.context = context;
  }


  @Override
  public int getCount() {
    return places.size();
  }

  @Override
  public Object getItem(int pos) {
    return places.get(pos);
  }

  @Override
  public long getItemId(int pos) {
    return 0;
    //return 0 if list items do not have an Id variable.
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      assert inflater != null;
      view = inflater.inflate(layoutResId, null);
    }

    //Handle TextViews
    TextView listItemName = view.findViewById(R.id.list_item_name);
    TextView listItemIloscOsob = view.findViewById(R.id.list_item_ilosc_osob);
    TextView listItemOdleglosc = view.findViewById(R.id.list_item_odleglosc);
    TextView listItemData = view.findViewById(R.id.list_item_data);
    TextView listItemMiejsce = view.findViewById(R.id.list_item_event);



    switch (layoutResId){


      case (R.layout.list_element_place):
        listItemName.setText(places.get(position).getName());
        listItemIloscOsob.setText("Ilość osob: " + String.valueOf(places.get(position).getIloscOsob()));
        listItemOdleglosc.setText("Odległość: " + String.valueOf(places.get(position).getOdleglosc()));
        break;

      case (R.layout.list_element_training):
        listItemData.setText("Data");
        listItemMiejsce.setText("Miejsce");
        break;

//           case(R.layout.selected_places_list_element):
//                String name = (position + 1) + ". " + selectedPlaces.get(position).getName();
//                listItemName.setText(name);
//
//                String description = selectedPlaces.get(position).getDescription() + ", " +
//                        context.getString(R.string.weight) + selectedPlaces.get(position).getWeight();
//                listItemDescription.setText(description);
//
//                Button deleteBtn = view.findViewById(R.id.delete_btn);
//                AssetManager assetManager = context.getAssets();
//                Typeface font = Typeface.createFromAsset( assetManager, "ionicons.ttf" );
//                deleteBtn.setTypeface(font);
//
//                deleteBtn.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        selectedPlaces.remove(position);
//                        notifyDataSetChanged();
//                    }
//               });
//
//                break;




    }

    return view;
  }
}