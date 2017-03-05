package com.kickstarter.ui.viewholders.discoverydrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.ui.adapters.data.NavigationDrawerData;
import com.kickstarter.ui.viewholders.KSViewHolder;

import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public final class TopFilterViewHolder extends KSViewHolder {
  protected LinearLayout filterView;
  protected TextView filterTextView;
  protected int filterSelectedColor;
  protected int filterUnselectedColor;
  private NavigationDrawerData.Section.Row item;
  private Delegate delegate;

  public interface Delegate {
    void topFilterViewHolderRowClick(final @NonNull TopFilterViewHolder viewHolder, final @NonNull NavigationDrawerData.Section.Row row);
  }

  public TopFilterViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    item = requireNonNull((NavigationDrawerData.Section.Row) data, NavigationDrawerData.Section.Row.class);
  }

  @Override
  public void onBind() {
    final Context context = context();

    filterTextView.setText(item.params().filterString(context, environment().ksString()));
    filterTextView.setTextAppearance(context, item.selected() ? R.style.SubheadPrimaryMedium : R.style.SubheadPrimary);

    filterView.setBackgroundColor(item.selected() ? filterSelectedColor : filterUnselectedColor);
  }

  protected void textViewClick() {
    delegate.topFilterViewHolderRowClick(this, item);
  }
}

