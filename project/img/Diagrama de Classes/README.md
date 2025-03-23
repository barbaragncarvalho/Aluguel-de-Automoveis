@startuml
skinparam classAttributeIconSize 0

' --- Interfaces (Papéis) ---
interface Cliente {
  + RG: String
  + CPF: String
  + profissão: String
}

interface Agente {
  + CNPJ: String
  + avaliar(pedido: Pedido): Avaliação
}

' --- Classes Principais ---
class Usuário {
  # id: String
  # nome: String
  # endereço: String
  # senha: String
}

class Banco {
}

class Empresa {
}

class Rendimento {
  entidade_empregadora: String
  valor_rendimento: Float
}

class Pedido {
  id: String
  data_pedido: Date
  status: StatusPedido
}

class Contrato {
  id: String
  data_início: Date
  data_fim: Date
  tipo: String
}

class Automóvel {
  matrícula: String
  ano: Integer
  marca: String
  modelo: String
  placa: String
}

class ContratoCrédito {
  id: String
  taxa_juros: Float
  prazo: Integer
}

class Avaliação {
  id: String
  data_análise: Date
  resultado: String
  observações: String
}

class OperaçãoContrato {
  id: String
  tipo: String
  data: Date
}

enum StatusPedido {
  EM_ANÁLISE
  APROVADO
  REPROVADO
  CANCELADO
}

' --- Implementações de Interfaces ---
Usuário .|> Cliente
Usuário .|> Agente
Agente <|.. Banco
Agente <|.. Empresa

' --- Associações ---
Cliente "1" *-- "0..3" Rendimento 
Cliente "1" --> "0..*" Pedido 
Agente "1" --> "0..*" Avaliação
Pedido "1" --> "1" Avaliação
Pedido "1" --> "1" Contrato 
Contrato "1" --> "1" Automóvel 
Contrato "1" --> "1" Cliente 
Contrato "1" --> "1" Agente 
Contrato "1" --> "0..*" OperaçãoContrato
Pedido "1" --> "0..1" ContratoCrédito 
Banco "1" --> "0..*" ContratoCrédito 
@enduml