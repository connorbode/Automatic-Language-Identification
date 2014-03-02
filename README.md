Automatic-Language-Identification
=================================

Parses text and determines the language of the text based on bigrams.

## Project Description

This Java project was done for my Concordia Artificial Intelligence course with Dr. Leila Kosseim.  The following are the requirements:

- system must be able to read training corpora into bigrams
- system must be able to use bigrams to identify the language of a given sentence

## Specifics

The system..

- considers all words as lower case
- considers if words are at the beginning or end of a sentence
- treats newlines as periods
- treats question marks and exclamation marks as periods
- concatenates the two strings on either side of a single dash (e.g. "grown-ups" becomes "grownups")
- treats double-dashes as periods
- does not consider quotation marks or apostrophes
- treats colons as periods
- treats commas as breaks (e.g. "A, B C" will create a bigram (B,C) but not (A,B))
- assumes input will be devoid of diacritics
