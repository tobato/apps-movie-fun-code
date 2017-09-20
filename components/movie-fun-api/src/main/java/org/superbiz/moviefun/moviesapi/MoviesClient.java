package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * simple rest
 * @author wuyf
 *
 */
public class MoviesClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String moviesUrl;
    private RestOperations restClient;
    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };


    public MoviesClient(String moviesUrl, RestOperations restClient) {
        this.moviesUrl = moviesUrl;
        this.restClient = restClient;
    }

    private String getServerUrl(String path) {
        return moviesUrl + path;
    }

    public MovieInfo find(Long id) {
        return restClient.getForObject(getServerUrl("/"+id),MovieInfo.class);
    }


    public void addMovie(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());
        restClient.postForObject(getServerUrl("/"),movie,MovieInfo.class);
    }


    public void updateMovie(MovieInfo movie) {
        restClient.put(getServerUrl("/"+movie.getId()),movie,MovieInfo.class);
    }


    public void deleteMovie(MovieInfo movie) {
        this.deleteMovieId(movie.getId());
    }


    public void deleteMovieId(long id) {
        restClient.delete(getServerUrl("/"+id));
    }

    public List<MovieInfo> getMovies() {
        return restClient.exchange(
                moviesUrl,
                HttpMethod.GET,
                null,
                movieListType).getBody();
    }

    public List<MovieInfo> findAll(int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);
        return restClient.exchange(builder.toUriString(),
                HttpMethod.GET, null, movieListType).getBody();
    }

    public int countAll() {
        return restClient.getForObject(getServerUrl("/count"),Integer.class);
    }

    public int count(String field, String key) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getServerUrl("/count"))
                .queryParam("field", field)
                .queryParam("key", key);
        return restClient.getForObject(builder.toUriString(), Integer.class);

    }

    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restClient.exchange(builder.toUriString(), HttpMethod.GET, null, movieListType).getBody();
    }

}
