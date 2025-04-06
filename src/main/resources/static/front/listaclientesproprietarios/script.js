window.addEventListener("DOMContentLoaded", async () => {
    try {
      const response = await fetch("http://localhost:8080/api/clientes");
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
          <td>${cliente.cpf}</td>
          <td><a href="../cadastroautomovel/cadastro.html"><button class="btnCadastrar" data-id="${cliente.id}">Cadastrar automóvel</button></a></td>
        `;
      tbody.appendChild(tr);
    });

    const botoes = document.querySelectorAll(".btnCadastrar");
  botoes.forEach((btn) => {
    btn.addEventListener("click", (ev) => {
      const clienteId = btn.dataset.id;
      localStorage.setItem("proprietarioId", clienteId);
    });
  });
  }