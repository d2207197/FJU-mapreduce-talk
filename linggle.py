#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import unicode_literals, print_function
import fileinput
from itertools import groupby, imap


def skipngrams(words):
    for dist in range(1, 4+1):
        for first, last in zip(words, words[dist:]):
            yield first, last, dist
            yield last, first, -dist



from itertools import product, chain
def dash_ngrams(ngram):
    dash_ngrams = ((word, '_') for word in ngram)
    dash_ngrams = product(*dash_ngrams)
    dash_ngrams = (' '.join(dash_ngram) for dash_ngram in dash_ngrams 
                   if not all(word == '_' for word in dash_ngram))
    return dash_ngrams

def mapper(line):
    # from nltk.tokenize import word_tokenize
    # words = word_tokenize(line.lower())
    line = line.strip()
    ngram, count = line.split('\t',1)
    ngram = ngram.split()
    
    for dash_ngram in dash_ngrams(ngram):
        yield dash_ngram, line

        # import re
    # words = re.findall(r'[a-z]+', line.lower())
    # for base, coll, dist in skipngrams(words):
    #     yield base, '{} {} {}'.format(base, coll, dist)


def reducer(key, values):
    # count = sum(int(v) for v in values)
    
    yield key, ' ||| '.join(values)


def do_mapper(files):
    for line in fileinput.input(files):
        for key, value in mapper(line):
            yield key, value



def do_reducer(files):
    def line_to_keyvalue(line):
        key, value = line.decode('utf8').split('\t', 1)
        return key, value

    keyvalues = imap(line_to_keyvalue, fileinput.input(files))
    for key, grouped_keyvalues in groupby(keyvalues,
                                          key=lambda x: x[0]):
        values = (v for k, v in grouped_keyvalues)
        for key, value in reducer(key, values):
            yield key, value


def argparser():
    import argparse
    parser = argparse.ArgumentParser(description='N-gram counter')
    mode_group = parser.add_mutually_exclusive_group(required=True)
    mode_group.add_argument(
        '-r', '--reducer', action='store_true', help='reducer mode')
    mode_group.add_argument(
        '-m', '--mapper', action='store_true', help='mapper mode')
    parser.add_argument('files', metavar='FILE', type=str, nargs='*',
                        help='input files')
    return parser.parse_args()

if __name__ == '__main__':

    args = argparser()
    if args.mapper:
        mapper_out = do_mapper(args.files)
        for key, value in mapper_out:
            print('{}\t{}'.format(key, value))

    elif args.reducer:
        reducer_out = do_reducer(args.files)
        for key, value in reducer_out:
            print('{}\t{}'.format(key, value))
