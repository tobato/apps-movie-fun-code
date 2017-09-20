package org.superbiz.moviefun.albums;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlbumsRepository albumsRepository;

    public AlbumsController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }


    @PostMapping
    public void addAlbum(@RequestBody Album album){
        albumsRepository.addAlbum(album);
    }

    @GetMapping
    public List<Album> getAlbums(){
        return albumsRepository.getAlbums();

    }


    @GetMapping("/{albumId}")
    public Album details(@PathVariable long albumId) {
        return albumsRepository.find(albumId);
    }

    @DeleteMapping("/{albumId}")
    public void delete(@PathVariable long albumId) {
        Album album = albumsRepository.find(albumId);
        if(null!=album){
            albumsRepository.deleteAlbum(album);
        }
    }

    @PutMapping("/{albumId}")
    public void update(@PathVariable long albumId,@RequestBody Album album){
        Album updateAlbum = albumsRepository.find(albumId);
        if(null!=updateAlbum){
            updateAlbum.setArtist(album.getArtist());
            updateAlbum.setRating(album.getRating());
            updateAlbum.setTitle(album.getTitle());
            updateAlbum.setYear(album.getYear());
            albumsRepository.updateAlbum(updateAlbum);
        }
    }

}
