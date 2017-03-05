package com.kickstarter.models.pushdata;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class GCM implements Parcelable {
  public abstract String alert();
  public abstract String title();

  public abstract static class Builder {
    public abstract Builder alert(String __);
    public abstract Builder title(String __);
    public abstract GCM build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
