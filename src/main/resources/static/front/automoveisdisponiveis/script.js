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
          <td><a href="../pedidoscliente/pedidos.html"><button class="btnPedido" data-id="${automovel.id}">Realizar Pedido</button></a></td>
        `;
        tbody.appendChild(tr);
    });

    document.querySelectorAll(".btnPedido").forEach((btn) => {
        btn.addEventListener("click", async (ev) => {
            ev.preventDefault();
            const automovelId = btn.getAttribute("data-id");
            if (!idCliente) {
                alert("Contratante não encontrado!");
                return;
            }

            const pedido = {
                contratante: { tipo:"contratante", id: idCliente },
                automovel: { id: automovelId }
            };

            try {
                const response = await fetch("http://localhost:8080/api/pedidos", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(pedido),
                });

                if (!response.ok) {
                    alert("Erro ao criar o pedido.");
                    return;
                }

                alert("Pedido criado com sucesso!"); 
                window.location.href = "../listacontratantes/lista.html";
            } catch (error) {
                console.error("Erro ao criar pedido:", error);
                alert("Erro na requisição para criar pedido. Tente novamente, por favor.");
            }
        });
    });
}
