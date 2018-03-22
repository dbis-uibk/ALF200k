package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.common.Path;
import musicanalysis.model.settings.Settings;

class SentimentStrengthTool {

    private uk.ac.wlv.sentistrength.SentiStrength sentiStrengthTool;

    private SentimentStrengthTool() {

        try {
            sentiStrengthTool = new uk.ac.wlv.sentistrength.SentiStrength();
            final String params[] = {"sentidata", Path.getInstance().getResourcePath(Settings.SENTI_STRENGTH_DATA_PATH) + "/", "trinary"};
            sentiStrengthTool.initialise(params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String[] computeSentimentScores(final String sentence) {

        return sentiStrengthTool.computeSentimentScores(sentence).split(" ");
    }

    public static SentimentStrengthTool getInstance() {

        return SentimentStrengthTool.InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {

        static final SentimentStrengthTool INSTANCE = new SentimentStrengthTool();
    }
}
