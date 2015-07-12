package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import retrofit.RetrofitError;


public class TopTenTracksFragment extends Fragment
{
    private static final String LOG_TAG = TopTenTracksTask.class.getSimpleName();

    static SpotifyService spotifyService;

    private TopTenTracksAdapter topTenTracksAdapter;


    public TopTenTracksFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);

        topTenTracksAdapter = new TopTenTracksAdapter(getActivity(), new ArrayList<Track>());

        ListView listView = (ListView) rootView.findViewById(R.id.top_ten_tracks_results);

        listView.setAdapter(topTenTracksAdapter);


        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {
            // Using getActivity().getActionBar() returns null. I don't know of any other way to
            // access the action bar's subtitle.
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            ActionBar actionBar = activity.getSupportActionBar();

            if (actionBar != null)
            {
                actionBar.setSubtitle(intent.getStringExtra(Intent.EXTRA_TEXT));
            }
        }

        if (intent != null && intent.hasExtra(Intent.EXTRA_UID))
        {
            new TopTenTracksTask().execute(intent.getStringExtra(Intent.EXTRA_UID));
        }

        return rootView;
    }

    public class TopTenTracksTask extends AsyncTask<String, Void, List<Track>>
    {
        private SpotifyError spotifyError;

        protected List<Track> doInBackground(String... strings)
        {
            HashMap<String, Object> options = new HashMap<>();

            List<Track> results;

            // Using this method under the assumption that the reviewer's device will have a locale
            // which is allowed by Spotify. An error will be shown in the UI thread if this goes
            // wrong.
            options.put("country", Locale.getDefault().getCountry());

            try
            {
                spotifyService = new SpotifyApi().getService();
                results = spotifyService.getArtistTopTrack(strings[0], options).tracks;
            }
            catch (RetrofitError error)
            {
                spotifyError = SpotifyError.fromRetrofitError(error);
                return null;
            }

            return results;
        }

        protected void onPostExecute(List<Track> tracks)
        {
            if (spotifyError != null)
            {
                Toast.makeText(getActivity(), spotifyError.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            if (tracks == null || tracks.size() == 0)
            {
                Toast.makeText(getActivity(), R.string.no_tracks_found, Toast.LENGTH_LONG).show();
                return;
            }

            topTenTracksAdapter.clear();

            for (Track track : tracks)
            {
                topTenTracksAdapter.add(track);
            }
        }
    }
}
