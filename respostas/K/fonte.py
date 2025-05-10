T = int(input())
V = int(input())
C = int(input())
M = int(input())

tempo_vale = 0 * V + 2 * T * C + 4 * T * M
tempo_colina = 2 * T * V + 0 * C + 2 * T * M
tempo_topo = 4 * T * V + 2 * T * C + 0 * M

print(min(tempo_vale, tempo_colina, tempo_topo))
