from collections import defaultdict, deque

qc = int(input().strip())

grafo = defaultdict(list)
for _ in range(qc):
    a, b = input().split()
    grafo[a].append(b)
    grafo[b].append(a)


input()

consultas = []
while True:
    linha = input().split()
    if not linha or linha == ['*', '*']:
        break
    consultas.append((linha[0], linha[1]))

for a, b in consultas:
    if a == b:
        print(f"{a}-{b} = 0")
        continue

    visitado = {a}
    fila = deque([(a, 0)])
    dist = None

    while fila:
        nodo, d = fila.popleft()
        for viz in grafo[nodo]:
            if viz == b:
                dist = d + 1
                fila.clear()
                break
            if viz not in visitado:
                visitado.add(viz)
                fila.append((viz, d + 1))

    if dist is None:
        print(f"{a}-{b} = sem conexao")
    else:
        print(f"{a}-{b} = {dist}")
