const API_URL = 'http://localhost:8080';

async function salvarTurma(event) {
    event.preventDefault();
    console.log(event)
    
    const nome = document.getElementById('nome-turma').value;
    const quantidadeAlunos = document.getElementById('max-alunos').value;
    
    try {
        const response = await fetch(`${API_URL}/turma`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: nome,
                maxAlunos: parseInt(quantidadeAlunos)
            })
        });

        if (!response.ok) {
            throw new Error('Falha ao criar turma');
        }

        
    } catch (error) {
        alert(error.message);
    }
}
