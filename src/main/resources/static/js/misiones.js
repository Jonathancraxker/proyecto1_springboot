let modalMisionInstance;

async function abrirModalMision(id = null) {
    try {
        limpiarFormulario();
        const titulo = document.getElementById('misionModalLabel');

        if (id) {
            titulo.textContent = 'Editar Información Institucional';
            const response = await fetch(`/api/mision/${id}`);
            if (!response.ok) throw new Error('Error al cargar datos');
            
            const mision = await response.json();
            
            document.getElementById('id').value = mision.id;
            document.getElementById('mision').value = mision.mision;
            document.getElementById('vision').value = mision.vision;
            document.getElementById('politica').value = mision.politica;
            document.getElementById('objetivos').value = mision.objetivos;
            document.getElementById('valores').value = mision.valores;
            document.getElementById('fecha').value = mision.fecha;
            document.getElementById('activo').checked = mision.activo;
            
        } else {
            titulo.textContent = 'Agregar Información Institucional';
            document.getElementById('id').value = '';
        }

        if (!modalMisionInstance) {
            modalMisionInstance = new bootstrap.Modal(document.getElementById('misionModal'));
        }
        modalMisionInstance.show();
    } catch (error) {
        console.error('Error al abrir la modal:', error);
    }
}

function limpiarFormulario() {
    const form = document.getElementById('misionForm');
    if (form) {
        form.reset();
        form.classList.remove('was-validated');
    }
}

async function guardarMision(event) {
    event.preventDefault();
    const form = document.getElementById('misionForm');

    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }

    try {
        const formData = new FormData(form);
        const data = {
            id: formData.get('id') ? parseInt(formData.get('id')) : null,
            mision: formData.get('mision'),
            vision: formData.get('vision'),
            politica: formData.get('politica'),
            objetivos: formData.get('objetivos'),
            valores: formData.get('valores'),
            fecha: formData.get('fecha'),
            activo: formData.get('activo') === 'true' || formData.get('activo') === 'on' ? true : false
        };

        const response = await fetch('/api/mision/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error('Error al guardar');
        const result = await response.json();

        if (result.success) {
            if (modalMisionInstance) modalMisionInstance.hide();

            const row = document.getElementById(`row-${data.id}`);
            if (row) {
                const cells = row.querySelectorAll('td');

                cells[1].textContent = data.mision;
                cells[2].textContent = data.vision;
                cells[3].textContent = data.politica;
                cells[4].textContent = data.objetivos;
                cells[5].textContent = data.valores;
                cells[6].textContent = data.fecha;
                cells[7].innerHTML = `<span class="${data.activo ? 'badge bg-success' : 'badge bg-secondary'}">${data.activo ? 'Activo' : 'Inactivo'}</span>`;

                row.classList.add('table-info');
                setTimeout(() => row.classList.remove('table-info'), 1500);
            } else {
                window.location.reload();
            }
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error al guardar: ' + error.message);
    }
}