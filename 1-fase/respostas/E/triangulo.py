import math

while True:
    a, b, theta = map(float, input().split())
    if a == 0 and b == 0 and theta == 0:
        break
    pi = 3.14159265358979323846
    theta_rad = theta * (pi / 180)
    area = (a * b * math.sin(theta_rad)) / 2
    print(f"{area:.4f}")
