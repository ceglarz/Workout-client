package ceglarz.streetworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class MyTrainingsListAdapter extends BaseAdapter implements android.widget.ListAdapter {
  private List<Training> trainings;
  private int layoutResId;
  private Context context;

  MyTrainingsListAdapter(List<Training> trainings, int layoutResId, Context context) {
    this.trainings = trainings;
    this.layoutResId = layoutResId;
    this.context = context;
  }


  @Override
  public int getCount() {
    return trainings.size();
  }

  @Override
  public Object getItem(int pos) {
    return trainings.get(pos);
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
    TextView listItemData = view.findViewById(R.id.list_item_data);
    TextView listItemMiejsce = view.findViewById(R.id.list_item_event);

    switch (layoutResId){

      case (R.layout.list_element_training):
        listItemData.setText(trainings.get(position).getDate());
        listItemMiejsce.setText(trainings.get(position).getEvent());
        break;

    }

    return view;
  }
}