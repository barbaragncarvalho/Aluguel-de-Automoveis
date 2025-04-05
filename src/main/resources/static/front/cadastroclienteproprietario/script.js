const btn = document.getElementById("btnCadastrar");

btn.addEventListener("click", async (ev) => {
    ev.preventDefault();

    const nome = document.getElementById("nome").value
    const cpf = document.getElementById("cpf").value;

    if (!validacao(nome, cpf)) {
        return;
    }

    const dadosCliente = {
        tipo: "cliente",
        nome: nome,
        cpf: cpf
    };

    try {
        const response = await fetch("http://localhost:8080/api/clientes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(dadosCliente),
        });

        if (!response.ok) {
            alert("Erro ao cadastrar o cliente.");
            return;
        }

        alert("Cliente cadastrado com sucesso! ;)");
        document.getElementById("nome").value = "";
        document.getElementById("cpf").value = "";
        window.location.href = "../listaclientesproprietarios/clientes.html";
    } catch (error) {
        alert("Erro na requisição de cadastro. Tente novamente, por favor ;)");
    }
});

function validacao(nome, cpf) {

    nome = nome.trim();

    if (nome === "" || nome.length < 3) {
        alert("Por favor, insira um nome que tenha, ao menos, 3 caracteres.");
        return false;
    }

    const nomeRegex = /^[a-zA-ZÀ-ÿ\s]+$/;
    if (!nomeRegex.test(nome)) {
        alert("O nome deve conter apenas letras.");
        return false;
    }

    const cpfRegex = new RegExp(
        /^\d{11}$/
    );
    if (!cpfRegex.test(cpf)) {
        alert("O CPF deve conter 11 dígitos e apenas números.");
        return false;
    }

    return true;
}