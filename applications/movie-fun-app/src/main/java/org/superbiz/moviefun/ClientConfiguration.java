package org.superbiz.moviefun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.moviesapi.MoviesClient;

/**
 * @author wuyf
 * @create 2017-09-20 上午8:57
 */
@Configuration
public class ClientConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${movies.url}")
    String moviesUrl;
    @Value("${albums.url}")
    String albumsUrl;

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public MoviesClient moviesClient(RestOperations restOperations) {
        logger.debug("moviesUrl={}", moviesUrl);
        return new MoviesClient(moviesUrl, restOperations);
    }

    @Bean
    public AlbumsClient albumsClient(RestOperations restOperations) {
        logger.debug("albumsUrl={}", albumsUrl);
        return new AlbumsClient(albumsUrl, restOperations);
    }
}