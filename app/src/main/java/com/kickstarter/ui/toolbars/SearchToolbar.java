package com.kickstarter.ui.toolbars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.kickstarter.R;
import com.kickstarter.ui.activities.SearchActivity;
import com.kickstarter.ui.views.IconButton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public final class SearchToolbar extends KSToolbar {
  public IconButton clearButton;
  public EditText searchEditText;

  public SearchToolbar(final @NonNull Context context) {
    super(context);
  }

  public SearchToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SearchToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    if (isInEditMode()) {
      return;
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    final Observable<CharSequence> text = RxTextView.textChanges(searchEditText);
    final Observable<Boolean> clearable = text.map(t -> t.length() > 0);

    addSubscription(clearable
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(c -> clearButton.setVisibility(c ? View.VISIBLE : View.INVISIBLE)));

    addSubscription(text
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(t -> ((SearchActivity) getContext()).viewModel().inputs.search(t.toString())));
  }

  public void clearButtonClick() {
    searchEditText.setText(null);
  }
}
