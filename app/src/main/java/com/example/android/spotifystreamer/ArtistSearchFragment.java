package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import retrofit.RetrofitError;


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

        artistSearchResultsAdapter = new ArtistSearchAdapter (getActivity(), new ArrayList<Artist>());

        ListView listView = (ListView) rootView.findViewById(R.id.artist_search_results);

        listView.setAdapter(artistSearchResultsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Artist artist = artistSearchResultsAdapter.getItem(position);

                Intent topTenTracks = new Intent(getActivity(), TopTenTracksActivity.class);
                topTenTracks.putExtra(Intent.EXTRA_TEXT, artist.name);
                topTenTracks.putExtra(Intent.EXTRA_UID, artist.id);
                startActivity(topTenTracks);
            }
        });

        final EditText textView = (EditText) rootView.findViewById(R.id.artist_search_text_input);

        // This listener will only be triggered when the Enter key is pressed
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                String input = textView.getText().toString();

                if (input.isEmpty())
                {
                   Toast.makeText(getActivity(), R.string.nothing_to_search, Toast.LENGTH_LONG).show();
                }
                else
                {
                    new SearchSpotifyTask().execute(input);
                }

                return false;
            }
        });

        return rootView;
    }

    public class SearchSpotifyTask extends AsyncTask<String, Void, List<Artist>>
    {
        private SpotifyError spotifyError;

        protected List<Artist> doInBackground(String... strings)
        {
            try
            {
                spotifyService = new SpotifyApi().getService();
                return spotifyService.searchArtists(strings[0]).artists.items;
            }
            catch (RetrofitError error)
            {
                spotifyError = SpotifyError.fromRetrofitError(error);
                return null;
            }
        }

        protected void onPostExecute(List<Artist> artists)
        {
            if (spotifyError != null)
            {
                Toast.makeText(getActivity(), spotifyError.toString(), Toast.LENGTH_LONG);
                return;
            }

            if (artists == null || artists.size() == 0)
            {
                Toast.makeText(getActivity(), R.string.no_artists_found, Toast.LENGTH_LONG).show();
                return;
            }

            artistSearchResultsAdapter.clear();

            for (Artist artist : artists)
            {
                artistSearchResultsAdapter.add(artist);
            }
        }
    }
}
