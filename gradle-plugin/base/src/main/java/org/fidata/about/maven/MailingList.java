package org.fidata.about.maven;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
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
  private final StringField subscribe;

  @Getter
  private final StringField unsubscribe;

  @Getter
  private final UrlField archiveUrl;

  @Getter
  private final StringField post;

  @Getter
  @Singular
  private final List<UrlField> otherArchives;

  protected static final class MailingListBuilderImpl extends MailingListBuilder<MailingList, MailingListBuilderImpl> {}
}
