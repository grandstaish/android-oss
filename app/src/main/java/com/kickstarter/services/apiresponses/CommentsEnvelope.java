package com.kickstarter.services.apiresponses;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.models.Comment;

import java.util.List;

@AutoGson
public abstract class CommentsEnvelope implements Parcelable {
  public abstract List<Comment> comments();
  public abstract UrlsEnvelope urls();

  @AutoGson
  public abstract static class UrlsEnvelope implements Parcelable {
    public abstract ApiEnvelope api();

    @AutoGson
    public abstract static class ApiEnvelope implements Parcelable {
      public abstract String moreComments();
      public abstract String newerComments();

      public abstract static class Builder {
        public abstract Builder moreComments(String __);
        public abstract Builder newerComments(String __);
        public abstract ApiEnvelope build();
      }

      public static Builder builder() {
        return null;
      }
    }

    public abstract static class Builder {
      public abstract Builder api(ApiEnvelope __);
      public abstract UrlsEnvelope build();
    }

    public static Builder builder() {
      return null;
    }
  }

  public abstract static class Builder {
    public abstract Builder comments(List<Comment> __);
    public abstract Builder urls(UrlsEnvelope __);
    public abstract CommentsEnvelope build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
