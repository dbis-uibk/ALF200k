
This file contains information about the content of the current data storage and describes how to use the developed software.

# 1. Content:

+ binary                        // contains the executable file
  + musicanalysis.jar
+ data                          // data sets
  + feature-analysis
    + feature-analysis.zip     // python code to detect features with an infromation gain above the mean and around the mean information gain
    + readme.txt               // notes the author of the python code - Dr. Eva Zangerle (collaborative work)
  + music
    + music-170731.tar.gz      // compressed mongoDB database 'music' containing playlists, tracks, lyrics, audio/lyrics features and evaluation results
    + readme.txt               // contains information about the collections contained in the 'music' database
  + spotifydataset              // Spotify datasets provided by Pichl et al.
    + echonestDataset.zip      // contains audio features for most tracks contained in the playlist corpus
    + playlistDataset.zip      // playlist corpus which is analyzed
    + readme.txt               // the file contains structural information about all Spotify datasets

+ source
  + fuzzy-search-tools          // library to calculate the Damerau-Levenstein metric (https://github.com/englianhu/fuzzy-search-tools)
  + musicanalysis               // source code of the own developed software
    + main
        + data                // necessary resources to compute lyrics features and conduct classification experiments
       + java                // source code
       + resources
          + application.properties     // define mongodb settings for the productive application; adjust settings here if you do not use default mongodb settings or run mongodb on another machine than the localhost
          + test.properties            // define mongodb settings for testing
    + test                     // code to test the entire application
    + libs                     // utilized libraries not offered by Maven
  + rhymeanalyzer               // RhymeAnalyzer tool developed by Hirjee and Brown (https://sourceforge.net/p/rhymeanalyzer/)
  + sentistrength               // SentiStrength tool developed by Thelwall (http://sentistrength.wlv.ac.uk/download.html)

# 2. How to use the software:

1. Install required software to run and modify the developed software:
    1.1 Database system: mongodb
           https://www.mongodb.com/
           Settings: - Host: localhost
                     - Port: 27017
           If you use other settings please adjust the values int the application.properties file (./source/musicanalysis/src/main/resources), rebuild the musicanalysis project and create a new .jar artifact.

    1.2 Install Java SDK
    http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

     1.3 Software project management tool Maven
           https://maven.apache.org/download.cgi
           All project dependencies are defined in a so-called POM file (Project Object Model) which are automatically downloaded/integrated by the Maven tool.

     1.4 Integrated Development Environment (Optional)
           e.g., IntelliJ IDEA (https://www.jetbrains.com/idea/)

2. Restore mongodb database "music"
     2.1 Uncompress archive /data/music/music-170731.tar.gz
     2.2 Restore the 'music' database
           e.g. for Linux, mongorestore --db music /path/to/the/uncompressed/folder/

3. Create necessary directories (export and resource directory)
     3.1 Create 'musicanalysis' directory in your home path
     3.2 Create 'export' directory in above created directory (/home/path/musicanalysis/export)
     3.3 Create 'resources' directory in the binary folder
     3.4 Copy all files from ./source/music/musicanalysis/data to the ./binary/resources directory
     3.5 Create 'spotifydataset' directory in the 'resource' directory (./binary/resources/spotifydataset)
     3.6 Unzip the files echonestDataset.zip and playlistDataset.zip from the 'spotifydataset' data sets directory (./data/spotifydataset) and copy the files to the newly created 'spotifydataset' resource directory.
    
     To debug the software copy the whole resources directory to the compile directory!
    If you want to store the created files in another directory please adjust the base path in the Settings.java file (./source/musicanalysis/src/main/java/settings)

4. Run the musicanalysis.jar executable to start the own developed console program (e.g. for Linux, java -jar ./binary/musicanalysis.jar)
     4.1 Command overview
           Execute the commands in the defined order to reproduce the evaluation results:
           1.  use playlist-importer
           2.  use track-requester
           3.  use lyrics-downloader
           4.  use lyrics-merger
           5.  use audio-features-importer
           6.  use lyrics-features-computer
           7.  use playlists-modifier
           8.  use test-cases-generator
           9.  use feature-analyzer
           10. use classifier
           11. use statistic-computer
           12. use evaluator

    Each of these commands activates a console view which can execute specific functions. Those are subsequently described in detail.
           Note that you can type always 'help' to see all available commands in the current context.

     4.2 use playlist-importer
           > import               // import playlists and users from the playlistDataset.csv file located in the 'spotifydataset' working directory
           > status               // print playlists and users import progress
           > cancel               // cancel playlists and users import progress
    
     4.3 use track-requester
           > request              // request track information from api.spotify.com
           > status               // print track request progress
           > cancel               // cancel track requesting

     4.4 use lyrics-downloader  // note that the structure of accessed websites are frequently updated which can cause a non-runnable crawler
           > crawl c:<ChartLyrics|ELyrics|LyricsMode|LyricsNMusic|LyricsWikia|MetroLyrics|MP3Lyrics|Sing365|SongLyrics|SongTexte>   // crawl lyrics from a specific website
           > status c:<ChartLyrics|ELyrics|LyricsMode|LyricsNMusic|LyricsWikia|MetroLyrics|MP3Lyrics|Sing365|SongLyrics|SongTexte>  // print the progress of a specific website crawler
           > cancel c:<ChartLyrics|ELyrics|LyricsMode|LyricsNMusic|LyricsWikia|MetroLyrics|MP3Lyrics|Sing365|SongLyrics|SongTexte>  // cancel a specific website crawler

     4.5 use lyrics-merger
           > merge                  // ascertain proper lyrics versions from the crawled lyrics websites; at least 3 out of 10 versions need to be similar (Jaccard similarity >= 0.6) to be picked; stores the merged lyrics in the 'lyrics' collection

     4.6 use audio-features-importer
           > import                 // import audio features from the echonestDataset.csv file located in the 'spotifydataset' working directory
           > status                 // print the audio features import progress
           > cancel                 // cancel audio features import progress

     4.7 use lyrics-features-computer
           > compute-lexical        // compute lexical features of the merged lyrics versions
           > compute-linguistic     // compute linguistic features of the merged lyrics versions
           > compute-syntactic      // compute syntactic features of the merged lyrics versions
           > compute-semantic       // compute semantic features of the merged lyrics versions
           > compute-all            // compute lexical, linguistic, syntactic and semantic features of the merged lyrics versions
           > status                 // print the progress of one of the above executed tasks
           > cancel                 // cancel one of the above executed tasks

     4.8 use playlists-modifier
           > modify                 // remove tracks from playlists without available audio and lyrics features; stores the modified playlists in the 'adaptedplaylists' collection
           > status                 // print the progress of the modification task
           > cancel                 // cancel the modification task

     4.9 use test-cases-generator
           > generate-test-cases    // create classification test cases; each playlist is doubled by adding random tracks which are not contained in the original playlist
           > status                 // print the progress of the test case generation task
           > cancel                 // cancel the test case generation task

     4.10 feature-analyzer
            > analysis              // compute the information gain of each attribute based on the created test cases
            > status                // print the progress of the analysis task
            > cancel                // cancel the analysis task

     4.11 use classifier                      // distinguish if a track fits into a playlist or not; classification on a per-playlist basis
                                                // ATTENTION: classification results are stored in the 'classificationresults' collection
                                                //            rename an existing 'classificationresults' collection or drop it before starting a new classification run
            > audio-only                        // classify tracks using audio features only
            > lexical-only                      // classify tracks using lexical lyrics features only
            > linguistic-only                   // classify tracks using linguistic lyrics features only
            > syntactic-only                    // classify tracks using syntactic lyrics features only
            > semantic-only                     // classify tracks using semantic lyrics features only
            > lyrics-only                       // classify tracks using lexical, linguistic, semantic and syntactic lyrics features only
            > all-features                      // classify tracks using all lyrics and audio features
            > cross-analysis                    // classify tracks using all feature subset combinations; in total 31 classification tasks for each playlist
            > cross-analysis-feature-selection-top-100      // classify tracks using all feature subset combinations and feature subset selection; in total 31 classification tasks for each playlist; top 100 features are considered for each feature subset combination, if less than 100 features are available 80% of all features are chosen
            > cross-analysis-feature-selection-around-mean  // classify tracks using all feature subset combinations and feature subset selection; in total 31 classification tasks for each playlist; use top features around the information gain mean
                                                            // execute this command to verify if feature selection around the mean information gain provides inferior classification results than the top 100 features;classification results are currently not stored in the database
            > cross-analysis-feature-selection-above-mean   // classify tracks using all feature subset combinations and feature subset selection; in total 31 classification tasks for each playlist; use top features above the information gain mean
                                                            // execute this command to verify if feature selection above the mean information gain provides inferior classification results than the top 100 features;classification results are currently not stored in the database
            > status                            // print the progress of one of the above tasks
            > cancel                            // cancel one of the above executed tasks

     4.12 use statistic-computer              // print track, lyrics and playlist statistics
            > t-total-count                     // print total track count
            > t-unique-count                    // print unique track count based on the Spotify track ID
            > t-count-by-artist-title           // count tracks by artist and title
            > t-group-by-artist-title           // group tracks by artist and title; creates the file grouped-tracks.txt in the export folder; aritst with similar artist and track are grouped together delimited by two new lines;
            > l-total-count s:<lyrics|chartlyrics|elyrics|lyricsmode|lyricsnmusic|lyricswikia|metrolyrics|mp3lyrics|sing365|songlyrics|songtexte>   // count lyrics by source
            > l-count-en s:<lyrics|chartlyrics|elyrics|lyricsmode|lyricsnmusic|lyricswikia|metrolyrics|mp3lyrics|sing365|songlyrics|songtexte>      // count English lyrics by source
            > l-count-by-artist-title           // count lyrics by artist and title
            > l-lang-dist m:<text|chart> s:<lyrics|chartlyrics|elyrics|lyricsmode|lyricsnmusic|lyricswikia|metrolyrics|mp3lyrics|sing365|songlyrics|songtexte> // print language distribution as text or as chart
            > p-dist [m:latex]                  // print playlist size distribution; optional formatted as latex code
            > p-adapted-dist [m:latex]          // print distribution of modified playlists; optional formatted as latex code
            > p-dist-chart                      // create playlist size distribution chart
            > p-adapted-dist-chart              // create distribution chart of modified playlists (tracks without audio/lyrics features are removed from the original playlists)

     4.13 use evaluator                       // accesses the 'classificationresults' mongodb collection;if you want to evaluate other classification result collections rename them to 'classificationresults'
            > pm-xy-plot m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>] [c:<BayesNet,J48,kNN,LibLinear,LibSVM_C,LibSVM_nu,NaiveBayes,PART>    // xy-plot; one plot for each classifier and config; prints the average measure (F1, Recall, Precision, Accuracy) per playlist size (amount of tracks);optional: define min/max playlist size and classifier type (c)
            > pm-top-xy-plot t:<amount> m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>]    // xy-plot; one plot for each classifier; prints the average measure (F1, Recall, Precision, Accuracy) per playlist size (amount of tracks) but only for the top (t) scoring configs (highest average measures of all classified playlists);one line for each config;optional: define min/max playlist size
            > pm-histo m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>]                     // histogram; average measure (m) per playlist size; one plot for each config; up to 20 bins of playlist sizes; one bar for each classifier;optional: define min/max instance size
            > mf-histo m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>]                     // histogram; frequencies of obtained measures (m); one plot for each config; one bar for each classifier; up to 20 bins of measures; prints the amount of obtained measures per classifier;optional: define min/max playlist size
            > cm-histo m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>] [t:latex]           // histogram; average measures (m) per config histogram; no binning; one plot for each classifier; one bar for each config;optional: define min/max playlist size; optional parameter t defines the output mode: chart (default) or latex code
            > cmo-histo m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>]                    // histogram; one single overview plot; average measures per config; one bar for each classifier;optional: define min/max playlist size
            > pm-cumulative-xy-plot m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>]        // xy-plot;average measures (m) plot per playlist size (cumulative, avg measures are summed up from 0 to all other playlist sizes); one plot per classifier and config; optional: define min/max playlist size
            > im-xy-plot m:<F1|Precision|Recall|Accuracy> [min:<value>] [max:<value>] [t:latex]         // xy-plot;average measures per playlist size over all configs; one line per classifier; optional: define min/max playlist size; optional parameter t defines the output mode: chart (default) or latex code
