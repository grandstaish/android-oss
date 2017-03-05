package com.kickstarter.services.apiresponses;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.models.Category;

import java.util.List;

@AutoGson
public abstract class CategoriesEnvelope implements Parcelable {
  public abstract List<Category> categories();
}
