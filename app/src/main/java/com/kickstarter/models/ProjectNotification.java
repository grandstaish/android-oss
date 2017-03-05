package com.kickstarter.models;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class ProjectNotification implements Parcelable {
  public abstract Project project();
  public abstract long id();
  public abstract boolean email();
  public abstract boolean mobile();
  public abstract Urls urls();

  public abstract static class Builder {
    public abstract Builder project(Project __);
    public abstract Builder id(long __);
    public abstract Builder email(boolean __);
    public abstract Builder mobile(boolean __);
    public abstract Builder urls(Urls __);
    public abstract ProjectNotification build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();

  @AutoGson
  public abstract static class Project implements Parcelable {
    public abstract String name();
    public abstract long id();

    public abstract static class Builder {
      public abstract Builder name(String __);
      public abstract Builder id(long __);
      public abstract Project build();
    }

    public static Builder builder() {
      return null;
    }
  }

  @AutoGson
  public abstract static class Urls implements Parcelable {
    public abstract Api api();

    public abstract static class Builder {
      public abstract Builder api(Api __);
      public abstract Urls build();
    }

    public static Builder builder() {
      return null;
    }

    @AutoGson
    public abstract static class Api implements Parcelable {
      public abstract String notification();

      public abstract static class Builder {
        public abstract Builder notification(String __);
        public abstract Api build();
      }

      public static Builder builder() {
        return null;
      }
    }
  }
}
