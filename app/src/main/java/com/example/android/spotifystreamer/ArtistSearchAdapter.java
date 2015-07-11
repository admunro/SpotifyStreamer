package com.example.android.spotifystreamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by andy on 11/07/15.
 */
public class ArtistSearchAdapter extends ArrayAdapter<Artist>
{
    private static final String LOG_TAG = ArtistSearchAdapter.class.getSimpleName();

    public ArtistSearchAdapter(Activity context, List<Artist> results)
    {
        super(context, 0, results);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Artist artist = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_artist_result, parent, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.fragment_artist_result_image);
        TextView  textView  = (TextView)  rootView.findViewById(R.id.fragment_artist_result_name);

        textView.setText(artist.name);

        if (artist.images.size() != 0)
        {
            Picasso.with(getContext()).load(artist.images.get(0).url).resize(50, 50).centerCrop().into(imageView);
        }

        return rootView;
    }
}