@startuml

package "Gestão de Pedidos e Contratos" {
  [Usuário]
  [Cliente] <<interface>>
  [Agente] <<interface>>
  [Banco]
  [Empresa]
  [Pedido]
  [Contrato]
  [Automóvel]
  [ContratoCrédito]
  [Rendimento]
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