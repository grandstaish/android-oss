package com.kickstarter.services.apiresponses;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.models.Project;

import java.util.List;

@AutoGson
public abstract class DiscoverEnvelope implements Parcelable {
  public abstract List<Project> projects();
  public abstract UrlsEnvelope urls();

  public abstract static class Builder {
    public abstract Builder projects(List<Project> __);
    public abstract Builder urls(UrlsEnvelope __);
    public abstract DiscoverEnvelope build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();

  @AutoGson
  public abstract static class UrlsEnvelope implements Parcelable {
    public abstract ApiEnvelope api();

    public abstract static class Builder {
      public abstract Builder api(ApiEnvelope __);
      public abstract UrlsEnvelope build();
    }

    public static Builder builder() {
      return null;
    }

    public abstract Builder toBuilder();

    @AutoGson
    public abstract static class ApiEnvelope implements Parcelable {
      public abstract String moreProjects();

      public abstract static class Builder {
        public abstract Builder moreProjects(String __);
        public abstract ApiEnvelope build();
      }

      public static Builder builder() {
        return null;
      }
    }
  }
}
