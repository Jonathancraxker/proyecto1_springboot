let modalDirectorioInstance;

async function abrirModalDirectorio(id = null) {
    try {
        limpiarFormulario();
        const titulo = document.getElementById('personaModalLabel');

        if (id) {
            titulo.textContent = 'Editar Persona';
            const response = await fetch(`/api/directorio/${id}`);
            if (!response.ok) throw new Error('Error al cargar datos');
            
            const persona = await response.json();
            
            document.getElementById('id').value = persona.id;
            document.getElementById('nombre').value = persona.nombre;
            document.getElementById('cargo').value = persona.cargo;
            document.getElementById('correo').value = persona.correo;
            document.getElementById('extension').value = persona.extension;
        } else {
            titulo.textContent = 'Agregar Persona';
            document.getElementById('id').value = '';
        }

        if (!modalDirectorioInstance) {
            modalDirectorioInstance = new bootstrap.Modal(document.getElementById('personaModal'));
        }
        modalDirectorioInstance.show();
    } catch (error) {
        console.error('Error al abrir la modal:', error);
    }
}

function limpiarFormulario() {
    const form = document.getElementById('personaForm');
    if (form) {
        form.reset();
        form.classList.remove('was-validated');
    }
}

async function guardarPersona(event) {
    event.preventDefault();
    const form = document.getElementById('personaForm');

    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }

    try {
        const formData = new FormData(form);
        const data = {
            id: formData.get('id') ? parseInt(formData.get('id')) : null,
            nombre: formData.get('nombre'),
            cargo: formData.get('cargo'),
            correo: formData.get('correo'),
            extension: formData.get('extension')
        };

        const response = await fetch('/api/directorio/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error('');
        const result = await response.json();

        if (result.success) {
            if (modalDirectorioInstance) modalDirectorioInstance.hide();

            const row = document.getElementById(`row-${data.id}`);
            if (row) {
                const cells = row.querySelectorAll('td');

                cells[1].textContent = data.nombre;
                cells[2].textContent = data.cargo;
                cells[3].textContent = data.correo;
                cells[4].textContent = data.extension;
                
                row.classList.add('table-info');
                setTimeout(() => row.classList.remove('table-info'), 900);
            } else {
                window.location.reload();
            }
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error al guardar ' + error.message);
    }
}