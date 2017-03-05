package com.kickstarter.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class Video implements Parcelable {
  public abstract String base();
  public abstract String frame();
  public abstract String high();
  public abstract @Nullable String webm();

  public abstract static class Builder {
    public abstract Builder base(String __);
    public abstract Builder frame(String __);
    public abstract Builder high(String __);
    public abstract Builder webm(String __);
    public abstract Video build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
