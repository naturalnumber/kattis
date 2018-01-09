#! /usr/bin/python3

import sys

scores = {}
best = 0

for i in range(5):
  score = sum([int(x) for x in sys.stdin.readline().split()])
  scores[i+1] = score
  if best is 0 or score > scores[best]:
    best = i+1
print (str(best)+" "+str(scores[best]))
sys.exit(0)