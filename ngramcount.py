#!/usr/bin/env python
# -*- coding: utf-8 -*-


def ngrams(words):
    for length in range(1, 5 + 1):
        for ngram in zip(*(words[i:] for i in range(length))):
            yield ngram


def mapper(files):
    import fileinput
    from nltk.tokenize import word_tokenize
    from collections import Counter
    ngram_counter = Counter()
    for line in fileinput.input(files):
        line = line.decode('iso-8859-1')
        words = word_tokenize(line.lower())
        ngram_counter.update(ngrams(words))

    for ngram, count in ngram_counter.iteritems():
        print (u' '.join(ngram) + u'\t' + unicode(count)).encode('utf-8')


def line_to_ngram(line):
    line = line.decode('iso-8859-1')
    return line.split(u'\t', 1)[0]


def line_to_count(line):
    line = line.decode('iso-8859-1')
    return int(line.split(u'\t', 1)[1])


def reducer(files):
    import fileinput
    from itertools import groupby, imap

    for ngram, lines in groupby(fileinput.input(files), key=line_to_ngram):
        count = sum(imap(line_to_count, lines))
        print (ngram + u'\t' + unicode(count)).encode('utf-8')


if __name__ == '__main__':
    import argparse
    import sys
    parser = argparse.ArgumentParser(description='N-gram counter')
    parser.add_argument(
        '-r', '--reducer', action='store_true', help='reducer mode')
    parser.add_argument(
        '-m', '--mapper', action='store_true', help='mapper mode')
    parser.add_argument('files', metavar='FILE', type=str, nargs='*',
                        help='input files')

    args = parser.parse_args()

    if (args.mapper and args.reducer
            or
            not args.mapper and not args.reducer):
        parser.print_help()
        sys.exit(1)

    if args.mapper:
        mapper(args.files)
    elif args.reducer:
        reducer(args.files)
