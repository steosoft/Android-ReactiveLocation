package pl.charmas.android.reactivelocation.sample;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.subscriptions.CompositeSubscription;

import static pl.charmas.android.reactivelocation.sample.utils.UnsubscribeIfPresent.unsubscribe;

public class PlacesActivity extends BaseActivity {

    private TextView currentPlaceView;
    private EditText queryView;
    private ListView placeSuggestionsList;
    private ReactiveLocationProvider reactiveLocationProvider;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        currentPlaceView = (TextView) findViewById(R.id.current_place_view);
        queryView = (EditText) findViewById(R.id.place_query_view);
        placeSuggestionsList = (ListView) findViewById(R.id.place_suggestions_list);
        placeSuggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompleteInfo info = (AutocompleteInfo) parent.getAdapter().getItem(position);
                startActivity(PlacesResultActivity.getStartIntent(PlacesActivity.this, info.id));
            }
        });

        reactiveLocationProvider = new ReactiveLocationProvider(this);
    }

    @Override
    protected void onLocationPermissionGranted() {
    }

    @Override
    protected void onStop() {
        super.onStop();
        unsubscribe(compositeSubscription);
    }

    private static class QueryWithCurrentLocation {
        public final String query;
        public final Location location;

        private QueryWithCurrentLocation(String query, Location location) {
            this.query = query;
            this.location = location;
        }
    }

    private static class AutocompleteInfo {
        private final String description;
        private final String id;

        private AutocompleteInfo(String description, String id) {
            this.description = description;
            this.id = id;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
