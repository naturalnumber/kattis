#! /usr/bin/python3

import sys

dominant = {'A' : 11, 'K' : 4, 'Q' : 3, 'J' : 20, 'T' : 10, '9' : 14}
subordinate = {'A' : 11, 'K' : 4, 'Q' : 3, 'J' : 2, 'T' : 10}

n = None
b = None
hand = []
score = 0

for line in sys.stdin:
  if n is None:
    temp = line.split()
    n = int(temp[0])
    b = str(temp[1])
  else:
    hand.append(line)
    if line[1] is b:
      score = score + dominant.get(line[0], 0)
    else:
      score = score + subordinate.get(line[0], 0)
    #if len(hand) is 4*n:
print (int(score))