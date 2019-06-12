// SPDX-Copyright: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.jvm.maven;

import static lombok.Builder.Default;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.AbstractFieldSet;
import org.fidata.about.model.StringField;
import org.fidata.about.model.UrlField;

@JsonDeserialize(builder = MailingList.MailingListBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MailingList extends AbstractFieldSet {
  public final StringField getSubscribe() {
    return getString("subscribe");
  }

  public final StringField getUnsubscribe() {
    return getString("unsubscribe");
  }

  public final UrlField getArchiveUrl() {
    return getUrl("archive");
  }

  public final StringField getPost() {
    return getString("post");
  }

  @Getter
  @Singular
  private final Set<UrlField> otherArchives;

  public static abstract class MailingListBuilder<C extends MailingList, B extends MailingListBuilder<C, B>> extends AbstractFieldSet.AbstractFieldSetBuilder<C, B> implements MailingListBuilderMeta {
  }

  private interface MailingListBuilderMeta {
    @JsonIgnore
    MailingListBuilder otherArchive(UrlField otherArchive);
  }

  protected static final class MailingListBuilderImpl extends MailingListBuilder<MailingList, MailingListBuilderImpl> {}
}
