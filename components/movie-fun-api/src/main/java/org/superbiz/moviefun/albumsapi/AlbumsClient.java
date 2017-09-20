package org.superbiz.moviefun.albumsapi; /**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class AlbumsClient {

    private String albumsUrl;
    private RestOperations restClient;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsUrl, RestOperations restClient) {
        this.albumsUrl = albumsUrl;
        this.restClient = restClient;
    }

    private String getServerUrl(String path) {
        return albumsUrl + path;
    }

    public void addAlbum(AlbumInfo album) {
        restClient.postForEntity(albumsUrl,album,AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        return restClient.getForObject(getServerUrl("/"+id),AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restClient.exchange(albumsUrl,
                HttpMethod.GET, null, albumListType).getBody();
    }

    public void deleteAlbum(AlbumInfo album) {
        restClient.delete(getServerUrl("/"+album.getId()));
    }

    public void updateAlbum(AlbumInfo album) {

        restClient.put(getServerUrl("/"+album.getId()),album,AlbumInfo.class);
    }
}
