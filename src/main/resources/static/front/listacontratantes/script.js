window.addEventListener("DOMContentLoaded", async () => {
  try {
    const response = await fetch("http://localhost:8080/api/contratantes");
    if (!response.ok) {
      alert("Erro ao buscar os clientes cadastrados!");
    }
    const clientes = await response.json();
    exibeClientes(clientes);
  } catch (error) {
    console.error("Erro:", error);
    alert("Não foi possível carregar os clientes.");
  }
});

function exibeClientes(clientes) {
  const tbody = document.getElementById("tabelaClientes");
  tbody.innerHTML = "";

  clientes.forEach((cliente) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${cliente.nome}</td>
        <td><button class="btnAtualizar" data-id="${cliente.id}">Atualizar</button></td>
        <td><button class="btnExcluir" data-id="${cliente.id}">Excluir</button></td>
      `;
    tbody.appendChild(tr);
  });

  document.querySelectorAll(".btnAtualizar").forEach((btn) => {
    btn.addEventListener("click", () => {
      const idCliente = btn.getAttribute("data-id");
      localStorage.setItem("clienteId", idCliente);
      window.location.href = "../atualizarcontratante/atualizar.html";
    });
  });

  document.querySelectorAll(".btnExcluir").forEach((btn) => {
    btn.addEventListener("click", () => {
      const idCliente = btn.getAttribute("data-id");
      const confirmar = confirm("Tem certeza que deseja excluir este cliente?");
      if (confirmar) {
        deletarCliente(idCliente);
      }
    });
  });
}

async function deletarCliente(idCliente) {
  try {
    const response = await fetch(`http://localhost:8080/api/contratantes/${idCliente}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      alert("Erro ao excluir o cliente!");
    }
    alert("Cliente deletado com sucesso! ;)");
    window.location.reload();
  } catch (error) {
    console.error("Erro:", error);
    alert("Não foi possível excluir o cliente.");
  }
}