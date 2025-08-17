d_e, y_e = map(int, input().split())
d_m, y_m = map(int, input().split())

target_e = (y_e - d_e) % y_e
target_m = (y_m - d_m) % y_m

for t in range(1, 5001):
    if t % y_e == target_e and t % y_m == target_m:
        print(t)
        break
