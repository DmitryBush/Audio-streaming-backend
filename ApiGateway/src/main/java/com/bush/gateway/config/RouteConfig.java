package com.bush.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder builder = getStreamingServiceRoutes(routeLocatorBuilder.routes());
        builder = getUserServiceRoutes(builder);
        builder = getSearchServiceRoutes(builder);
        return builder.build();
    }

    private RouteLocatorBuilder.Builder getStreamingServiceRoutes(RouteLocatorBuilder.Builder builder) {
        return builder.route("uploads_route", predicateSpec -> predicateSpec
                        .path("/api/*/uploads/**")
                        .uri(ServiceUriEnum.STREAMING_SERVICE_URI.getServiceUri()))
                .route("streaming_route", predicateSpec -> predicateSpec
                        .method(HttpMethod.GET).and()
                        .path("/api/*/streaming/**")
                        .uri(ServiceUriEnum.STREAMING_SERVICE_URI.getServiceUri()))
                .route("public_songs_route", predicateSpec -> predicateSpec
                        .method(HttpMethod.GET).and()
                        .path("/api/*/songs/**")
                        .uri(ServiceUriEnum.STREAMING_SERVICE_URI.getServiceUri()))
                .route("public_genres_route", predicateSpec -> predicateSpec
                        .method(HttpMethod.GET).and()
                        .path("/api/*/genres")
                        .uri(ServiceUriEnum.STREAMING_SERVICE_URI.getServiceUri()))
                .route("public_artists_route", predicateSpec -> predicateSpec
                        .method(HttpMethod.GET).and()
                        .path("/api/*/artists/**")
                        .uri(ServiceUriEnum.STREAMING_SERVICE_URI.getServiceUri()))
                .route("public_albums_route", predicateSpec -> predicateSpec
                        .method(HttpMethod.GET).and()
                        .path("/api/*/albums/**")
                        .uri(ServiceUriEnum.STREAMING_SERVICE_URI.getServiceUri()));
    }

    private RouteLocatorBuilder.Builder getUserServiceRoutes(RouteLocatorBuilder.Builder builder) {
        return builder
                .route("user_route", predicateSpec -> predicateSpec
                        .path("/api/*/users/**")
                        .uri(ServiceUriEnum.USER_SERVICE_URI.getServiceUri()))
                .route("login_route", predicateSpec -> predicateSpec
                        .path("/api/*/login").and()
                        .uri(ServiceUriEnum.USER_SERVICE_URI.getServiceUri()))
                .route("register_route", predicateSpec -> predicateSpec
                        .path("/api/v1/register")
                        .uri(ServiceUriEnum.USER_SERVICE_URI.getServiceUri()))
                .route("change_password_route", predicateSpec -> predicateSpec
                        .path("/api/*/change-password")
                        .uri(ServiceUriEnum.USER_SERVICE_URI.getServiceUri()))
                .route("refresh_token_route", predicateSpec -> predicateSpec
                        .path("/api/*/refresh-token")
                        .uri(ServiceUriEnum.USER_SERVICE_URI.getServiceUri()))
                .route("playlist_route", predicateSpec -> predicateSpec
                        .path("/api/*/playlists/**")
                        .uri(ServiceUriEnum.USER_SERVICE_URI.getServiceUri()));
    }

    private RouteLocatorBuilder.Builder getSearchServiceRoutes(RouteLocatorBuilder.Builder builder) {
        return builder
                .route("public_search_routes", predicateSpec -> predicateSpec
                        .method(HttpMethod.GET).and()
                        .path("/api/*/search/**")
                        .uri(ServiceUriEnum.SEARCH_SERVICE_URI.getServiceUri()));
    }
}
