package com.kickstarter.services.apirequests;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class SignupBody {
  public abstract String name();
  public abstract String email();
  public abstract boolean newsletterOptIn();
  public abstract String password();
  public abstract String passwordConfirmation();
  public abstract boolean sendNewsletters();

  public abstract static class Builder {
    public abstract Builder name(String __);
    public abstract Builder email(String __);
    public abstract Builder newsletterOptIn(boolean __);
    public abstract Builder password(String __);
    public abstract Builder passwordConfirmation(String __);
    public abstract Builder sendNewsletters(boolean __);
    public abstract SignupBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
