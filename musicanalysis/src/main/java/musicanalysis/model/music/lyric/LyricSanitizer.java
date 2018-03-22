package musicanalysis.model.music.lyric;

import musicanalysis.model.data.Artist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class LyricSanitizer {

   private final String WS = "[ ]?";

   private LyricSanitizer() {

   }

   public static LyricSanitizer getInstance() {

      return LyricSanitizer.InstanceHolder.INSTANCE;
   }

   public String sanitize(final List<Artist> artists, final String title, final String lyric) {

      String sanitizedText = removeSuperfluousWhitespaces(lyric);
      sanitizedText = removeSpecialCharacters(sanitizedText);
      sanitizedText = removeChords(sanitizedText);
      sanitizedText = removeArtistNames(artists, sanitizedText);
      sanitizedText = removeRemarks(sanitizedText);
      sanitizedText = removeSongTitle(sanitizedText, title);
      sanitizedText = removeHyperlinks(sanitizedText);
      sanitizedText = reduplicateChorus(sanitizedText);
      sanitizedText = reduplicateSegment(sanitizedText);
      sanitizedText = reduplicateLine(sanitizedText);
      sanitizedText = removeSongStructureAnnotations(sanitizedText);
      sanitizedText = removeInstrumentAnnotations(sanitizedText);
      sanitizedText = removeAnnouncements(sanitizedText);
      sanitizedText = removeConsecutiveNewlines(sanitizedText);
      sanitizedText = sanitizedText.trim();

      return sanitizedText;
   }

   private String removeSuperfluousWhitespaces(final String text) {

      String sanitizedText = text;

      sanitizedText = sanitizedText.replaceAll("\\r?\\n", "\n");
      sanitizedText = sanitizedText.replaceAll("^\\n+", "");               // remove empty lines from the beginning
      sanitizedText = sanitizedText.replaceAll("\\n+$", "");               // remove empty lines from the end
      sanitizedText = sanitizedText.replaceAll("(?m)^[^\\S\\n]+", "");     // delete leading spaces
      sanitizedText = sanitizedText.replaceAll("(?m)[^\\S\\n]+$", "");     // delete trailing spaces
      sanitizedText = sanitizedText.replaceAll("[^\\S\\n]+", " ");         // delete consecutive spaces (exclude new lines)

      return sanitizedText;
   }

   private String removeSpecialCharacters(final String text) {

      String sanitizedText = text;

      sanitizedText = sanitizedText.replaceAll("\uFFFD", " ");    // �
      sanitizedText = sanitizedText.replaceAll("(?i)(&amp;quot;)", "\"");
      sanitizedText = sanitizedText.replace("“", "\"");
      sanitizedText = sanitizedText.replace("”", "\"");
      sanitizedText = sanitizedText.replace("’", "'");
      sanitizedText = sanitizedText.replace("‘", "'");
      sanitizedText = sanitizedText.replace("–", "-");
      sanitizedText = sanitizedText.replace("…", "...");
      sanitizedText = sanitizedText.replace("ﬁ", "fi");
      sanitizedText = sanitizedText.replaceAll("[^\\S\\n]*﻿[^\\S\\n]*", " ");
      sanitizedText = sanitizedText.replace("ṗ", "p");
      sanitizedText = sanitizedText.replace("â€~", "'");
      sanitizedText = sanitizedText.replaceAll(String.format("(?im)(^%1$s((\\.|-|\\*|_)%1$s)+$)", WS), "");

      return sanitizedText;
   }

   private String removeChords(String text) {

      final String chord = "(((([ABDEG]b?|[ACDFG]#?)((([Mm](aj|in)?|\\+|aug|dim|°)?6?)|(dom|ø?))?7?([ ]/)?)|(\\([x1-8]{5,6}\\))))";
      text = text.replaceAll(String.format("(?im)(^%1$s(intro)?%1$s:?(bflat)?%1$s%2$s(((?<=/)[ ]?%2$s)|([ ]%2$s))*\\n)", WS, chord), "");
      text = text.replaceAll(String.format("(?im)(^%1$sbflat%1$s\\n)", WS), "");
      return text;
   }

   private String removeSongTitle(final String text, final String title) {

      return text.replaceAll(String.format("(?im)(.*lyrics.*%s.*\\n)",
            Pattern.quote(title.trim()).replaceAll("\\s+", "\\\\E" + WS + "\\\\Q")), "");
   }

   private String removeArtistNames(final List<Artist> artists, String text) {

      final List<String> regexes1 = new ArrayList<>();   // replace with empty string
      final List<String> regexes2 = new ArrayList<>();   // replace with 'repeat' string

      for (final Artist artist : artists) {
         final String name = Pattern.quote(artist.getName().trim()).replaceAll("\\s+", "\\\\E" + WS + "\\\\Q");
         regexes1.add(String.format("(?im)(%1$s\\(%1$s%2$s%1$s\\)([:|-]%1$s)?)", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\[%1$s%2$s%1$s]([:|-]%1$s)?)", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\{%1$s%2$s%1$s}([:|-]%1$s)?)", WS, name));
         regexes1.add(String.format("(?im)(%1$s<%1$s%2$s%1$s>([:|-]%1$s)?)", WS, name));
         regexes1.add(String.format("(?im)(%1$s~%1$s%2$s%1$s~([:|-]%1$s)?)", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\*%1$s%2$s%1$s\\*([:|-]%1$s)?)", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\(%1$s%2$s%1$s([:|-]%1$s)?\\)%1$s)", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\[%1$s%2$s%1$s([:|-]%1$s)?]%1$s)", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\{%1$s%2$s%1$s([:|-]%1$s)?}%1$s)", WS, name));
         regexes1.add(String.format("(?im)(%1$s<%1$s%2$s%1$s([:|-]%1$s)?>%1$s)?", WS, name));
         regexes1.add(String.format("(?im)(%1$s~%1$s%2$s%1$s([:|-]%1$s)?~%1$s)?", WS, name));
         regexes1.add(String.format("(?im)(%1$s\\*%1$s%2$s%1$s([:|-]%1$s)?\\*%1$s)?", WS, name));
         regexes1.add(String.format("(?im)(^%1$s%2$s%1$s([:|-]%1$s)?$)", WS, name));
         regexes1.add(String.format("(?im)(^%1$s%2$s%1$s[:|-]%1$s)", WS, name));

         regexes2.add(String.format("(?im)((?<artist>(\\(%1$s%2$s%1$s\\)([:|-])?)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\[%1$s%2$s%1$s]([:|-])?)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\{%1$s%2$s%1$s}([:|-])?)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(<%1$s%2$s%1$s>([:|-])?)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(~%1$s%2$s%1$s~([:|-])?)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\*%1$s%2$s%1$s\\*([:|-])?)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\(%1$s%2$s%1$s([:|-]%1$s)?\\))).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\[%1$s%2$s%1$s([:|-]%1$s)?])).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\{%1$s%2$s%1$s([:|-]%1$s)?})).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(<%1$s%2$s%1$s([:|-]%1$s)?>)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(~%1$s%2$s%1$s([:|-]%1$s)?~)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(\\*%1$s%2$s%1$s([:|-]%1$s)?\\*)).*repeat.*)", WS, name));
         regexes2.add(String.format("(?im)((?<artist>(%1$s%2$s%1$s[:|-]?)).*repeat.*)", WS, name));
      }

      for (final String regex : regexes1) {
         text = text.replaceAll(regex, "");
      }

      final List<String> lines = new LinkedList<>();
      lines.addAll(Arrays.asList(text.split("\\n")));
      for (final String regex : regexes2) {
         for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            final Matcher matcher = Pattern.compile(regex).matcher(line);
            if (matcher.find()) {
               final String artist = matcher.group("artist");
               line = line.replace(artist, "");
               lines.set(i, line);
            }
         }
      }

      return lines.stream().map(String::trim).collect(joining(System.lineSeparator()));
   }

   private String removeRemarks(String text) {

      final List<String> regexes = new ArrayList<>();

      // match beginning of segments
      regexes.add(String.format("(?i)((^|\\n{2}).*%1$sinc%1$s\\..*%1$s(publishing|music)[^\\n]*)", WS));
      regexes.add(String.format("(?i)((^|\\n{2}).*%1$s(publishing|music)%1$s.*%1$sinc\\.[^\\n]*)", WS));
      regexes.add(String.format("(?i)((^|\\n{2})%1$stranscribed%1$sfrom[^\\n]*)", WS));
      regexes.add(String.format("(?i)((^|\\n{2})%1$s(written|words|lyrics|music|mixed|vocals|sung|spoken%1$swords)%1$sby[^\\n]*)", WS));
      regexes.add(String.format("(?i)((^|\\n{2})%1$s(copyright|©)[^\\n]*)", WS));
      regexes.add(String.format("(?i)((^|\\n{2})%1$sproducer[^\\n]*)", WS));
      regexes.add(String.format("(?i)((^|\\n{2})%1$stime%1$s(:%1$s)?\\d{1,2}%1$s:%1$s\\d{2}[^\\n]*)", WS));

      //match last line
      regexes.add(String.format("(?i)([\\n]+.*%1$sinc%1$s\\..*%1$s(publishing|music)[^\\n]*$)", WS));
      regexes.add(String.format("(?i)([\\n]+.*%1$s(publishing|music)%1$s.*%1$sinc\\.[^\\n]*$)", WS));
      regexes.add(String.format("(?i)([\\n]+%1$stranscribed%1$sfrom[^\\n]*$)", WS));
      regexes.add(String.format("(?i)([\\n]+%1$s(written|words|lyrics|music|mixed|vocals|sung|spoken%1$swords)%1$sby[^\\n]*$)", WS));
      regexes.add(String.format("(?i)([\\n]+%1$scopyright[^\\n]*$)", WS));
      regexes.add(String.format("(?i)([\\n]+%1$sproducer[^\\n]*$)", WS));
      regexes.add(String.format("(?i)([\\n]+%1$stime%1$s(:%1$s)?\\d{1,2}%1$s:%1$s\\d{2}[^\\n]*$)", WS));
      regexes.add(String.format("(?i)fade%1$sto%1$send\\s*$", WS));

      String old;
      do {
         old = text;
         for (final String regex : regexes) {
            text = text.replaceAll(regex, "\n\n").trim();
         }
      } while (!old.equals(text));

      text = text.replaceAll(String.format("(?i)(%1$s(\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*)%1$s)",
            WS, String.format("%1$s(%1$s\\*)*%1$s%2$s%1$s(\\*%1$s)*", WS, "(spoken|laughing|whispered|whistle|whistels)")), "");
      // remove "music fade" at end of each segment
      text = text.replaceAll(String.format("(?i)(\\n%1$smusic%1$sfade(:%1$s)?%1$s(\\n{2}|$))", WS), "\n\n");

      return text;
   }

   private String removeHyperlinks(String text) {

      return text.replaceAll("(?im)(^.*((https?://)|www\\.)[\\da-zA-Z-]+\\..*)", "");
   }

   private String removeSongStructureAnnotations(String text) {

      final String lyricAnnotations = String
            .format("(repeat|solo|verse|intro|chorus|refrain|outro|bridge|pre%s-?%1$schorus|interlude|end%1$schorus|end%1$srefrain)", WS);

      // "end of" at the beginning of a line and followed by the terms of above
      text = text.replaceAll(String.format("(?im)(^%1$send%1$sof%1$s%2$s%1$s:?%1$s)", WS, lyricAnnotations), "");
      // any of the above terms followed by a ":"
      text = text.replaceAll(String.format("(?im)^%1$s%2$s%1$s:%1$s", WS, lyricAnnotations), "");
      // any occurrence of the above terms furnished with additional decorations
      text = text.replaceAll(String.format("(?im)(%1$s(\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*)(%1$s:)?)",
            WS, String.format("%1$s(%1$s\\*)*%1$s%2$s%1$s((#?\\d+)|(%1$s\\w+%1$s))?%1$s(\\*%1$s)*", WS, lyricAnnotations)), "");

      return text;
   }

   private String removeInstrumentAnnotations(String text) {

      final String instruments = String.format("(instrument|instrumental|instrumental%1$sbreak|piano|violin)", WS);
      text = text.replaceAll(String.format("(?im)(%1$s(\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*)%1$s:?%1$s)",
            WS, String.format("%1$s(%1$s\\*)*%1$s%2$s%1$s(\\*%1$s)*", WS, instruments)), "");
      return text;
   }

   private String removeAnnouncements(String text) {

      // text between * *
      return text.replaceAll(String.format("(?im)(^%1$s\\*.+\\*%1$s$)", WS), "");
   }

   private String removeConsecutiveNewlines(final String text) {

      return text.replaceAll(String.format("(%s\\n){3,}", WS), "\n\n");
   }

   private String reduplicateSegment(final String text) {

      final List<String> lines = new LinkedList<>();
      lines.addAll(Arrays.asList(text.split("\\n")));

      final List<String> multireplacements = new ArrayList<>();
      final List<String> replacements = new ArrayList<>();
      replacements.add(String.format("%1$s(?<amount>(\\d{1,2}))%1$sx%1$s", WS));   // (repeat: 2x), [repeat, 2x] ...
      replacements.add(String.format("%1$sx%1$s(?<amount>(\\d{1,2}))%1$s", WS));   // (repeat: x2), [repeat, x2] ...
      Numbers2Int.getWords()
            .forEach(w -> replacements.add(String.format("%1$s(?<amount>(%2$s))%1$stimes%1$s", WS, w)));   // (repeat three times)
      replacements.add(String.format("%1$s(?<amount>(\\d{1,2}))%1$stimes%1$s", WS)); // repeat 3 times
      replacements.add(String.format("%1$s(?<amount>(once))%1$s", WS));
      replacements.add(String.format("%1$s(?<amount>(twice))%1$s", WS));
      replacements.add(String.format("%1$s(?<amount>(thrice))%1$s", WS));
      replacements.forEach(r -> {
         multireplacements.add(String.format("(?i)(^%1$s\\[(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*]%1$s(\\n|$))", WS, r));
         multireplacements.add(String.format("(?i)(^%1$s\\{(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*}%1$s(\\n|$))", WS, r));
         multireplacements.add(String.format("(?i)(^%1$s\\((%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*\\)%1$s(\\n|$))", WS, r));
         multireplacements.add(String.format("(?i)(^%1$s<(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*>%1$s(\\n|$))", WS, r));
         multireplacements.add(String.format("(?i)(^%1$s~(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*~%1$s(\\n|$))", WS, r));
         multireplacements.add(String.format("(?i)(^%1$s\\*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*\\*%1$s(\\n|$))", WS, r));
      });

      // repeat: x2 / 2x
      multireplacements.add(String.format("(?im)(^%1$s(repeat%1$s(:|,|-)?)?%1$s(?<amount>(\\d{1,2}))%1$sx%1$s(\\n|$))", WS));
      multireplacements.add(String.format("(?im)(^%1$s(repeat%1$s(:|,|-)?)?%1$sx%1$s(?<amount>(\\d{1,2}))%1$s(\\n|$))", WS));

      for (int i = 0; i < lines.size(); i++) {
         for (final String regex : multireplacements) {
            final Matcher matcher = Pattern.compile(regex).matcher(lines.get(i));
            if (matcher.find()) {
               final Integer amount = numberToInt(matcher.group("amount"));
               reduplicateSegment(lines, i, amount);
            }
         }
      }

      final String fade = String.format("(%1$srepeat(ed)?%1$s((till|to|and)%1$s(end|fade)(%1$s\\.)*)?%1$s(:%1$s)?%1$s)", WS);
      final String boxedFade = String.format("((%1$s\\*)*%2$s(\\*%1$s)*)", WS, fade);
      final List<String> singlereplacements = new ArrayList<>();
      singlereplacements.add(String.format("(?i)(^%1$s\\[%2$s]%1$s(\\n|$))", WS, boxedFade));
      singlereplacements.add(String.format("(?i)(^%1$s\\(%2$s\\)%1$s(\\n|$))", WS, boxedFade));
      singlereplacements.add(String.format("(?i)(^%1$s\\{%2$s}%1$s(\\n|$))", WS, boxedFade));
      singlereplacements.add(String.format("(?i)(^%1$s<%2$s>%1$s(\\n|$))", WS, boxedFade));
      singlereplacements.add(String.format("(?i)(^%1$s~%2$s~%1$s(\\n|$))", WS, boxedFade));
      singlereplacements.add(String.format("(?i)(^%1$s\\*%2$s*\\*%1$s(\\n|$))", WS, boxedFade));
      singlereplacements.add(String.format("(?i)(^%1$s(\\n|$))", fade));

      for (int i = 0; i < lines.size(); i++) {
         for (final String regex : singlereplacements) {
            final Matcher matcher = Pattern.compile(regex).matcher(lines.get(i));
            if (matcher.find()) {
               reduplicateSegment(lines, i, 2);    // int total we have two segments
            }
         }
      }

      return lines.stream().map(String::trim).collect(joining(System.lineSeparator()));
   }

   private void reduplicateSegment(List<String> lines, int index, Integer amount) {

      lines.remove(index);
      lines.add(index, System.lineSeparator());

      // we get the segment in reversed order
      final List<String> segment = findSegment(lines, index);
      for (int j = 0; j < amount - 1; j++) {
         for (final String line : segment) {
            lines.add(index, line + System.lineSeparator());
         }

         // do not make a new block for blocks with a single line
         if (segment.size() > 1) {
            lines.add(index, System.lineSeparator());
         }
      }
   }

   private List<String> findSegment(final List<String> lines, int searchIndex) {

      final List<String> segment = new ArrayList<>();

      final StringBuilder builder = new StringBuilder();
      for (int i = searchIndex - 1; i >= 0; i--) {
         builder.append(lines.get(i).trim()).append(System.lineSeparator());
      }

      final String regex = String.format("^(?<segment>(((?!\\n{2}).)+))", WS);
      final Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(builder.toString().trim());
      if (matcher.find()) {
         final String text = matcher.group("segment");
         segment.addAll(Arrays.asList(text.split("\\n")));
      }

      return segment;
   }

   private String reduplicateLine(final String text) {

      final List<String> lines = new LinkedList<>(Arrays.asList(text.split("\\n")));

      final List<String> multiReplacements = new ArrayList<>();
      final List<String> replacements = new ArrayList<>();
      replacements.add(String.format("%1$s(?<amount>(\\d{1,2}))%1$sx%1$s", WS));   // (repeat: 2x), [repeat, 2x] ...
      replacements.add(String.format("%1$sx%1$s(?<amount>(\\d{1,2}))%1$s", WS));   // (repeat: x2), [repeat, x2] ...
      Numbers2Int.getWords().forEach(w -> replacements.add(String.format("%1$s(?<amount>(%2$s))%1$s(more%1$s)?times%1$s", WS, w)));   // (repeat three more times)
      replacements.add(String.format("%1$s(?<amount>(\\d{1,2}))%1$s(more%1$s)?times%1$s", WS)); // repeat 3 more times
      replacements.add(String.format("%1$s(?<amount>(twice))%1$s", WS));
      replacements.add(String.format("%1$s(?<amount>(thrice))%1$s", WS));
      replacements.forEach(r -> {
         multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\[(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*]%1$s(\\n|$))", WS, r));
         multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\{(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*}%1$s(\\n|$))", WS, r));
         multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\((%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*\\)%1$s(\\n|$))", WS, r));
         multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s<(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*>%1$s(\\n|$))", WS, r));
         multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s~(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*~%1$s(\\n|$))", WS, r));
         multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\*(%1$s\\*)*(%1$srepeat%1$s(:|,|-)?)?%2$s(\\*%1$s)*\\*%1$s(\\n|$))", WS, r));
      });
      // repeat: x2 / 2x
      multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s(repeat%1$s(:|,|-)?)?%1$s(?<amount>(\\d{1,2}))%1$sx%1$s(\\n|$))", WS));
      multiReplacements.add(String.format("(?i)((?<line>(.+))%1$s(repeat%1$s(:|,|-)?)?%1$sx%1$s(?<amount>(\\d{1,2}))%1$s%1$s(\\n|$))", WS));

      for (int i = 0; i < lines.size(); i++) {
         for (final String regex : multiReplacements) {
            final Matcher matcher = Pattern.compile(regex).matcher(lines.get(i));
            if (matcher.find()) {
               final String line = matcher.group("line");
               final Integer amount = numberToInt(matcher.group("amount"));
               lines.remove(i);
               for (int j = 0; j < amount; j++) {
                  lines.add(i, line);
               }
            }
         }
      }

      // single replacements
      final String fades = String.format("((%1$s\\*)*%1$srepeat(ed)?%1$s((till|to|and)%1$s(end|fade)(%1$s\\.)*%1$s)?(:%1$s)?(\\*%1$s)*%1$s)", WS);
      final List<String> singleReplacements = new ArrayList<>();
      singleReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\[%2$s]%1$s(\\n|$))", WS, fades));
      singleReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\(%2$s\\)%1$s(\\n|$))", WS, fades));
      singleReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\{%2$s}%1$s(\\n|$))", WS, fades));
      singleReplacements.add(String.format("(?i)((?<line>(.+))%1$s<%2$s>%1$s(\\n|$))", WS, fades));
      singleReplacements.add(String.format("(?i)((?<line>(.+))%1$s~%2$s~%1$s(\\n|$))", WS, fades));
      singleReplacements.add(String.format("(?i)((?<line>(.+))%1$s\\*%2$s\\*%1$s(\\n|$))", WS, fades));

      for (int i = 0; i < lines.size(); i++) {
         for (final String regex : singleReplacements) {
            final Matcher matcher = Pattern.compile(regex).matcher(lines.get(i));
            if (matcher.find()) {
               final String line = matcher.group("line");
               lines.remove(i);
               lines.add(i, line);
               lines.add(i, line);
            }
         }
      }

      return lines.stream().map(String::trim).collect(joining(System.lineSeparator()));
   }

   private String reduplicateChorus(String text) {

      final String chorus = findChorus(text);

      if (chorus != null && !chorus.isEmpty()) {

         text = removeFirstChorusTag(text);
         text = reduplicateMultipleChoruses(text, chorus);
         text = reduplicateSingleChorus(text, chorus);
      }

      return text;
   }

   private String removeFirstChorusTag(String text) {

      // Attention: same regex in findChorus() required
      final String chorusI = String.format("(%1$s(chorus|refrain)%1$s:?%1$s)", WS);
      final String chorusII = String.format("(%1$s(chorus|refrain)%1$s)", WS);
      final String regex = String.format("(?i)" +
            "(%1$s((\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*|%2$s)|" +
            "((\\[%3$s]|\\(%3$s\\)|\\{%3$s}|<%3$s>|~%3$s~|\\*%3$s\\*|%3$s):?))" +
            "%1$s\\n+)", WS, chorusI, chorusII);

      return text.replaceFirst(regex, "\n");
   }

   private String findChorus(final String text) {

      String chorus = null;

      // Attention: same regex in removeFirstChorusTag() required
      final String chorusI = String.format("(%1$s(chorus|refrain)%1$s:?%1$s)", WS);
      final String chorusII = String.format("(%1$s(chorus|refrain)%1$s)", WS);
      final String regex = String.format("(?i)" +
            "(%1$s((\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*|%2$s)|" +
            "((\\[%3$s]|\\(%3$s\\)|\\{%3$s}|<%3$s>|~%3$s~|\\*%3$s\\*|%3$s):?))" +
            "%1$s\\n+(?<chorus>((?!((\\(|\\[|\\{|<|~)[ ]?)?(chorus|refrain)).+\\n)*))", WS, chorusI, chorusII);

      final String group = "chorus";
      // new line needed else regex does not match lookahead token
      final Matcher matcher = Pattern.compile(regex).matcher(text.concat(System.lineSeparator()));
      if (matcher.find()) {
         if (!matcher.group(group).trim().isEmpty()) {
            chorus = matcher.group(group).trim() + System.lineSeparator();
         }
      }

      return chorus;
   }

   private String reduplicateSingleChorus(String text, final String chorus) {

      final String fade = String.format("((%1$s(till|to|and)%1$s(end|fade)(%1$s\\.)*)?%1$s)", WS);
      final String repeat = String.format("(%1$s(repeat)?%2$s%1$s)", WS, fade);
      final String boxedRepeat = String.format("((%1$s\\*)*%2$s(\\*%1$s)*)", WS, repeat);

      // attention: regex ordering is important
      final List<String> regexParts = new ArrayList<>();
      regexParts.add(String.format("(%1$s(repeat%1$s)?(chorus|refrain)%1$s%2$s:?%1$s)", WS, fade));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(\\(%2$s\\))?:?%1$s)", WS, boxedRepeat));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(\\[%2$s])?:?%1$s)", WS, boxedRepeat));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(\\{%2$s})?:?%1$s)", WS, boxedRepeat));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(<%2$s>)?:?%1$s)", WS, boxedRepeat));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(~%2$s~)?:?%1$s)", WS, boxedRepeat));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(\\*%2$s\\*)?:?%1$s)", WS, boxedRepeat));
      regexParts.add(String.format("(%1$s(chorus|refrain)%1$s%2$s:?%1$s)", WS, repeat));

      final List<String> regexes = new ArrayList<>();
      regexParts.forEach(c -> regexes.add(String.format("(?i)(%1$s(\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*|%3$s)%1$s:?%1$s(\\n{2,}|$))",
            WS, String.format("(%1$s(%1$s\\*)*%2$s(\\*%1$s)*)", WS, c), c)));

      for (final String regex : regexes) {
         text = text.replaceAll(regex, Matcher.quoteReplacement(chorus) + "\n");
      }

      // remove single chorus tags - sometimes chorus is always tagged with [Chorus]
      regexes.clear();
      regexParts.forEach(c -> regexes.add(String.format("(?i)(%1$s(\\[%2$s]|\\(%2$s\\)|\\{%2$s}|<%2$s>|~%2$s~|\\*%2$s\\*|%3$s)%1$s:?%1$s(\\n{1}|$))",
            WS, String.format("(%1$s(%1$s\\*)*%2$s(\\*%1$s)*)", WS, c), c)));
      for (final String regex : regexes) {
         text = text.replaceAll(regex, "\n");
      }

      return text;
   }

   private String reduplicateMultipleChoruses(String text, final String chorus) {

      final List<String> amounts = new ArrayList<>();
      amounts.add(String.format("(((?<amount1>(\\d{1,2}))%1$sx)|((x|\\*)%1$s(?<amount2>(\\d{1,2})))%1$s)", WS));
      amounts.add(String.format("(%1$s\\(%1$s(((?<amount1>(\\d{1,2}))%1$sx)|((x|\\*)%1$s(?<amount2>(\\d{1,2}))))%1$s\\)%1$s)", WS));
      amounts.add(String.format("(((?<amount1>(\\d{1,2}))%1$stimes)|((times|\\*)%1$s(?<amount2>(\\d{1,2})))%1$s)", WS));
      amounts.add(String.format("(%1$s\\(%1$s(((?<amount1>(\\d{1,2}))%1$stimes)|((times|\\*)%1$s(?<amount2>(\\d{1,2}))))%1$s\\)%1$s)", WS));
      Numbers2Int.getWords().forEach(word -> amounts.add(String.format("(((?<amount1>(%2$s))%1$stimes)%1$s)", WS, word)));
      amounts.add(String.format("((?<amount1>(once))%1$s)", WS));
      amounts.add(String.format("((?<amount1>(twice))%1$s)", WS));
      amounts.add(String.format("((?<amount1>(thrice))%1$s)", WS));

      final String repeatChorus = String.format("%1$s(%1$s\\*)*%1$s(repeat%1$s)?(chorus|refrain)%1$s(\\*%1$s)*", WS);
      final List<String> choruses = new ArrayList<>();
      choruses.add(String.format("(\\[%2$s]%1$s(:|,|-)?%1$s", WS, repeatChorus));
      choruses.add(String.format("(\\(%2$s\\)%1$s(:|,|-)?%1$s", WS, repeatChorus));
      choruses.add(String.format("(\\{%2$s}%1$s(:|,|-)?%1$s", WS, repeatChorus));
      choruses.add(String.format("(<%2$s>%1$s(:|,|-)?%1$s", WS, repeatChorus));
      choruses.add(String.format("(\\*%2$s\\*%1$s(:|,|-)?%1$s", WS, repeatChorus));
      choruses.add(String.format("(~%2$s~%1$s(:|,|-)?%1$s", WS, repeatChorus));
      choruses.add(String.format("(%1$s(repeat%1$s)?(chorus|refrain)%1$s(:|,|-)?%1$s", WS));
      choruses.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$srepeat%1$s", WS));

      final List<String> regexParts = new ArrayList<>();
      choruses.forEach(c -> amounts.forEach(a -> {

         final String boxedAmount = String.format("%1$s(%1$s\\*)*%1$s%2$s%1$s(\\*%1$s)*", WS, a);

         // find chorus: e.g. [Chorus 2x], (Chorus: x3), *CHORUS* (3x), ...
         regexParts.add(String.format("%2$s\\[%3$s]%1$s)", WS, c, boxedAmount));
         regexParts.add(String.format("%2$s\\{%3$s}%1$s)", WS, c, boxedAmount));
         regexParts.add(String.format("%2$s\\(%3$s\\)%1$s)", WS, c, boxedAmount));
         regexParts.add(String.format("%2$s\\*%3$s\\*%1$s)", WS, c, boxedAmount));
         regexParts.add(String.format("%2$s~%3$s~%1$s)", WS, c, boxedAmount));
         regexParts.add(String.format("%2$s<%3$s>%1$s)", WS, c, boxedAmount));
         regexParts.add(String.format("%2$s%3$s%1$s)", WS, c, a));

         // find chorus: e.g. [2x Chorus], ...
         final String cPrime = c.replaceAll(String.format("%1$s(:|,|-)?%1$s$", WS), String.format("%1$s:?%1$s", WS));
         regexParts.add(String.format("\\[%3$s]%2$s%1$s)", WS, cPrime, boxedAmount));
         regexParts.add(String.format("\\{%3$s}%2$s%1$s)", WS, cPrime, boxedAmount));
         regexParts.add(String.format("\\(%3$s\\)%2$s%1$s)", WS, cPrime, boxedAmount));
         regexParts.add(String.format("\\*%3$s\\*%2$s%1$s)", WS, cPrime, boxedAmount));
         regexParts.add(String.format("<%3$s>%2$s%1$s)", WS, cPrime, boxedAmount));
         regexParts.add(String.format("~%3$s~%2$s%1$s)", WS, cPrime, boxedAmount));
         regexParts.add(String.format("%3$s%2$s%1$s)", WS, cPrime, a));
      }));

      amounts.forEach(a -> {
         final String boxedAmount = String.format("%1$s(%1$s\\*)*%1$srepeat%1$s%2$s%1$s(\\*%1$s)*", WS, a);
         regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$s\\(%2$s\\)%1$s)", WS, boxedAmount));
         regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$s\\[%2$s]%1$s)", WS, boxedAmount));
         regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$s\\{%2$s}%1$s)", WS, boxedAmount));
         regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$s\\*%2$s\\*%1$s)", WS, boxedAmount));
         regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$s<%2$s>%1$s)", WS, boxedAmount));
         regexParts.add(String.format("(%1$s(chorus|refrain)%1$s(:|,|-)?%1$s~%2$s~%1$s)", WS, boxedAmount));
      });

      final List<String> regexes = new ArrayList<>();
      regexParts.forEach(m -> {
         final String boxed = String.format("%1$s(%1$s\\*)*%1$s%2$s%1$s(\\*%1$s)*", WS, m);
         regexes.add(String.format("(?im)^(%1$s\\[%2$s]%1$s(\\n|$))", WS, boxed));
         regexes.add(String.format("(?im)^(%1$s\\(%2$s\\)%1$s(\\n|$))", WS, boxed));
         regexes.add(String.format("(?im)^(%1$s\\{%2$s}(\\n|$))", WS, boxed));
         regexes.add(String.format("(?im)^(%1$s<%2$s>%1$s(\\n|$))", WS, boxed));
         regexes.add(String.format("(?im)^(%1$s~%2$s~%1$s(\\n|$))", WS, boxed));
         regexes.add(String.format("(?im)^(%1$s\\*%2$s\\*%1$s(\\n|$))", WS, boxed));
         regexes.add(String.format("(?im)^(%1$s%2$s%1$s(\\n|$))", WS, m));
      });

      for (final String regex : regexes) {
         final Matcher matcher = Pattern.compile(regex).matcher(text);
         while (matcher.find()) {
            final String value = (matcher.group("amount1") != null ? matcher.group("amount1") : matcher.group("amount2"));
            final int reduplication = numberToInt(value);
            text = text.replaceAll(regex.replaceAll("\\(\\?<amount1>\\([^\\(\\)]*\\)\\)", value).replaceAll("\\(\\?<amount2>\\([^\\(\\)]*\\)\\)", value),
                  Matcher.quoteReplacement(reduplicateText(chorus, reduplication)));

         }
      }

      return text;
   }

   private String reduplicateText(final String text, int amount) {

      String replace = text + System.lineSeparator();

      for (int i = 1; i < amount; i++) {
         replace = replace.concat(text + System.lineSeparator());
      }

      return replace;
   }

   private int numberToInt(final String text) {

      int value;

      if (text.matches("\\d+")) {
         value = Integer.parseInt(text);
      } else {
         value = Numbers2Int.parse(text);
      }

      return value;
   }

   private static class InstanceHolder {

      static final LyricSanitizer INSTANCE = new LyricSanitizer();
   }
}
