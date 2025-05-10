while True:
    T = int(input())
    if T == 0:
        break
    camisetas = []
    for _ in range(3):
        Q, L = map(int, input().split())
        camisetas.append((Q, L))

    dp = [0] * (T + 1)

    for Q, L in camisetas:
        for t in range(Q, T + 1):
            dp[t] = max(dp[t], dp[t - Q] + L)

    print(dp[T])
