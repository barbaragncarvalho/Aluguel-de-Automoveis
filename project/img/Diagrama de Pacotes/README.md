@startuml

package "Gestão de Pedidos e Contratos" {
  [Usuário]
  [Cliente] 
  [Agente] 
  [Banco]
  [Empresa]
  [Pedido]
  [Contrato]
  [Automóvel]
  [ContratoCrédito]
  [Rendimento]
  [OperacaoContrato]
  [Avaliacao]
}

package "Construção Dinâmica de Páginas Web" {
  [Formulários]
  [Templates]
  [Gerador de Interfaces]
}

[Gestão de Pedidos e Contratos] --> [Construção Dinâmica de Páginas Web] : usa para geração de UI
[Formulários] ..> [Cliente] : coleta dados
[Formulários] ..> [Agente] : valida permissões
@enduml
