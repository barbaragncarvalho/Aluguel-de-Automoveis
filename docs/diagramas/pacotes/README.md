@startuml
left to right direction

package "com.example.aluguelautomoveis" {
    package controller <<Layer>> {
        [AgenteController.java]
        [ClienteController.java]
        [ContratanteController.java]
        [UsuarioController.java]
    }
    
   package enumerators <<Layer>> {

    }

    package service <<Layer>> {
        [AluguelAutomoveisApplication.java]
    }
    
    package repository <<Layer>> {
        [AgenteRepository.java]
        [ClienteRepository.java]
        [ContratanteRepository.java]
        [UsuarioRepository.java]
    }
    
    package model <<Layer>> {
        [Agente.java]
        [Cliente.java]
        [Contratante.java]
        [Usuario.java]
    }
package "View" <<Layer>> {

    }
}


controller .up.> repository
controller .up.> service
service .up.> repository
repository .up.> model
controller .up.> "View"
controller .up.> "model"
model .up.> enumerators

@enduml
