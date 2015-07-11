package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchFragment extends Fragment
{
    final static String LOG_TAG = ArtistSearchActivity.class.getSimpleName();

    static SpotifyService spotifyService;

    private ArtistSearchAdapter artistSearchResultsAdapter;

    public ArtistSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container, false);

        ArrayList<Artist> dummyData = new ArrayList<>();

        artistSearchResultsAdapter = new ArtistSearchAdapter (getActivity(), dummyData);

        ListView listView = (ListView) rootView.findViewById(R.id.artist_search_results);

        listView.setAdapter(artistSearchResultsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String artist = artistSearchResultsAdapter.getItem(position).toString();

                Intent topTenTracks = new Intent(getActivity(), TopTenTracksActivity.class);
                topTenTracks.putExtra(Intent.EXTRA_TEXT, artist);
                startActivity(topTenTracks);
            }
        });

        final EditText textView = (EditText) rootView.findViewById(R.id.artist_search_text_input);

        textView.setText("Type an artist to search for...");

        // This listener will only be triggered when the Enter key is pressed
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                String input = textView.getText().toString();

                new SearchSpotifyTask().execute(input);

                return false;
            }
        });

        return rootView;
    }

    public class SearchSpotifyTask extends AsyncTask<String, Void, ArtistsPager>
    {
        protected ArtistsPager doInBackground(String... strings)
        {
            spotifyService = new SpotifyApi().getService();

            ArtistsPager results = spotifyService.searchArtists(strings[0]);

            List<Artist> artists = results.artists.items;

            // Check for a zero-length list - this means no artists were found
            if (artists.size() == 0)
            {
                return null;
            }

            for (Artist artist : artists)
            {
                Log.i(LOG_TAG, artist.name);
            }

            return results;

        }

        protected void onPostExecute(ArtistsPager artists)
        {
            if (artists == null)
            {
                return;
            }

            artistSearchResultsAdapter.clear();

            for (Artist artist : artists.artists.items)
            {
                artistSearchResultsAdapter.add(artist);

                if (artist.images.size() != 0)
                {
                    Log.i(LOG_TAG, "Log image: " + artist.images.get(0).url);
                }
            }
        }
    }
}
