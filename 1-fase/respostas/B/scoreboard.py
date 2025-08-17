class Team:
    def __init__(self, name, fatec, solved, time):
        self.name = name
        self.fatec = fatec
        self.solved = solved
        self.time = time

    def key_score(self):
        return -self.solved, self.time, self.name


# Leitura direta linha a linha
host = input().rstrip()
V, V_es = map(int, input().split())
NT = int(input())

teams = []
for _ in range(NT):
    nome, fatec, prob_str, tempo_str = input().split('|')
    teams.append(Team(nome, fatec, int(prob_str), int(tempo_str)))

# 1) Desclassificados (resolveram zero) e candidatos
desclassificados = [t for t in teams if t.solved == 0]
candidatos = [t for t in teams if t.solved > 0]

# 2) Critério da sede: V_es + 1 melhores da Fatec host
host_teams = [t for t in candidatos if t.fatec == host]
host_teams.sort(key=lambda t: (-t.solved, t.time))
selecionados = host_teams[: V_es + 1]

# 3) Melhor de cada Fatec (exceto a sede)
outras_fatecs = set(t.fatec for t in candidatos if t.fatec != host)
for f in outras_fatecs:
    ft = [t for t in candidatos if t.fatec == f and t not in selecionados]
    if ft:
        ft.sort(key=lambda t: (-t.solved, t.time))
        selecionados.append(ft[0])

# 4) Preencher vagas remanescentes
vagas_rest = V - len(selecionados)
if vagas_rest > 0:
    remanescentes = [t for t in candidatos if t not in selecionados]
    remanescentes.sort(key=lambda t: t.key_score())
    selecionados.extend(remanescentes[:vagas_rest])

# 5) Ordenações finais
finais = sorted(selecionados, key=lambda t: t.name)
espera = [t for t in candidatos if t not in selecionados]
espera.sort(key=lambda t: t.key_score())
desclassificados.sort(key=lambda t: t.name)

# 6) Impressão formatada
print("Classificados para a Final")
for t in finais:
    print(f"{t.name} - {t.fatec} ({t.solved},{t.time})")

print()
print("Lista de Espera")
for t in espera:
    print(f"{t.name} - {t.fatec} ({t.solved},{t.time})")

print()
print("Desclassificados")
for t in desclassificados:
    print(f"{t.name} - {t.fatec} ({t.solved},{t.time})")

print()
print("Apuracao concluida!")
