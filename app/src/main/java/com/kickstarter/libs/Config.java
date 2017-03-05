package com.kickstarter.libs;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.kickstarter.libs.qualifiers.AutoGson;

import java.util.List;

@AutoGson
public abstract class Config implements Parcelable {
  public abstract String countryCode();
  public abstract List<LaunchedCountry> launchedCountries();

  public abstract static class Builder {
    public abstract Builder countryCode(String __);
    public abstract Builder launchedCountries(List<LaunchedCountry> __);
    public abstract Config build();
  }

  @AutoGson
  public abstract static class LaunchedCountry implements Parcelable {
    public abstract String name();
    public abstract String currencyCode();
    public abstract String currencySymbol();
    public abstract Boolean trailingCode();

    public abstract static class Builder {
      public abstract Builder name(String __);
      public abstract Builder currencyCode(String __);
      public abstract Builder currencySymbol(String __);
      public abstract Builder trailingCode(Boolean __);
      public abstract LaunchedCountry build();
    }

    public static Builder builder() {
      return null;
    }

    public abstract Builder toBuilder();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();

  /**
   * A currency needs a code if its symbol is ambiguous, e.g. `$` is used for currencies such as USD, CAD, AUD.
   */
  public boolean currencyNeedsCode(final @NonNull String currencySymbol) {
    for (final LaunchedCountry country : launchedCountries()) {
      if (country.currencySymbol().equals(currencySymbol)) {
        return country.trailingCode();
      }
    }

    // Unlaunched country, default to showing the code.
    return true;
  }
}
