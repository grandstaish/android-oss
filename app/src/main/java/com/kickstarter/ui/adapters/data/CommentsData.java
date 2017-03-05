package com.kickstarter.ui.adapters.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kickstarter.models.Comment;
import com.kickstarter.models.Project;
import com.kickstarter.models.User;

import java.util.List;

public abstract class CommentsData {
  public abstract @NonNull Project project();
  public abstract @Nullable List<Comment> comments();
  public abstract @Nullable User user();

  public abstract static class Builder {
    public abstract Builder project(Project __);
    public abstract Builder comments(List<Comment> __);
    public abstract Builder user(User __);
    public abstract CommentsData build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();

  public static @NonNull CommentsData deriveData(final @NonNull Project project,
    final @Nullable List<Comment> comments, final @Nullable User user) {

    return CommentsData.builder()
      .project(project)
      .comments(comments)
      .user(user)
      .build();
  }
}
