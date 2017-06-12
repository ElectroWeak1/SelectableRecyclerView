package recyclerview.selectablerecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private boolean selectionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);     // Improves performance when we know item size won't change

        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 5; i++) {
            adapter.addTestData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void toggleSelectionMode(MenuItem menuItem) {
        selectionMode = !selectionMode;
        adapter.notifyDataSetChanged();
    }

    public void addItem(MenuItem menuItem) {
        adapter.addTestData();
    }

    public void removeSelectedItems(MenuItem menuItem) {
        adapter.removeSelectedItems();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public boolean isSelectionMode() {
        return selectionMode;
    }

}
