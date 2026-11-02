import java.util.Random;

public class TabelaHash {

    static class No { // cada no guarda um valor e aponta para o proximo no da lista
        int valor;
        No proximo;

        No(int valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    public static void main(String[] args) {

        Random random = new Random(1090);   // seed = 1090
        No[] tabela = new No[100];          // tabela com 100 posicoes (todas comecam vazias = null)

        int ultimo1 = -1; // guarda os 3 ultimos valores inseridos em listas encadeadas 
        int ultimo2 = -1;
        int ultimo3 = -1;

        // insercao dos 1000 num
        for (int i = 0; i < 1000; i++) {
            int valor = random.nextInt(100001);   // numero de 0 a 100000
            int posicao = valor % 100;            // funcao hash, resto da divisao por 100

            if (tabela[posicao] == null) {
                tabela[posicao] = new No(valor); // posicao vazia, o valor vira o primeiro No
            } else {
                No atual = tabela[posicao]; // colisao, vai ate o final da lista e coloca o novo no
                while (atual.proximo != null) {
                    atual = atual.proximo;
                }
                atual.proximo = new No(valor);

                ultimo1 = ultimo2; // atualiza os 3 ultimos valores inseridos em listas
                ultimo2 = ultimo3;
                ultimo3 = valor;
            }
        }

        // ---------- ESTATISTICAS ----------
        int posicoesVazias = 0;
        int quantidadeListas = 0;      // posicoes com 2 ou mais elementos
        int maiorTamanho = 0;
        int posicaoMaiorLista = -1;

        for (int i = 0; i < 100; i++) {
            if (tabela[i] == null) {
                posicoesVazias++;
            } else {
                // conta quantos elementos tem na posicao i
                int tamanho = 0;
                No atual = tabela[i];
                while (atual != null) {
                    tamanho++;
                    atual = atual.proximo;
                }

                if (tamanho > 1) {
                    quantidadeListas++;
                }
                if (tamanho > maiorTamanho) {
                    maiorTamanho = tamanho;
                    posicaoMaiorLista = i;
                }
            }
        }

        // resultados
        System.out.println("Posicoes vazias: " + posicoesVazias);
        System.out.println("Listas encadeadas geradas: " + quantidadeListas);

        System.out.println("Maior lista: posicao " + posicaoMaiorLista
                + " com " + maiorTamanho + " elementos");
        No atual = tabela[posicaoMaiorLista];
        while (atual != null) {
            System.out.print(atual.valor + " ");
            atual = atual.proximo;
        }
        System.out.println();

        System.out.println("Tres ultimos valores inseridos em listas: "
                + ultimo1 + ", " + ultimo2 + ", " + ultimo3);
    }
}