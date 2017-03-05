package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.utils.DateTimeUtils;
import com.kickstarter.libs.utils.NumberUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.models.Project;
import com.kickstarter.models.Reward;
import com.kickstarter.ui.IntentKey;
import com.kickstarter.ui.activities.CheckoutActivity;
import com.kickstarter.ui.activities.ViewPledgeActivity;
import com.kickstarter.ui.adapters.RewardsItemAdapter;
import com.kickstarter.viewmodels.RewardViewModel;

import static com.kickstarter.libs.rx.transformers.Transformers.observeForUI;
import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;
import static com.kickstarter.libs.utils.TransitionUtils.slideInFromRight;
import static com.kickstarter.libs.utils.TransitionUtils.transition;

public final class RewardViewHolder extends KSViewHolder {
  private final RewardViewModel viewModel;

  protected TextView allGoneTextView;
  protected TextView backersTextView;
  protected TextView descriptionTextView;
  protected View estimatedDeliveryDateSection;
  protected TextView estimatedDeliveryDateTextView;
  protected TextView limitAndBackersSeparatorTextView;
  protected TextView limitAndRemainingTextView;
  protected TextView minimumTextView;
  protected RecyclerView rewardsItemRecyclerView;
  protected View rewardsItemSection;
  protected View selectedHeader;
  protected View shippingSection;
  protected TextView shippingSummaryTextView;
  protected TextView titleTextView;
  protected CardView rewardView;
  protected TextView usdConversionTextView;
  protected View whiteOverlayView;

  protected int lightGreenColor;
  protected int whiteColor;

  protected String limitedRewardsRemainingString;
  protected String pledgeRewardCurrencyOrMoreString;
  protected String projectBackButtonString;
  protected String usdConversionString;

  private final KSString ksString;

  public RewardViewHolder(final @NonNull View view) {
    super(view);

    ksString = environment().ksString();
    viewModel = new RewardViewModel(environment());

    final RewardsItemAdapter rewardsItemAdapter = new RewardsItemAdapter();
    rewardsItemRecyclerView.setAdapter(rewardsItemAdapter);
    final LinearLayoutManager layoutManager = new LinearLayoutManager(context());
    rewardsItemRecyclerView.setLayoutManager(layoutManager);

    RxView.clicks(rewardView)
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(__ -> viewModel.inputs.rewardClicked());

    viewModel.outputs.allGoneTextViewIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(allGoneTextView));

    viewModel.outputs.backersTextViewIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(backersTextView));

    viewModel.outputs.backersTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(this::setBackersTextView);

    viewModel.outputs.descriptionTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(descriptionTextView::setText);

    viewModel.outputs.estimatedDeliveryDateSectionIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(estimatedDeliveryDateSection));

    viewModel.outputs.estimatedDeliveryDateTextViewText()
      .map(DateTimeUtils::estimatedDeliveryOn)
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(estimatedDeliveryDateTextView::setText);

    viewModel.outputs.goToCheckout()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(pr -> goToCheckout(pr.first, pr.second));

    viewModel.outputs.goToViewPledge()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(this::goToViewPledge);

    viewModel.outputs.isClickable()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(rewardView::setClickable);

    viewModel.outputs.limitAndBackersSeparatorIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(limitAndBackersSeparatorTextView));

    viewModel.outputs.limitAndRemainingTextViewIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(limitAndRemainingTextView));

    viewModel.outputs.limitAndRemainingTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(lr -> setLimitAndRemainingTextView(lr.first, lr.second));

    viewModel.outputs.minimumTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(this::setMinimumTextView);

    viewModel.outputs.rewardsItems()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(rewardsItemAdapter::rewardsItems);

    viewModel.outputs.rewardsItemsAreHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(rewardsItemSection));

    viewModel.outputs.selectedHeaderIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(selectedHeader));

    viewModel.outputs.selectedOverlayIsHidden()
      .map(hidden -> hidden ? whiteColor : lightGreenColor)
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(rewardView::setCardBackgroundColor);

    viewModel.outputs.shippingSummarySectionIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(shippingSection));

    viewModel.outputs.shippingSummaryTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(shippingSummaryTextView::setText);

    viewModel.outputs.titleTextViewIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(titleTextView));

    viewModel.outputs.titleTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(titleTextView::setText);

    viewModel.outputs.usdConversionTextViewIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setGone(usdConversionTextView));

    viewModel.outputs.usdConversionTextViewText()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(this::setUsdConversionTextView);

    viewModel.outputs.whiteOverlayIsHidden()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(ViewUtils.setInvisible(whiteOverlayView));
  }

  @Override
  @SuppressWarnings("unchecked")
  public void bindData(final @Nullable Object data) throws Exception {
    final Pair<Project, Reward> projectAndReward = requireNonNull((Pair<Project, Reward>) data);
    final Project project = requireNonNull(projectAndReward.first, Project.class);
    final Reward reward = requireNonNull(projectAndReward.second, Reward.class);

    viewModel.inputs.projectAndReward(project, reward);
  }

  private void goToCheckout(final @NonNull Project project, final @NonNull Reward reward) {
    final Context context = context();
    final Intent intent = new Intent(context, CheckoutActivity.class)
      .putExtra(IntentKey.PROJECT, project)
      .putExtra(IntentKey.TOOLBAR_TITLE, projectBackButtonString)
      .putExtra(IntentKey.URL, project.rewardSelectedUrl(reward));

    context.startActivity(intent);
    transition(context, slideInFromRight());
  }

  private void goToViewPledge(final @NonNull Project project) {
    final Context context = context();
    final Intent intent = new Intent(context, ViewPledgeActivity.class)
      .putExtra(IntentKey.PROJECT, project);

    context.startActivity(intent);
    transition(context, slideInFromRight());
  }

  private void setBackersTextView(final int count) {
    final String backersCountText = ksString.format("rewards_info_backer_count_backers", count,
      "backer_count", NumberUtils.format(count));
    backersTextView.setText(backersCountText);
  }

  private void setMinimumTextView(final @NonNull String minimum) {
    minimumTextView.setText(ksString.format(
      pledgeRewardCurrencyOrMoreString,
      "reward_currency", minimum
    ));
  }

  private void setLimitAndRemainingTextView(final @NonNull String limit, final @NonNull String remaining) {
    limitAndRemainingTextView.setText(ksString.format(
      limitedRewardsRemainingString,
      "rewards_remaining", remaining,
      "reward_limit", limit
    ));
  }

  private void setUsdConversionTextView(final @NonNull String amount) {
    usdConversionTextView.setText(ksString.format(
      usdConversionString,
      "reward_amount", amount
    ));
  }

  @Override
  protected void destroy() {
    rewardsItemRecyclerView.setAdapter(null);
  }
}
