package recyclerview.selectablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private MainActivity activity;
    private List<ListItem> data = new ArrayList<>();

    public RecyclerViewAdapter(MainActivity activity) {
        this.activity = activity;
        // Since we are enabling stable ids, we also need to override getItemId method
        setHasStableIds(true);  // This enables animations for list elements
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CheckBox selectedCheckBox;
        public TextView textView;

        public MyViewHolder(View itemView, final MainActivity activity) {
            super(itemView);

            selectedCheckBox = (CheckBox) itemView.findViewById(R.id.selected_check_box);
            textView = (TextView) itemView.findViewById(R.id.item_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity.isSelectionMode()) {
                        int position = activity.getRecyclerView().getChildAdapterPosition(view);
                        activity.getAdapter().toggleSelected(position);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout listItemLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(listItemLayout, activity);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position).text);
        holder.selectedCheckBox.setChecked(data.get(position).selected);
        holder.selectedCheckBox.setVisibility(activity.isSelectionMode() ? View.VISIBLE : View.GONE);
        holder.selectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.get(holder.getAdapterPosition()).selected = isChecked;
            }
        });
    }

    public void addTestData() {
        data.add(0, new ListItem("Test Data", false));
        notifyItemInserted(0);
    }

    public void removeSelectedItems() {
        // We are iterating through list from end to start, otherwise we'd get the concurrent modification error
        for (int i = getItemCount() - 1; i >= 0; i--) {
            if (data.get(i).selected) {
                data.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void toggleSelected(int position) {
        ListItem listItem = data.get(position);
        listItem.selected = !listItem.selected;
        notifyItemChanged(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
