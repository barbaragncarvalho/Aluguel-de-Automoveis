document.addEventListener("DOMContentLoaded", () => {
    const proprietarioId = localStorage.getItem("proprietarioId");
    console.log(proprietarioId)

    const btnCadastrar = document.getElementById("btnCadastrar");
    btnCadastrar.addEventListener("click", async (ev) => {
        ev.preventDefault();

        const matricula = document.getElementById("matricula").value;
        const placa = document.getElementById("placa").value;
        const modelo = document.getElementById("modelo").value;
        const marca = document.getElementById("marca").value;
        const ano = parseInt(document.getElementById("ano").value);
        const tipoProprietario = document.getElementById("tipoProprietario").value;

        const dadosAutomovel = {
            matricula: matricula,
            placa: placa,
            modelo: modelo,
            marca: marca,
            ano: ano,
            disponivel: true,
            proprietario: {
                tipo: tipoProprietario,
                id: proprietarioId
            }
        };

        try {
            const response = await fetch("http://localhost:8080/api/automoveis", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(dadosAutomovel),
            });

            if (!response.ok) {
                alert("Erro ao cadastrar o automóvel.");
                return;
            }

            alert("Automóvel cadastrado com sucesso!");
            document.getElementById("matricula").value = "";
            document.getElementById("placa").value = "";
            document.getElementById("modelo").value = "";
            document.getElementById("marca").value = "";
            document.getElementById("ano").value = "";
        } catch (error) {
            console.error("Erro:", error);
            alert("Erro na requisição de cadastro. Tente novamente, por favor.");
        }
    });
});