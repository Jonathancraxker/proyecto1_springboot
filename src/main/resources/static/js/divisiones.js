// Variable global para almacenar la instancia del modal
let modalDivisionInstance;

async function abrirModalDivision(id = null) {
try {
limpiarFormulario();
// Obtener el título según si es agregar o editar
const titulo = document.getElementById('exampleModalLabel');

if (id) {
// Modo edición
titulo.textContent = 'Editar División';

// Obtener los datos de la división
const response = await fetch(`/api/division/${id}`);
if (!response.ok) {
throw new Error('Error al cargar los datos');
}

const division = await response.json();

// Llenar el formulario con los datos
document.getElementById('id').value = division.id;
document.getElementById('clave').value = division.clave;
document.getElementById('nombre').value = division.nombre;
document.getElementById('activo').checked = division.activo;
} else {
// Modo agregar
titulo.textContent = 'Agregar División';
document.getElementById('id').value = '';
}

if (!modalDivisionInstance) {
modalDivisionInstance = new bootstrap.Modal(
document.getElementById('exampleModal'));
}
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

const row = document.getElementById(`row-${data.id}`);
if (row) {
    const cells = row.querySelectorAll('td');
    cells[1].textContent = data.clave;
    cells[2].textContent = data.nombre;
    cells[3].textContent = data.activo ? "Sí" : "No";
} else {
    setTimeout(() => {
        window.location.reload();
    }, 500);
}
}
} catch (error) {
console.error('Error:', error);
mostrarAlerta('Error al guardar: ' + error.message);
}

}