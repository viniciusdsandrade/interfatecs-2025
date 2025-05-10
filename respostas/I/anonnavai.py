# Dorothy e Dagmar são primas da mesma idade, moram na mesma rua e têm a Nonna Bina como avó. Elas
# costumam sair juntas nos fins de semana e usam bolinhas de bingo numeradas de 1 a 30 para decidir o que
# fazer. Toda quinta-feira, após o jantar na casa da Nonna, cada uma sorteia uma bolinha; quem tirar o maior
# número decide o programa do fim de semana.
# A Nonna só permite o uso das bolinhas se, às vezes, for incluída nos passeios. Quando a soma dos dois
# números sorteados for maior que 40, ela vai junto, e o programa deve ser mais tranquilo (como cinema,
# teatro ou pizzaria). Caso contrário, as primas podem ir a baladas e festivais, que são suas preferências.
# Como agora precisam se planejar com antecedência para comprar ingressos, querem fazer os sorteios para
# várias semanas e registrar quem decide e se a Nonna irá. Para isso, você foi encarregado de escrever um
# programa que organize essas informações.
# Entrada
# A primeira linha da entrada contém a quantidade Q (2 ≤ Q ≤ 1000) de pares de bolinhas sorteadas. Em
# seguida, há 2Q linhas, cada uma com um número inteiro entre 1 e 30. Estas linhas devem ser lidas aos pares:
# o primeiro valor é o número sorteado por Dorothy, e o segundo é o de Dagmar. Como elas sorteiam bolinhas
# físicas e não as devolvem ao globo, os valores sorteados por ambas nunca serão iguais.
# Saída
# Para cada par de valores o programa deve imprimir na saída quem decide e se a Nonna vai.
# Se o valor da Dorothy for maior que o da Dagmar e a soma dos valores for maior que 40 o programa
# deve escrever "DOROTHY DECIDE E A NONNA VAI". Caso a soma não ultrapasse 40 escreva apenas
# "DOROTHY DECIDE".
# Se o valor da Dorothy for menor que o da Dagmar e a soma dos valores for maior que 40 o programa
# deve escrever "DAGMAR DECIDE E A NONNA VAI". Caso a soma não ultrapasse 40 escreva apenas
# "DAGMAR DECIDE".
# As aspas não devem ser incluídas na saída. A saída é toda em letras maiúsculas. Ao final da última linha
# não se equeçam da quebra de linha.
# Exemplo de Entrada 1 Exemplo de Saída 1
# 4
# 23
# 12
# 26
# 20
# 12
# 23
# 20
# 26
# DOROTHY DECIDE
# DOROTHY DECIDE E A NONNA VAI
# DAGMAR DECIDE
# DAGMAR DECIDE E A NONNA VAI

while True:
    Q = int(input().strip())
    for _ in range(Q):
        dorothy = int(input().strip())
        dagmar = int(input().strip())

        if dorothy > dagmar:
            decider = "DOROTHY"
        else:
            decider = "DAGMAR"

        total = dorothy + dagmar
        if total > 40:
            print(f"{decider} DECIDE E A NONNA VAI")
        else:
            print(f"{decider} DECIDE")
