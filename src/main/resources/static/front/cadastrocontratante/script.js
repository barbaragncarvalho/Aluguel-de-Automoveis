const btn = document.getElementById("btnCadastrar");

btn.addEventListener("click", async (ev) => {
    ev.preventDefault();

    const nome = document.getElementById("nome").value
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
    const cpf = document.getElementById("cpf").value;
    const rg = document.getElementById("rg").value;
    const rua = document.getElementById("rua").value;
    const bairro = document.getElementById("bairro").value;
    const cep = document.getElementById("cep").value;
    const numero = parseInt(document.getElementById("numero").value);
    const opcional = document.getElementById("opcional").value;
    const profissao = document.getElementById("profissao").value;

    if (!validacao(nome, email, senha, cpf, rg, rua, bairro, cep, numero, profissao)) {
        return;
    }

    if (!validaRendimentos()) {
        return;
    }

    const rendimentos = [];
    for (let i = 1; i <= 3; i++) {
        const rendimentoInput = document.getElementById(`rendimento${i}`);
        const entidadeInput = document.getElementById(`entidade${i}`);
        const rendimento = rendimentoInput.value.trim();
        const entidade = entidadeInput.value.trim();

        if (rendimento && entidade) {
            rendimentos.push({
                entidadeEmpregadora: entidade,
                valorRendimento: parseFloat(rendimento),
            });
        }
    }

    const dadosCliente = {
        nome: nome,
        rua: rua,
        bairro: bairro,
        cep: cep,
        numero: numero,
        opcional: opcional,
        email: email,
        senha: senha,
        rg: rg,
        cpf: cpf,
        profissao: profissao,
        rendimentos: rendimentos
    };

    try {
        const response = await fetch("http://localhost:8080/api/contratantes", {
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
        document.getElementById("email").value = "";
        document.getElementById("senha").value = "";
        document.getElementById("cpf").value = "";
        document.getElementById("rg").value = "";
        document.getElementById("rua").value = "";
        document.getElementById("bairro").value = "";
        document.getElementById("cep").value = "";
        document.getElementById("numero").value = "";
        document.getElementById("opcional").value = "";
        document.getElementById("profissao").value = "";

        document.getElementById("rendimento1").value = "";
        document.getElementById("entidade1").value = "";
        document.getElementById("rendimento2").value = "";
        document.getElementById("entidade2").value = "";
        document.getElementById("rendimento3").value = "";
        document.getElementById("entidade3").value = "";
        window.location.href = "../listaclientes/lista.html";
    } catch (error) {
        alert("Erro na requisição de cadastro. Tente novamente, por favor ;)");
    }
});

function validaRendimentos() {
    let peloMenosUmPreenchido = false;

    for (let i = 1; i <= 3; i++) {
        const rendimentoInput = document.getElementById(`rendimento${i}`).value.trim();
        const entidade = document.getElementById(`entidade${i}`).value.trim();

        if (rendimentoInput || entidade) {
            const rendimento = parseFloat(rendimentoInput);
            if (isNaN(rendimento) || rendimento <= 0) {
                alert("Por favor, informe um valor de rendimento positivo.");
                return false;
            }

            if (!entidade) {
                alert("Por favor, preencha ambos os campos de rendimento e entidade quando um deles for preenchido.");
                return false;
            }
            peloMenosUmPreenchido = true;
        }
    }

    if (!peloMenosUmPreenchido) {
        alert("Por favor, informe pelo menos 1 rendimento e a entidade que o provê.");
        return false;
    }

    return true;
}

function validacao(nome, email, senha, cpf, rg, rua, bairro, cep, numero, profissao) {

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

    const cpfRegex = new RegExp(
        /^\d{11}$/
    );
    if (!cpfRegex.test(cpf)) {
        alert("O CPF deve conter 11 dígitos e apenas números.");
        return false;
    }

    rg = rg.trim();
    if (rg === "") {
        alert("Por favor, informe seu RG.");
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

    profissao = profissao.trim();
    if (profissao === "") {
        alert("Por favor, informe a sua profissão.");
        return false;
    }
    return true;
}