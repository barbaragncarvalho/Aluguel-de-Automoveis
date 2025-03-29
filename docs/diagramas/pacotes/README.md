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
    
    package dto <<Layer>> {
        [AutomovelDTO.java]
        [ClienteDTO.java]
        [ContratanteDTO.java]
    }
package "View" <<Layer>> {

    }
}


controller --> repository
controller --> service
service --> repository
repository --> model
controller --> dto
dto .up.> model 
controller --> "View"
controller --> "model"
model --> enumerators

@enduml