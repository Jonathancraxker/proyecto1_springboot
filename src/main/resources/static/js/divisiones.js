// Variable global para almacenar la instancia del modal
let modalDivisionInstance;

// Función para abrir la modal (agregar o editar)
async function abrirModalDivision(id = null) {
    try {
        if (!modalDivisionInstance) {
            modalDivisionInstance = new bootstrap.Modal(
                document.getElementById('exampleModal'), {
                }
            );
        }
        limpiarFormulario();
        modalDivisionInstance.show();
    } catch (error) {
        console.error('Error al abrir la modal:', error);
    }
}

// Función para limpiar el formulario
function limpiarFormulario() {
    const form = document.getElementById('divisonForm');
    if (form) {
        form.reset();
        form.classList.remove('was-validated');
        document.getElementById('alertaError').classList.add('d-none');
    }
}

// Función para guardar la división
async function guardarDivision(event) {
    event.preventDefault();
// Validar el formulario
const form = document.getElementById('divisonForm');

form.classList.add('was-validated');

if (!form.checkValidity()) {
console.warn('Formulario no válido');
event.preventDefault();
event.stopPropagation();
return;
}

try {
const formData = new FormData(form);
console.log('-->FormData:', Object.fromEntries(formData.entries()));
const data = {
id: formData.get('id') ? parseInt(formData.get('id')) : "null",
clave: formData.get('clave'),
nombre: formData.get('nombre'),
activo: document.getElementById('activo').checked
};

const response = await fetch('/api/division/save', {
method: 'POST',
headers: {
'Content-Type': 'application/json',
},
body: JSON.stringify(data)
});

if (!response.ok) {
throw new Error('Error al guardar');
}

const result = await response.json();

if (result.success) {
// Cerrar la modal
if (modalDivisionInstance) {
modalDivisionInstance.hide();
}

// Recargar la página después de 500ms
setTimeout(() => {
window.location.reload();
}, 500);
} else {
mostrarAlerta(result.message || 'Error al guardar la división');
}
} catch (error) {
console.error('Error:', error);
mostrarAlerta('Error al guardar: ' + error.message);
}

}