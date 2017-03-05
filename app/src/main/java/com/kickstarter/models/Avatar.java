package com.kickstarter.models;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class Avatar implements Parcelable {
  public abstract String medium();
  public abstract String small();
  public abstract String thumb();

  public abstract static class Builder {
    public abstract Builder medium(String __);
    public abstract Builder small(String __);
    public abstract Builder thumb(String __);
    public abstract Avatar build();
  }

  public static Builder builder() {
    return null;
  }
}
