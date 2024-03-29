package course.labs.todomanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;
    private LayoutInflater mInflater;

    private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // TODO - Get the current ToDoItem
        final ToDoItem toDoItem = (ToDoItem) getItem(position);

        if (convertView == null) {
            // TODO - Inflate the View for this ToDoItem
            // from todo_item.xml
            mInflater=(LayoutInflater)mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView=mInflater.inflate(R.layout.todo_item,null);

            holder = new ViewHolder();


            // Fill in specific ToDoItem data
            // Remember that the data that goes in this View
            // corresponds to the user interface elements defined
            // in the layout file

            // TODO - Display Title in TextView
            holder.titleView=(TextView)convertView.findViewById(R.id.titleView);

            // TODO - Set up Status CheckBox
            holder.statusView=(CheckBox)convertView.findViewById(R.id.statusCheckBox);


            // TODO - Must also set up an OnCheckedChangeListener,
            // which is called when the user toggles the status checkbox

            holder.statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if(isChecked){ // if the checkbox is now marked as DONE

                        // change the status of the item itself
                        toDoItem.setStatus(ToDoItem.Status.DONE);

                        // and change the appearance of the checkbox
                        holder.statusView.setChecked(true);
                    } else {
                        // do the opposite
                        toDoItem.setStatus(ToDoItem.Status.NOTDONE);
                        holder.statusView.setChecked(false);
                    }





                }
            });

            // TODO - Display Priority in a TextView
             holder.priorityView = (TextView)convertView.findViewById(R.id.priorityView);



            // TODO - Display Time and Date.
             holder.dateView = (TextView)convertView.findViewById(R.id.dateView);

            convertView.setTag(holder);

        }
        else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();

        }


        holder.titleView.setText(toDoItem.getTitle());
        holder.statusView.setChecked(toDoItem.getStatus() == ToDoItem.Status.DONE);
        holder.priorityView.setText(toDoItem.getPriority().toString());
        // Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
        // time String
        holder.dateView.setText(toDoItem.FORMAT.format(toDoItem.getDate()));










		// Return the View you just created
		return convertView;


	}
    static class ViewHolder {
         TextView titleView = null;
         CheckBox statusView = null;
        TextView priorityView=null;
        TextView dateView=null;

    }

}
