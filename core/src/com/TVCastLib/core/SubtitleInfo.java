
package com.TVCastLib.core;

import android.support.annotation.NonNull;

/**
 * Normalized reference object for information about a subtitle track. It's used in `MediaInfo` class.
 * The only one required parameter is `url`, others can be `null`. This class is immutable and has
 * a builder for easy construction.
 *
 * Different services support specific subtitle formats:
 *  - `DLNAService` supports only SRT subtitles. Since there is no official specification for them,
 *  subtitles may not work on all DLNA-compatible devices
 *  - `NetcastTVService` supports only SRT subtitles and has the same restrictions as `DLNAService`
 *  - `CastService` supports only WebVTT subtitles and it has additional requirements
 *  @see {@link https://developers.google.com/cast/docs/android_sender#cors-requirements}
 *  - `FireTVService` supports only WebVTT subtitles
 *  - `WebOSTVService` supports WebVTT subtitles. Server providing subtitles should
 *  support CORS headers, similarly to Cast service's requirements.
 *
 */
public class SubtitleInfo {
    private final String url;
    private final String mimeType;
    private final String label;
    private final String language;

    public static class Builder {
        // required fields
        private String url;

        // optional fields
        private String mimeType;
        private String label;
        private String language;

        public Builder(@NonNull String url) {
            this.url = url;
        }

        public Builder setMimeType(@NonNull String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder setLabel(@NonNull String label) {
            this.label = label;
            return this;
        }

        public Builder setLanguage(@NonNull String language) {
            this.language = language;
            return this;
        }

        public SubtitleInfo build() {
            return new SubtitleInfo(this);
        }
    }

    private SubtitleInfo(SubtitleInfo.Builder builder) {
        url = builder.url;
        mimeType = builder.mimeType;
        label = builder.label;
        language = builder.language;
    }

    public String getUrl() {
        return url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getLabel() {
        return label;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubtitleInfo that = (SubtitleInfo) o;

        if (getUrl() != null ? !getUrl().equals(that.getUrl()) : that.getUrl() != null) {
            return false;
        }
        return !(getMimeType() != null ? !getMimeType().equals(that.getMimeType()) : that.getMimeType() != null);

    }

    @Override
    public int hashCode() {
        int result = getUrl() != null ? getUrl().hashCode() : 0;
        result = 31 * result + (getMimeType() != null ? getMimeType().hashCode() : 0);
        return result;
    }
}
