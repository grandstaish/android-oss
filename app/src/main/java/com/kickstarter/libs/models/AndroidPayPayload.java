package com.kickstarter.libs.models;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

import java.util.List;

/**
 * An object that represents the JSON payload received during checkout which describes how to construct native
 * Android Pay objects.
 */
@AutoGson
public abstract class AndroidPayPayload implements Parcelable {
  public abstract String stripePublishableKey();
  public abstract String stripeVersion();
  public abstract String merchantName();
  public abstract Boolean phoneNumberRequired();
  public abstract Boolean shippingAddressRequired();
  public abstract String currencyCode();
  public abstract String estimatedTotalPrice();
  public abstract Boolean allowDebitCard();
  public abstract Boolean allowPrepaidCard();
  public abstract Cart cart();

  @AutoGson
  public abstract static class Cart implements Parcelable {
    public abstract String currencyCode();
    public abstract String totalPrice();
    public abstract List<LineItem> lineItems();

    @AutoGson
    public abstract static class LineItem implements Parcelable {
      public abstract String currencyCode();
      public abstract String description();
      public abstract String quantity();
      public abstract String totalPrice();
      public abstract String unitPrice();
    }
  }
}
