package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "songtitles")
public class SongTitle extends LyricFeature {

   @Field(Fields.TITLE_APPEARS)
   private Boolean titleAppears;

   public Boolean isTitleAppearing() {

      return titleAppears;
   }

   public void setTitleAppears(Boolean titleAppears) {

      this.titleAppears = titleAppears;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.TITLE_APPEARS, titleAppears);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String TITLE_APPEARS = "title_appears";
      private Fields() {

      }
   }
}
