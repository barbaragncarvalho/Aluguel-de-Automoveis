window.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch("http://localhost:8080/api/agentes");
        if (!response.ok) {
            alert("Erro ao buscar os agentes cadastrados!");
        }
        const agentes = await response.json();
        exibeAgentes(agentes);
    } catch (error) {
        console.error("Erro:", error);
        alert("Não foi possível carregar os agentes.");
    }
});

function exibeAgentes(agentes) {
    const tbody = document.getElementById("tabelaAgentes");
    tbody.innerHTML = "";

    agentes.forEach((agente) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${agente.nome}</td>
          <td><a href="../cadastroautomovel/cadastro.html"><button class="btnCadAutomovel" data-id="${agente.id}">Cadastrar Automóvel</button></a></td>
          <td><a href="../pedidosagente/pedidos.html"><button class="btnPedidos" data-id="${agente.id}">Pedidos</button></a></td>
        `;
        tbody.appendChild(tr);
    });

    document.querySelectorAll(".btnCadAutomovel").forEach((btn) => {
        btn.addEventListener("click", () => {
            const idAgente = btn.getAttribute("data-id");
            localStorage.setItem("proprietarioId", idAgente);
            window.location.href = "../cadastroautomovel/cadastro.html";
        });
    });

    document.querySelectorAll(".btnPedidos").forEach((btn) => {
        btn.addEventListener("click", () => {
            const idAgente = btn.getAttribute("data-id");
            localStorage.setItem("proprietarioId", idAgente);
            window.location.href = "../pedidosagente/pedidos.html";
        });
    });
}
