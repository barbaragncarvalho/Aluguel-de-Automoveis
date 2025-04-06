window.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch("http://localhost:8080/api/automoveis");
        if (!response.ok) {
            alert("Erro ao buscar os automóveis cadastrados!");
        }
        const automoveis = await response.json();
        const idCliente = localStorage.getItem("clienteId");
        exibeAutomoveis(automoveis, idCliente);
    } catch (error) {
        console.error("Erro:", error);
        alert("Não foi possível carregar os automóveis.");
    }
});

function exibeAutomoveis(automoveis, idCliente) {
    const tbody = document.getElementById("tabelaAutomoveis");
    tbody.innerHTML = "";

    let tr = document.createElement("tr");
    tr.innerHTML = `
          <td><strong>Marca</strong></td>
          <td><strong>Modelo</strong></td>
          <td><strong>Ano</strong></td>
          <td><strong>Placa</strong></td>
        `;
    tbody.appendChild(tr);

    const disponiveis = automoveis.filter(automovel => automovel.disponivel);

    disponiveis.forEach((automovel) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${automovel.marca}</td>
          <td>${automovel.modelo}</td>
          <td>${automovel.ano}</td>
          <td>${automovel.placa}</td>
          <td><a href="../pedidosagente/pedidos.html"><button class="btnPedido">Realizar Pedido</button></a></td>
        `;
        tbody.appendChild(tr);
    });

    /*document.querySelectorAll(".btnPedido").forEach((btn) => {
        btn.addEventListener("click", () => {
            window.location.href = "../pedidosagente/pedidos.html";
        });
    });*/
}
