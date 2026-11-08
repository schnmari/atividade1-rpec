import java.util.Random;

public class TabelaHash {

    static class No { // classe no representa um elemento da lista encadeada
        int valor;  // num armazenado nesse no
        No proximo; //  ref para o proximo no da lista (nulo = ultimo no)

        No(int valor) { //construtor (guarda o valor recebido e deixa proximo como nulo)
            this.valor = valor;
            this.proximo = null;
        }
    }

    public static void main(String[] args) {

        Random random = new Random(1090);   // seed = 1090
        No[] tabela = new No[100];          // um vetor de 100 posicoes (todas comecam vazias)

        int ultimo1 = -1; // guarda os 3 ultimos valores inseridos em listas encadeadas 
        int ultimo2 = -1;
        int ultimo3 = -1; // ** -1 pq nao eh um valor possivel (tem q ser se 0 a 100k)

        // insercao dos 1000 num na tabela
        for (int i = 0; i < 1000; i++) {
            int valor = random.nextInt(100001);   // numero de 0 a 100000 (n inclui 100001)
            int posicao = valor % 100;            // funcao hash!!! resto da divisao por 100 é sempre entre 0 e 99

                if (tabela[posicao] == null) {
                tabela[posicao] = new No(valor); // posicao vazia, entao criamos um novo no e vira 1 da lista

            } else { // houve colisao aq, novo valor precisa ser add no final da lista
                No atual = tabela[posicao]; // atual é um ponteiro auxiliar, começa no 1 no da posicao

                while (atual.proximo != null) {
                    atual = atual.proximo; // avança de nó em nó até chegar no último
                }
                atual.proximo = new No(valor); // cria o novo no e liga ele no final da lista

                ultimo1 = ultimo2; // atualiza os 3 ultimos valores inseridos em listas
                ultimo2 = ultimo3;
                ultimo3 = valor;
            }
        }

        // estatisticas
        int posicoesVazias = 0;      // qnts posicoes do vetor continuaram null
        int quantidadeListas = 0;    // qnts posicoes tem 2 ou mais nos
        int maiorTamanho = 0;        // maior qnt de nos encontrada em uma posicao
        int posicaoMaiorLista = -1;  // indice do vetor onde ta a maior lista

        for (int i = 0; i < 100; i++) {  // percorre todas as 100 posições do vetor
            if (tabela[i] == null) { // posição null = nenhum valor caiu nela
                posicoesVazias++;
            } else {
                // (posicao c lista) percorre os nos p contar quantos existem
                int tamanho = 0;
                No atual = tabela[i];      // comeca no primeiro no
                while (atual != null) {    // p quando passar do ultimo no
                    tamanho++;             // conta o no atual
                    atual = atual.proximo; // vai p o prox
                }

                if (tamanho > 1) { // tamanho 1 eh so um valor solto, 2 ou mais ja forma lista encadeada
                    quantidadeListas++;
                }
                if (tamanho > maiorTamanho) { // compara com a maior lista até agora (se tive empate continua a 1 posicao encontrada)
                    maiorTamanho = tamanho;
                    posicaoMaiorLista = i;
                }
            }
        }

        // resultados
        System.out.println("Posicoes vazias: " + posicoesVazias);
        System.out.println("Listas encadeadas geradas: " + quantidadeListas);

        System.out.println("Maior lista: posicao " + posicaoMaiorLista // imprime em qual posição ta a maior lista e qnts elementos ela tem
                + " com " + maiorTamanho + " elementos");
        No atual = tabela[posicaoMaiorLista]; // percorre a maior lista imprimindo o valor de cada no (na ordem q foi inserid)
        while (atual != null) {
            System.out.print(atual.valor + " ");
            atual = atual.proximo;
        }
        System.out.println(); // quebra linha

        System.out.println("Tres ultimos valores inseridos em listas: " // imprime os 3 ultimos valores q entraram em listas
                + ultimo1 + ", " + ultimo2 + ", " + ultimo3);
    }
}