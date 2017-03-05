package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.KSCurrency;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.libs.utils.I18nUtils;
import com.kickstarter.libs.utils.NumberUtils;
import com.kickstarter.libs.utils.ProgressBarUtils;
import com.kickstarter.libs.utils.ProjectUtils;
import com.kickstarter.libs.utils.SocialUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.models.Category;
import com.kickstarter.models.Location;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.kickstarter.ui.IntentKey;
import com.kickstarter.ui.activities.ProjectSocialActivity;
import com.kickstarter.ui.views.IconButton;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.math.RoundingMode;

import javax.inject.Inject;

import static com.kickstarter.libs.utils.DateTimeUtils.mediumDate;
import static com.kickstarter.libs.utils.DateTimeUtils.mediumDateShortTime;
import static com.kickstarter.libs.utils.ObjectUtils.coalesce;
import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;
import static com.kickstarter.libs.utils.ViewUtils.getScreenDensity;
import static com.kickstarter.libs.utils.ViewUtils.getScreenHeightDp;
import static com.kickstarter.libs.utils.ViewUtils.getScreenWidthDp;

public final class ProjectViewHolder extends KSViewHolder {
  private Project project;
  private String configCountry;
  private Context context;
  private final Delegate delegate;

  protected ImageView avatarImageView;
  protected TextView avatarNameTextView;
  protected TextView backersCountTextView;
  protected LinearLayout backerLabelLinearLayout;
  protected @Nullable Button backProjectButton;
  protected TextView blurbTextView;
  protected TextView categoryTextView;
  protected TextView commentsCountTextView;
  protected TextView creatorNameTextView;
  protected TextView deadlineCountdownTextView;
  protected TextView deadlineCountdownUnitTextView;
  protected TextView projectDisclaimerTextView;
  protected TextView goalTextView;
  protected @Nullable ViewGroup landOverlayTextViewGroup;
  protected TextView locationTextView;
  protected @Nullable Button managePledgeButton;
  protected @Nullable ViewGroup nameCreatorViewGroup;
  protected ProgressBar percentageFundedProgressBar;
  protected ImageView photoImageView;
  protected IconButton playButton;
  protected TextView pledgedTextView;
  protected TextView projectNameTextView;
  protected ImageView projectSocialImageView;
  protected TextView projectSocialTextView;
  protected ViewGroup projectStatsViewGroup;
  protected ViewGroup projectSocialViewGroup;
  protected TextView projectStateHeaderTextView;
  protected TextView projectStateSubheadTextView;
  protected ViewGroup projectStateViewGroup;
  protected @Nullable Button viewPledgeButton;
  protected TextView updatesCountTextView;
  protected TextView usdConversionTextView;

  protected int greenAlpha50Color;
  protected int mediumGrayColor;

  protected int grid1Dimen;
  protected int grid2Dimen;
  protected int grid3Dimen;
  protected int grid4Dimen;

  protected Drawable clickIndicatorLightMaskedDrawable;
  protected Drawable grayGradientDrawable;

  protected String byCreatorString;
  protected String blurbReadMoreString;
  protected String convertedFromString;
  protected String projectDisclaimerGoalNotReachedString;
  protected String projectDisclaimerGoalReachedString;
  protected String fundingCanceledString;
  protected String fundingCanceledByCreatorString;
  protected String successfullyFundedOnDeadlineString;
  protected String fundingSuspendedString;
  protected String fundingProjectSuspendedString;
  protected String fundingUnsuccessfulString;
  protected String fundingGoalNotReachedString;
  protected String fundedString;
  protected String pledgedOfGoalString;
  protected String ofGoalString;
  protected String backersString;

  protected @Inject KSCurrency ksCurrency;
  protected @Inject KSString ksString;

  public interface Delegate {
    void projectViewHolderBackProjectClicked(ProjectViewHolder viewHolder);
    void projectViewHolderBlurbClicked(ProjectViewHolder viewHolder);
    void projectViewHolderCommentsClicked(ProjectViewHolder viewHolder);
    void projectViewHolderCreatorClicked(ProjectViewHolder viewHolder);
    void projectViewHolderManagePledgeClicked(ProjectViewHolder viewHolder);
    void projectViewHolderUpdatesClicked(ProjectViewHolder viewHolder);
    void projectViewHolderVideoStarted(ProjectViewHolder viewHolder);
    void projectViewHolderViewPledgeClicked(ProjectViewHolder viewHolder);
  }

  public ProjectViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    this.context = view.getContext();

    ((KSApplication) context.getApplicationContext()).component().inject(this);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    @SuppressWarnings("unchecked")
    final Pair<Project, String> projectAndCountry = requireNonNull((Pair<Project, String>) data);
    project = requireNonNull(projectAndCountry.first, Project.class);
    configCountry = requireNonNull(projectAndCountry.second, String.class);
  }

  public void onBind() {
    final Photo photo = project.photo();
    if (photo != null) {
      final int targetImageWidth = (int) (getScreenWidthDp(context) * getScreenDensity(context));
      final int targetImageHeight = ProjectUtils.photoHeightFromWidthRatio(targetImageWidth);
      photoImageView.setMaxHeight(targetImageHeight);

      Picasso.with(context)
        .load(photo.full())
        .resize(targetImageWidth, targetImageHeight)
        .centerCrop()
        .placeholder(grayGradientDrawable)
        .into(photoImageView);
    }

    if (project.hasVideo()) {
      playButton.setVisibility(View.VISIBLE);
    } else {
      playButton.setVisibility(View.GONE);
    }

    /* Project */
    blurbTextView.setText(Html.fromHtml(ksString.format(blurbReadMoreString,
      "blurb", TextUtils.htmlEncode(project.blurb()),
      "space", "\u00A0"
    )));
    creatorNameTextView.setText(Html.fromHtml(ksString.format(byCreatorString,
      "creator_name", TextUtils.htmlEncode(project.creator().name()))));
    if (project.isBacking()) {
      backerLabelLinearLayout.setVisibility(View.VISIBLE);
    } else {
      backerLabelLinearLayout.setVisibility(View.GONE);
    }
    projectNameTextView.setText(project.name());
    final Category category = project.category();
    if (category != null) {
      categoryTextView.setText(category.name());
    }
    final Location location = project.location();
    if (location != null) {
      locationTextView.setText(location.displayableName());
    }
    percentageFundedProgressBar.setProgress(ProgressBarUtils.progress(project.percentageFunded()));
    deadlineCountdownTextView.setText(NumberUtils.format(ProjectUtils.deadlineCountdownValue(project)));
    deadlineCountdownUnitTextView.setText(ProjectUtils.deadlineCountdownDetail(project, context, ksString));
    backersCountTextView.setText(NumberUtils.format(project.backersCount()));

    /* Creator */
    Picasso.with(context).load(project.creator().avatar()
      .medium())
      .transform(new CircleTransformation())
      .into(avatarImageView);
    avatarNameTextView.setText(project.creator().name());
    final Integer updatesCount = project.updatesCount();
    updatesCountTextView.setText(updatesCount != null ? NumberUtils.format(updatesCount) : null);
    final Integer commentsCount = project.commentsCount();
    commentsCountTextView.setText(commentsCount != null ? NumberUtils.format(commentsCount) : null);

    setConvertedUsdView();
    setLandscapeActionButton();
    setLandscapeOverlayText();
    setPledgedOfGoalView();
    setProjectDisclaimerView();
    setProjectSocialClick();
    setProjectStateView();
    setSocialView();
    setStatsContentDescription();
  }

  @Nullable
  public void backProjectButtonOnClick() {
    delegate.projectViewHolderBackProjectClicked(this);
  }

  public void blurbClick() {
    delegate.projectViewHolderBlurbClicked(this);
  }

  public void commentsClick() {
    delegate.projectViewHolderCommentsClicked(this);
  }

  public void creatorNameClick() {
    delegate.projectViewHolderCreatorClicked(this);
  }

  @Nullable
  public void managePledgeOnClick() {
    delegate.projectViewHolderManagePledgeClicked(this);
  }

  public void playButtonClick() {
    delegate.projectViewHolderVideoStarted(this);
  }

  @Nullable
  public void viewPledgeOnClick() {
    delegate.projectViewHolderViewPledgeClicked(this);
  }

  public void setConvertedUsdView() {
    if (I18nUtils.isCountryUS(configCountry) && !I18nUtils.isCountryUS(project.country())) {
      usdConversionTextView.setVisibility(View.VISIBLE);
      usdConversionTextView.setText(ksString.format(
        convertedFromString,
        "pledged",
        ksCurrency.format(project.pledged(), project),
        "goal",
        ksCurrency.format(project.goal(), project)
      ));
    } else {
      usdConversionTextView.setVisibility(View.GONE);
    }
  }

  /**
   * Set landscape project action buttons in the ViewHolder rather than Activity.
   */
  public void setLandscapeActionButton() {
    if (backProjectButton != null && managePledgeButton != null && viewPledgeButton != null) {
      ProjectUtils.setActionButton(project, backProjectButton, managePledgeButton, viewPledgeButton);
    }
  }

  /**
   * Set top margin of overlay text based on landscape screen height, scaled by screen density.
   */
  public void setLandscapeOverlayText() {
    if (landOverlayTextViewGroup != null && nameCreatorViewGroup != null) {
      final int screenHeight = getScreenHeightDp(context);
      final float densityOffset = context.getResources().getDisplayMetrics().density;
      final float topMargin = ((screenHeight / 3 * 2) * densityOffset) - grid4Dimen;  // offset for toolbar
      ViewUtils.setRelativeViewGroupMargins(landOverlayTextViewGroup, grid4Dimen, (int) topMargin, grid4Dimen, 0);

      if (!project.hasVideo()) {
        ViewUtils.setRelativeViewGroupMargins(nameCreatorViewGroup, 0, 0, 0, grid2Dimen);
      } else {
        ViewUtils.setRelativeViewGroupMargins(nameCreatorViewGroup, 0, 0, 0, grid1Dimen);
      }
    }
  }

  public void setPledgedOfGoalView() {
    pledgedTextView.setText(ksCurrency.format(project.pledged(), project, false, true, RoundingMode.DOWN));

    /* a11y */
    final String goalString = ksCurrency.format(project.goal(), project, false, true, RoundingMode.DOWN);
    final String goalText = ViewUtils.isFontScaleLarge(context) ?
      ksString.format(ofGoalString, "goal", goalString) : ksString.format(pledgedOfGoalString, "goal", goalString);
    goalTextView.setText(goalText);
  }

  public void setProjectDisclaimerView() {
    final DateTime deadline = project.deadline();

    if (deadline == null) {
      projectDisclaimerTextView.setVisibility(View.GONE);
    } else if (!project.isLive()) {
      projectDisclaimerTextView.setVisibility(View.GONE);
    } else if (project.isFunded()) {
      projectDisclaimerTextView.setVisibility(View.VISIBLE);
      projectDisclaimerTextView.setText(ksString.format(
        projectDisclaimerGoalReachedString,
        "deadline",
        mediumDateShortTime(deadline)
      ));
    } else {
      projectDisclaimerTextView.setVisibility(View.VISIBLE);
      projectDisclaimerTextView.setText(ksString.format(
        projectDisclaimerGoalNotReachedString,
        "goal_currency",
        ksCurrency.format(project.goal(), project, true),
        "deadline",
        mediumDateShortTime(deadline)
      ));
    }
  }

  public void setProjectSocialClick() {
    if (project.isFriendBacking()) {
      if (project.friends().size() > 2) {
        projectSocialViewGroup.setBackground(clickIndicatorLightMaskedDrawable);
        projectSocialViewGroup.setOnClickListener(view -> {
          final BaseActivity activity = (BaseActivity) context;
          final Intent intent = new Intent(activity, ProjectSocialActivity.class)
            .putExtra(IntentKey.PROJECT, project);
          activity.startActivity(intent);
          activity.overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out_slide_out_left);
        });
      }
    }
  }

  public void setProjectStateView() {
    final DateTime stateChangedAt = coalesce(project.stateChangedAt(), new DateTime());

    switch(project.state()) {
      case Project.STATE_SUCCESSFUL:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        projectStateViewGroup.setBackgroundColor(greenAlpha50Color);

        projectStateHeaderTextView.setText(fundedString);
        projectStateSubheadTextView.setText(ksString.format(successfullyFundedOnDeadlineString,
          "deadline", mediumDate(stateChangedAt)
        ));
        break;
      case Project.STATE_CANCELED:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        projectStateViewGroup.setBackgroundColor(mediumGrayColor);

        projectStateHeaderTextView.setText(fundingCanceledString);
        projectStateSubheadTextView.setText(fundingCanceledByCreatorString);
        break;
      case Project.STATE_FAILED:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        projectStateViewGroup.setBackgroundColor(mediumGrayColor);

        projectStateHeaderTextView.setText(fundingUnsuccessfulString);
        projectStateSubheadTextView.setText(ksString.format(fundingGoalNotReachedString,
          "deadline", mediumDate(stateChangedAt)
        ));
        break;
      case Project.STATE_SUSPENDED:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        projectStateViewGroup.setBackgroundColor(mediumGrayColor);

        projectStateHeaderTextView.setText(fundingSuspendedString);
        projectStateSubheadTextView.setText(fundingProjectSuspendedString);
        break;
      default:
        percentageFundedProgressBar.setVisibility(View.VISIBLE);
        projectStateViewGroup.setVisibility(View.GONE);
        break;
    }
  }

  public void setSocialView() {
    if (project.isFriendBacking()) {
      projectSocialViewGroup.setVisibility(View.VISIBLE);
      ViewUtils.setLinearViewGroupMargins(projectStatsViewGroup, 0, grid3Dimen, 0, grid2Dimen);

      projectSocialImageView.setVisibility(View.VISIBLE);
      Picasso.with(context).load(project.friends().get(0).avatar()
        .small())
        .transform(new CircleTransformation())
        .into(projectSocialImageView);

      projectSocialTextView.setText(SocialUtils.projectCardFriendNamepile(project.friends(), ksString));

    } else {
      projectSocialViewGroup.setVisibility(View.GONE);
      ViewUtils.setLinearViewGroupMargins(projectStatsViewGroup, 0, grid3Dimen, 0, grid4Dimen);
    }
  }

  public void setStatsContentDescription() {
    final String backersCountContentDescription = NumberUtils.format(project.backersCount()) + " " +  backersString;
    final String pledgedContentDescription = pledgedTextView.getText() + " " + goalTextView.getText();
    final String deadlineCountdownContentDescription = deadlineCountdownTextView.getText() + " " + deadlineCountdownUnitTextView.getText();

    backersCountTextView.setContentDescription(backersCountContentDescription);
    pledgedTextView.setContentDescription(pledgedContentDescription);
    deadlineCountdownTextView.setContentDescription(deadlineCountdownContentDescription);
  }

  public void updatesClick() {
    delegate.projectViewHolderUpdatesClicked(this);
  }
}
