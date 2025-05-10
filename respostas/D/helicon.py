# Leitura da entrada
N = int(input())
A = list(map(int, input().split()))

# Caso especial: somente um elemento
if N == 1:
    print(A[0])
    exit()

# Variável que guardará o maior somatório encontrado
max_sum = A[0]

# Para cada passo k de 1 até floor(N/2)
for k in range(1, N // 2 + 1):
    # Cada uma das k "rotas" começando em r = 0, 1, ..., k-1
    for r in range(k):
        current = 0
        best = float('-inf')
        # Percorre a subsequência A[r], A[r+k], A[r+2k], ...
        for idx in range(r, N, k):
            val = A[idx]
            # Kadane adaptado: reinicia em val se soma anterior for negativa
            current = val if current < 0 else current + val
            # Atualiza o melhor para esta subsequência
            if current > best:
                best = current
        # Se esta rota tiver soma maior, atualiza o global
        if best > max_sum:
            max_sum = best

# Saída do resultado
print(max_sum)
