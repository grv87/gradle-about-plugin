package org.fidata.about.maven;

import static lombok.Builder.Default;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableSet;
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
  @Getter
  @Default
  private final StringField subscribe = StringField.NULL;

  @Getter
  @Default
  private final StringField unsubscribe = StringField.NULL;

  @Getter
  @Default
  private final UrlField archiveUrl = UrlField.NULL;

  @Getter
  @Default
  private final StringField post = StringField.NULL;

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
