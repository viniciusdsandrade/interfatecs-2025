saida = []

n = 1
while n:
    inp = input().split()
    n = 0

    for i, d in enumerate(inp[::-1]):
        traco = d.count("-")
        ponto = d.count(".")

        n += (traco * 5 + ponto) * 20 ** i

    saida.append(n)

for n in saida:
    print(n)
