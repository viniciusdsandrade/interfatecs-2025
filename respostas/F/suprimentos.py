n = int(input())
curr = 0
min_curr = 0

for _ in range(n):
    t = int(input())
    curr += t
    min_curr = min(min_curr, curr)

print(-min_curr)
