N = int(input())

for _ in range(N):
    # Cada linha traz Temperatura (T), Umidade (U) e Previsão de Chuva (P)
    T_str, U_str, P_str = input().split()
    T = float(T_str)
    U = float(U_str)
    P = int(P_str)

    # Regra 1: se há previsão de chuva → NAO REGAR
    if P == 1:
        print("NAO REGAR")
    else:
        # Regra 2.1: sem chuva, mas calor extremo e solo seco → REGAR
        if T > 30.0 and U < 50.0:
            print("REGAR")
        # Regra 2.2: todas as outras condições → NAO REGAR
        else:
            print("NAO REGAR")
