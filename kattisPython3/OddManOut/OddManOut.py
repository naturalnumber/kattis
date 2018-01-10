#! /usr/bin/python3

import sys
import collections

n = None
i = 0
g = None

for line in sys.stdin:
  if n is None:
    n = int(line)
  elif g is None:
    g = int(line)
  else:
    guests = [int(x) for x in line.split()]
    counter = collections.Counter(guests)
    for key in counter:
      if counter[key] is 1:
        print("Case #"+str(i+1)+": "+str(key)); g = None;
        if i+1 is n:
          sys.exit(0)
        else:
          i = i+1
          continue