#! /usr/bin/python3

import sys

for line in sys.stdin:
  n = int(line)
  if n is -1:
    sys.exit(0)
  d = 0
  last = 0
  for i in range (n):
    line = sys.stdin.readline()
    values = line.split()
    current = int(values[1])
    d = d + int(values[0]) * (current - last)
    last = current
  print (str(d)+" miles")
sys.exit(0)