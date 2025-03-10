const API_URL = 'http://localhost:8080';


async function salvarDisciplina(event) {
    event.preventDefault();
    console.log(event);
    
    const nome = document.getElementById('nome-disciplina').value;
    const codigo = document.getElementById('codigo-disciplina').value;
    const carga_horaria = document.getElementById('creditos-disciplina').value;
    const professorId = document.getElementById('professor-select').value;

    
    try {
        const response = await fetch(`${API_URL}/disciplina`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nome: nome,
                codigo: codigo, 
                carga_horaria: parseInt(carga_horaria),
                professorId: parseInt(professorId)
            })
        });

        if (!response.ok) {
            throw new Error('Falha ao criar disciplina');
        }
        
        alert('Disciplina criada com sucesso!');
        document.getElementById('disciplina-form').reset();
        loadDisciplinas();

    } catch (error) {
        alert(error.message);
    }
}

// async function loadDisciplinas() {
//     try {
//         const response = await fetch('http://localhost:8080/api/disciplinas/professor', {
//         });
//         const disciplinas = await response.json();
        
//         const container = document.getElementById('disciplinas-list');
//         container.innerHTML = '';
        
//         disciplinas.forEach(disciplina => {
//             const div = document.createElement('div');
//             div.className = 'disciplina-item';
//             div.innerHTML = `
//                 <h4>${disciplina.nome}</h4>
//                 <p>Código: ${disciplina.codigo}</p>
//                 <button onclick="verAlunos(${disciplina.id})">Ver Alunos</button>
//             `;
//             container.appendChild(div);
//         });
//     } catch (error) {
//         alert('Erro ao carregar disciplinas');
//     }
// }

