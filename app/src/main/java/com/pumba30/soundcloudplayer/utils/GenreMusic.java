package com.pumba30.soundcloudplayer.utils;

public enum GenreMusic {

    ALL_MUSIC(0, "all-music"),
    ALTERNATIVE_ROCK(1, "alternativerock"),
    AMBIENT(2, "ambient"),
    CLASSICAL(3, "classical"),
    COUNTRY(4, "country"),
    DANCE_EDM(5, "danceedm"),
    DANCEHALL(6, "dancehall"),
    DEEP_HOUSE(7, "deephouse"),
    DISCO(8, "disco"),
    DRUM_BASS(9, "drumbass"),
    DUBSTEP(10, "dubstep"),
    ELECTRONIC(11, "electronic"),
    FOLK(12, "folksingersongwriter"),
    HIP_HOP_RAP(13, "hiphoprap"),
    HOUSE(14, "house"),
    INDIE(15, "indie"),
    JAZZ_BLUES(16, "jazzblues"),
    LATIN(17, "latin"),
    METAL(18, "metal"),
    PIANO(19, "piano"),
    POP(20, "pop"),
    RNB_SOUL(21, "rbsoul"),
    REGGAE(22, "reggae"),
    REGGAETON(23, "reggaeton"),
    ROCK(24, "rock"),
    SOUNDTRACK(25, "soundtrack"),
    TECHNO(26, "techno"),
    TRANCE(27, "trance"),
    TRAP(28, "trap"),
    TRIPHOP(29, "triphop"),
    WORLD(30, "world");


    private int mPosition;
    private String mGenre;


    GenreMusic(int position, String genre) {
        mPosition = position;
        mGenre = genre;
    }


    public static String getGenre(int position) {
        String genre = null;
        for (GenreMusic genreMusic : values()) {
            if (genreMusic.mPosition == position) {
                genre = genreMusic.mGenre;
                break;
            }
        }

        return genre;
    }

}
