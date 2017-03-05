package com.kickstarter.ui.adapters.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kickstarter.libs.utils.ListUtils;
import com.kickstarter.models.Category;
import com.kickstarter.models.User;
import com.kickstarter.services.DiscoveryParams;

import java.util.List;

public abstract class NavigationDrawerData {
  public abstract @Nullable User user();
  public abstract List<Section> sections();

  public abstract @Nullable Category expandedCategory();
  public abstract @Nullable DiscoveryParams selectedParams();

  public abstract static class Builder {
    public abstract Builder user(User __);
    public abstract Builder sections(List<Section> __);
    public abstract Builder expandedCategory(Category __);
    public abstract Builder selectedParams(DiscoveryParams __);
    public abstract NavigationDrawerData build();
  }
  public static Builder builder() {
    return null;
  }
  public abstract Builder toBuilder();

  static public abstract class Section {
    public abstract boolean expandable();
    public abstract boolean expanded();
    public abstract List<Section.Row> rows();

    public abstract static class Builder {
      public abstract Section.Builder expandable(boolean __);
      public abstract Section.Builder expanded(boolean __);
      public abstract Section.Builder rows(List<Section.Row> __);
      public abstract Section build();
    }

    public static Section.Builder builder() {
      return null;
    }
    public abstract Section.Builder toBuilder();

    public boolean isCategoryFilter() {
      return rows().size() >= 1 && rows().get(0).params().isCategorySet();
    }

    public boolean isTopFilter() {
      return !isCategoryFilter();
    }

    static public abstract class Row {
      public abstract @NonNull DiscoveryParams params();
      public abstract boolean selected();
      public abstract boolean rootIsExpanded();

      public static abstract class Builder {
        public abstract Builder params(DiscoveryParams __);
        public abstract Builder selected(boolean __);
        public abstract Builder rootIsExpanded(boolean __);
        public abstract Section.Row build();
      }
      public static Builder builder() {
        return null;
      }
      public abstract Section.Row.Builder toBuilder();
    }
  }
}
