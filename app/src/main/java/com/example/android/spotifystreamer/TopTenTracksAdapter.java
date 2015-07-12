package com.example.android.spotifystreamer;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by andy on 11/07/15.
 */
public class TopTenTracksAdapter extends ArrayAdapter<Track>
{
    private static final String LOG_TAG = TopTenTracksAdapter.class.getSimpleName();

    // For some reason, encoding these values in the dimens.xml file results in blank images, so
    // I've used consts instead.
    private static final int IMAGE_WIDTH  = 64;
    private static final int IMAGE_HEIGHT = 64;

    public TopTenTracksAdapter(Activity context, List<Track> results)
    {
        super(context, 0, results);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Track track = getItem(position);

        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.fragment_top_ten_tracks_result, parent, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.fragment_top_ten_tracks_image);
        TextView  song      = (TextView)  rootView.findViewById(R.id.fragment_top_ten_tracks_song);
        TextView  album     = (TextView)  rootView.findViewById(R.id.fragment_top_ten_tracks_album);

        song.setText(track.name);
        album.setText(track.album.name);
        album.setTypeface(null, Typeface.ITALIC);

        if (track.album.images.size() != 0)
        {
            Picasso.with(getContext()).load(track.album.images.get(0).url)
                                      .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                                      .centerCrop()
                                      .into(imageView);
        }

        return rootView;
    }
}
