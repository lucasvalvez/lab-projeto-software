const API_URL = 'http://localhost:8080';

async function loadProfessores() {
    const token = localStorage.getItem('token');
    if (!token) {
        console.error("Token não encontrado");
        return;
    }

    try {
        const response = await fetch(`${API_URL}/api/usuarios/professores`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) throw new Error('Falha ao carregar professores');

        const professores = await response.json();
        const select = document.getElementById('professor-select');
        select.innerHTML = '<option value="">Selecione o professor</option>';

        professores.forEach(professor => {
            const option = document.createElement('option');
            option.value = professor.id;
            option.textContent = professor.nome;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar professores:", error);
    }
}