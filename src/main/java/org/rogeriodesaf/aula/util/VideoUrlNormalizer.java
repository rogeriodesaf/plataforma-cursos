package org.rogeriodesaf.aula.util;

import java.net.URI;
import java.net.URISyntaxException;

public final class VideoUrlNormalizer {

    private VideoUrlNormalizer() {
    }

    public static String normalize(String rawUrl) {
        if (rawUrl == null) {
            return null;
        }

        String trimmedUrl = rawUrl.trim();
        if (trimmedUrl.isEmpty()) {
            return trimmedUrl;
        }

        try {
            URI uri = new URI(trimmedUrl);
            String host = uri.getHost();
            if (host == null) {
                return trimmedUrl;
            }

            String normalizedHost = host.toLowerCase();
            String videoId = extractYoutubeVideoId(normalizedHost, uri);
            if (videoId == null || videoId.isBlank()) {
                return trimmedUrl;
            }

            return "https://www.youtube.com/embed/" + videoId;
        } catch (URISyntaxException ignored) {
            return trimmedUrl;
        }
    }

    private static String extractYoutubeVideoId(String host, URI uri) {
        if (host.equals("youtu.be")) {
            return extractLastSegment(uri.getPath());
        }

        if (!host.contains("youtube.com")) {
            return null;
        }

        String path = uri.getPath();
        if (path == null || path.isBlank()) {
            return extractQueryParam(uri.getQuery(), "v");
        }

        if (path.startsWith("/watch")) {
            return extractQueryParam(uri.getQuery(), "v");
        }

        if (path.startsWith("/embed/")) {
            return extractLastSegment(path);
        }

        if (path.startsWith("/shorts/") || path.startsWith("/live/")) {
            return extractLastSegment(path);
        }

        return extractQueryParam(uri.getQuery(), "v");
    }

    private static String extractLastSegment(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }

        String[] segments = path.split("/");
        for (int i = segments.length - 1; i >= 0; i--) {
            if (!segments[i].isBlank()) {
                return segments[i];
            }
        }

        return null;
    }

    private static String extractQueryParam(String query, String name) {
        if (query == null || query.isBlank()) {
            return null;
        }

        String[] params = query.split("&");
        for (String param : params) {
            String[] parts = param.split("=", 2);
            if (parts.length == 2 && parts[0].equals(name) && !parts[1].isBlank()) {
                return parts[1];
            }
        }

        return null;
    }
}
