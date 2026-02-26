// Variable global para almacenar la instancia del modal
let modalOfertaInstance;

async function abrirModalOferta(id = null) {
try {
limpiarFormulario();
// Obtener el título según si es agregar o editar
const titulo = document.getElementById('exampleModalLabel');

if (id) {
// Modo edición
titulo.textContent = 'Editar Oferta Educativa';

// Obtener los datos de la división
const response = await fetch(`/api/oferta/${id}`);
if (!response.ok) {
throw new Error('Error al cargar los datos');
}

const oferta = await response.json();

// Llenar el formulario con los datos
document.getElementById('id').value = oferta.id;
document.getElementById('nombreOferta').value = oferta.nombreOferta;
document.getElementById('modalidad').value = oferta.modalidad;
document.getElementById('imagen').value = oferta.imagen;
document.getElementById('division').value = oferta.division ? oferta.division.id : '';
} else {
// Modo agregar
titulo.textContent = 'Agregar Oferta Educativa';
document.getElementById('id').value = '';
}

if (!modalOfertaInstance) {
modalOfertaInstance = new bootstrap.Modal(
document.getElementById('exampleModal'));
}
modalOfertaInstance.show();

} catch (error) {
console.error('Error al abrir la modal:', error);
}
}

// Función para limpiar el formulario
function limpiarFormulario() {
    const form = document.getElementById('ofertaForm');
    if (form) {
        form.reset();
        form.classList.remove('was-validated');
        document.getElementById('alertaError').classList.add('d-none');
    }
}

// Función para guardar la oferta educativa
async function guardarOfertaEducativa(event) {
    event.preventDefault();
// Validar el formulario
const form = document.getElementById('ofertaForm');

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
nombreOferta: formData.get('nombreOferta'),
modalidad: formData.get('modalidad'),
imagen: formData.get('imagen'),
division: formData.get('division') ? { id: parseInt(formData.get('division')) } : null
};

const response = await fetch('/api/oferta/save', {
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
if (modalOfertaInstance) {
modalOfertaInstance.hide();
}

const row = document.getElementById(`row-${data.id}`);
if (row) {
    const cells = row.querySelectorAll('td');
    cells[1].textContent = data.nombreOferta;
    cells[2].textContent = data.modalidad;
    const img = cells[3].querySelector('img');
        if (img) {
            img.src = data.imagen;
        }
    const selectDivision = document.getElementById('division');
    const nombreDivision = selectDivision.options[selectDivision.selectedIndex].text;
    cells[4].textContent = nombreDivision;
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