package com.example.android.spotifystreamer;

/**
 * Created by andy on 11/07/15.
 */
public class ArtistSearchResult
{
    String artistName;
    int    artistImage;

    public ArtistSearchResult(String name, int image)
    {
        this.artistName  = name;
        this.artistImage = image;
    }
}
