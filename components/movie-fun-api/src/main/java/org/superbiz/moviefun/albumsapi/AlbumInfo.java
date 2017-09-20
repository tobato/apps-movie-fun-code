package org.superbiz.moviefun.albumsapi;

/**
 * @author wuyf
 * @create 2017-09-20 下午12:18
 */
public class AlbumInfo {

    private Long id;

    private String artist;
    private String title;
    private int year;
    private int rating;

    public AlbumInfo() {
    }

    public AlbumInfo(String artist, String title, int year, int rating) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumInfo albumInfo = (AlbumInfo) o;

        if (year != albumInfo.year) return false;
        if (rating != albumInfo.rating) return false;
        if (id != null ? !id.equals(albumInfo.id) : albumInfo.id != null) return false;
        if (artist != null ? !artist.equals(albumInfo.artist) : albumInfo.artist != null) return false;
        return title != null ? title.equals(albumInfo.title) : albumInfo.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + rating;
        return result;
    }
}
