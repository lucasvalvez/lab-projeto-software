const API_URL = 'http://localhost:8080';

async function login(email, password) {
    try {
        const response = await fetch(`${API_URL}/api/usuarios/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email,
                password
            })
        });

        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.message || 'Login failed');
        }

        localStorage.setItem('token', data.token);
        localStorage.setItem('username', data.username);

        window.location.href = `./html/home.html`;
    } catch (error) {
        alert(error.message);
    }
}

async function register(name, email, password, role) {
    alert('a');
    try {
        const response = await fetch(`${API_URL}/api/usuarios/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name,
                email,
                password,
                role    
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Falha ao registrar');
        }

        alert('Usuario registrado com sucesso');
        document.querySelector('[onclick="openTab(event, \'login\')"]').click();
    } catch (error) {
        alert(error.message);
    }
}

async function auth(token) {
    try {
        const response = await fetch(`${API_URL}/api/usuarios/auth/${token}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email,
                password
            })
        });

        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.message || 'Login failed');
        }

        return data;
    } catch (error) {
        alert(error.message);
    }
}
