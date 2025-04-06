const btn = document.getElementById("btnCadastrar");

btn.addEventListener("click", async (ev) => {
    ev.preventDefault();

    const nome = document.getElementById("nome").value
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
    const cnpj = document.getElementById("cnpj").value;
    const rua = document.getElementById("rua").value;
    const bairro = document.getElementById("bairro").value;
    const cep = document.getElementById("cep").value;
    const numero = parseInt(document.getElementById("numero").value);
    const opcional = document.getElementById("opcional").value;

    if (!validacao(nome, email, senha, cnpj, rua, bairro, cep, numero)) {
        return;
    }

    const dadosAgente = {
        nome: nome,
        rua: rua,
        bairro: bairro,
        cep: cep,
        numero: numero,
        opcional: opcional,
        email: email,
        senha: senha,
        cnpj: cnpj,
    };

    try {
        const response = await fetch("http://localhost:8080/api/agentes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(dadosAgente),
        });

        if (!response.ok) {
            alert("Erro ao cadastrar o agente.");
            return;
        }

        alert("Agente cadastrado com sucesso! ;)");
        document.getElementById("nome").value = "";
        document.getElementById("email").value = "";
        document.getElementById("senha").value = "";
        document.getElementById("cnpj").value = "";
        document.getElementById("rua").value = "";
        document.getElementById("bairro").value = "";
        document.getElementById("cep").value = "";
        document.getElementById("numero").value = "";
        document.getElementById("opcional").value = "";
        window.location.href = "../listadeagentes/agentes.html";
    } catch (error) {
        alert("Erro na requisição de cadastro. Tente novamente, por favor ;)");
    }
});

function validacao(nome, email, senha, cnpj, rua, bairro, cep, numero) {

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

    const cnpjRegex = new RegExp(
        /^\d{14}$/
    );
    if (!cnpjRegex.test(cnpj)) {
        alert("O CNPJ deve conter 14 dígitos e apenas números.");
        return false;
    }

    const emailRegex = new RegExp(
        /^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,}$/
    );
    if (!emailRegex.test(email)) {
        alert("Por favor, insira um email válido.");
        return false;
    }

    if (senha.length < 3) {
        alert("A senha deve ter pelo menos 3 dígitos.");
        return false;
    }

    rua = rua.trim();
    if (rua === "") {
        alert("Por favor, informe a rua.");
        return false;
    }

    bairro = bairro.trim();
    if (bairro === "") {
        alert("Por favor, informe o bairro.");
        return false;
    }

    if (cep == "") {
        alert("Por favor, informe o CEP.");
        return false;
    }

    if (isNaN(numero) || numero < 0) {
        alert("Por favor, informe o número da sua residência.");
        return false;
    }

    return true;
}