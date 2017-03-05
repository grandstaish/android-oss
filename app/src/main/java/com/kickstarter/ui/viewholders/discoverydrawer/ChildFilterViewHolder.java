package com.kickstarter.ui.viewholders.discoverydrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.models.Category;
import com.kickstarter.ui.adapters.data.NavigationDrawerData;
import com.kickstarter.ui.viewholders.KSViewHolder;

import timber.log.Timber;

import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public final class ChildFilterViewHolder extends KSViewHolder {
  protected LinearLayout filterView;
  protected TextView filterTextView;
  protected int blackColor;
  protected int darkGrayColor;
  protected int filterSelectedColor;
  protected int filterUnselectedColor;

  private final KSString ksString;

  private NavigationDrawerData.Section.Row item;
  private Delegate delegate;

  public interface Delegate {
    void childFilterViewHolderRowClick(final @NonNull ChildFilterViewHolder viewHolder, final @NonNull NavigationDrawerData.Section.Row row);
  }

  public ChildFilterViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    this.ksString = environment().ksString();
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    item = requireNonNull((NavigationDrawerData.Section.Row) data, NavigationDrawerData.Section.Row.class);
  }

  @Override
  public void onBind() {
    final Context context = context();

    final Category category = item.params().category();
    if (category != null && category.isRoot()) {
      filterTextView.setText(item.params().filterString(context, ksString));
    } else {
      filterTextView.setText(item.params().filterString(context, ksString));
    }
    if (item.selected()) {
      filterTextView.setTextAppearance(context, R.style.SubheadPrimaryMedium);
      filterTextView.setTextColor(blackColor);
    } else {
      filterTextView.setTextAppearance(context, R.style.SubheadPrimary);
      filterTextView.setTextColor(darkGrayColor);
    }

    filterView.setBackgroundColor(item.selected() ? filterSelectedColor : filterUnselectedColor);
  }

  protected void textViewClick() {
    Timber.d("DiscoveryDrawerChildParamsViewHolder topFilterViewHolderRowClick");
    delegate.childFilterViewHolderRowClick(this, item);
  }
}

