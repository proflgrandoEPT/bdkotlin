// 1. O DADO: Usamos o "data class" para criar a estrutura da informação.
// O "val" garante que o nome do aluno e o equipamento não mudem após o registro.
data class Emprestimo(val aluno: String, val equipamento: String)

// 2. O GERENTE: A classe que decide para onde o dado vai.
class RepositorioLaboratorio {
    
    // Simulando o nosso banco de dados local (SQLite) usando uma lista simples.
    // Usamos "val" para a caixa (a lista em si), mas podemos adicionar itens dentro dela.
    val bancoLocal = mutableListOf<Emprestimo>()

    // Função que decide o destino baseada na conexão
    fun salvarDado(novoEmprestimo: Emprestimo, temInternet: Boolean) {
        if (temInternet) {
            println("NUVEM: Salvando '${novoEmprestimo.equipamento}' direto no servidor da escola!")
        } else {
            println("SEM INTERNET: Salvando '${novoEmprestimo.equipamento}' no SQLite (Banco Local).")
            bancoLocal.add(novoEmprestimo)
        }
    }

    // Função que executa a sincronização quando a internet volta
    fun sincronizarDadosRetidos() {
        println("\n--- Sistema detectou que a Internet voltou! ---")
        
        if (bancoLocal.isEmpty()) {
            println("Nenhum dado pendente no banco local.")
        } else {
            for (item in bancoLocal) {
                println("SINCRONIZANDO: Enviando '${item.equipamento}' do aluno ${item.aluno} para a Nuvem.")
            }
            // Após enviar para a nuvem, limpamos o banco local
            bancoLocal.clear() 
            println("Sincronização concluída com sucesso! Banco local limpo.")
        }
    }
}

// 3. A TELA DO APP: Onde a simulação acontece na prática
fun main() {
    val repositorio = RepositorioLaboratorio()
    
    println("=== INICIANDO O APLICATIVO DO LABORATÓRIO ===\n")
    
    // Cenário 1: Alunos pedem equipamentos, mas o Wi-Fi da escola caiu
    val internetConectada = false
    
    val pedido1 = Emprestimo("João", "Arduino Uno")
    val pedido2 = Emprestimo("Maria", "Cabo de Rede")
    
    repositorio.salvarDado(pedido1, internetConectada)
    repositorio.salvarDado(pedido2, internetConectada)
    
    // Cenário 2: O Wi-Fi da escola volta a funcionar
    val internetVoltou = true
    
    if (internetVoltou) {
        repositorio.sincronizarDadosRetidos()
    }
    
    // Cenário 3: Um novo aluno pede equipamento, agora com a internet funcionando
    println("\n--- Novo registro ---")
    val pedido3 = Emprestimo("Carlos", "Multímetro")
    repositorio.salvarDado(pedido3, internetVoltou)
}
