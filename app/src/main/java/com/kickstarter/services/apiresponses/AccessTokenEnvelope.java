package com.kickstarter.services.apiresponses;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.models.User;

@AutoGson
public abstract class AccessTokenEnvelope implements Parcelable {
  public abstract String accessToken();
  public abstract User user();

  public abstract static class Builder {
    public abstract Builder accessToken(String __);
    public abstract Builder user(User __);
    public abstract AccessTokenEnvelope build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
