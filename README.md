# Data

The dataset can be found in the `data` folder. Thereby, each relevant collection of the MongoDB has been exported to a separate JSON file. For legal reasons, the lyrics itself have been removed and only the extracted features are exported.

# Code

The Java code in this repository contains only the most important classes out from a bigger project on music playlist analyses. Therefore, it is not runnable as is, but nevertheless provides insight into the construction of our dataset. The main reason is that the original code relies on a database (MongoDB) containing all lyrics, which we can not publish for legal reasons. Nevertheless, if one wants, for example, compute features of own lyrics, only minor changes have to be made, i.e., exchanging the data source from MnogoDB to whatever is needed (e.g., only a String containing the lyrics).

We are working on a standalone lyrics feature extractor, which provides a simple interface that does exactly that.


### Lyrics Features

The probably most interesting part is the computation of the various lyrics features, including lexical, syntactical, semantic and other features. The main class for this is `FeatureComputer` in the `musicanalysis.model.music.lyric.feature` package. As said, this class requires a MongoDB connection, which is not published at the moment. Nevertheless, have a look at the `compute(Lyric l, double duration)` method, which computes all features for the given `Lyric`. So, in order to compute features, you would simply have to create a `Lyric` object from your existing lyrics file.

The concrete feature extractors (lexical, linguistic, semantic, syntactic) can be found within the corresponding packages.


### Crawler

For this dataset, we crawled 10 different web pages. The code for that can be found in the package `musicanalysis.model.music.lyric.crawler`.


### Sanitizing Lyrics

As crawled lyrics have to be cleaned in most of the cases, we processed them by using the `LyricSanitizer` class in the `musicanalysis.model.music.lyric` package. Here you can find dozens of regexes to clean lyrics which aim to extract the real lyric and dismissing all non-relevant information like chords or other reading instructions (e.g., "repeat 2x").

# Citation
If you make use of our dataset, please cite our paper [ALF-200k: Towards Extensive Multimodal Analyses of Music Tracks and Playlists](https://link.springer.com/chapter/10.1007%2F978-3-319-76941-7_48) published at ECIR 2018: 
```
@inproceedings{DBLP:conf/ecir/zangerle17,
title = {ALF-200k: Towards Extensive Multimodal Analyses of Music Tracks and Playlists},
author = {Eva Zangerle and Michael Tschuggnall and Stefan Wurzinger and G\"{u}nther Specht},
doi = {10.1007/978-3-319-76941-7_48},
isbn = {978-3-319-76941-7},
year = {2018},
date = {2018-01-01},
booktitle = {Advances in Information Retrieval - 39th European Conference on IR Research, ECIR 2018},
pages = {584--590},
publisher = {Springer},
address = {Cham}
}

