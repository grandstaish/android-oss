package com.kickstarter.services.apiresponses;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.models.Activity;

import java.util.List;

@AutoGson
public abstract class ActivityEnvelope implements Parcelable {
  public abstract List<Activity> activities();
  public abstract UrlsEnvelope urls();

  @AutoGson
  public abstract static class UrlsEnvelope implements Parcelable {
    public abstract ApiEnvelope api();

    @AutoGson
    public abstract static class ApiEnvelope implements Parcelable {
      public abstract String moreActivities();
      public abstract @Nullable String newerActivities();

      public abstract static class Builder {
        public abstract Builder moreActivities(String __);
        public abstract Builder newerActivities(String __);
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
    public abstract Builder activities(List<Activity> __);
    public abstract Builder urls(UrlsEnvelope __);
    public abstract ActivityEnvelope build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
