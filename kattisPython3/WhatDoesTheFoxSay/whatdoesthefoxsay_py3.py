#! /usr/bin/python3

import sys
 
n = None
sounds = None
ignore = []

for line in sys.stdin:
    if n is None:
      n = int(line)
    elif sounds is None:
      sounds = line.split();
    elif line.strip() == "what does the fox say?":
      says = ""
      for sound in [x for x in sounds if not x in ignore]:
        if not says is "":
            says = says + " "
        says = says + sound
      print (says)
      sounds = None
      ignore = []
    else:
      ignore.append(line.strip().split()[2])