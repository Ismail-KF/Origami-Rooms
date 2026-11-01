import { crearBarraNavegacion } from '../components/navbar.js';
import { establecimientoAPI } from '../api/establecimientoAPI.js';
import { habitacionAPI } from '../api/habitacionAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const parametros = new URLSearchParams(window.location.search);
    const establecimientoIdPreseleccionado = parametros.get('establecimientoId');
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container mt-4 mb-5">
            <div class="card shadow-sm border-primary">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Crear y Asignar Nueva Habitación</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formularioHabitacion">
                        <div class="mb-4">
                            <label for="selectorEstablecimiento" class="form-label fw-bold">Seleccionar Establecimiento</label>
                            <select id="selectorEstablecimiento" class="form-select" required>
                                <option value="" disabled selected>Cargando establecimientos...</option>
                            </select>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="numeroHabitacion" class="form-label fw-bold">Número o nombre de la habitación</label>
                                <input type="text" id="numeroHabitacion" class="form-control" required placeholder="Ej. 101 o Suite Especial">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="tipoHabitacion" class="form-label fw-bold">Tipo de Habitación</label>
                                <select id="tipoHabitacion" class="form-select" required>
                                    <option value="INDIVIDUAL">Individual</option>
                                    <option value="DOBLE">Doble</option>
                                    <option value="TRIPLE">Triple</option>
                                    <option value="ECONOMICA">Económica</option>
                                    <option value="SUITE">Suite</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="capacidad" class="form-label fw-bold">Capacidad (Personas)</label>
                                <input type="number" id="capacidad" class="form-control" required min="1" max="10">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="precioPorNoche" class="form-label fw-bold">Precio por Noche (€)</label>
                                <input type="number" id="precioPorNoche" class="form-control" required step="0.01" min="0">
                            </div>
                        </div>

                        <div class="mt-4 text-end">
                            <a href="habitacionLista.html" class="btn btn-outline-secondary me-2">Cancelar</a>
                            <button type="submit" class="btn btn-primary fw-bold">Guardar Habitación</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    const formulario = document.getElementById('formularioHabitacion');
    const selectorEstablecimiento = document.getElementById('selectorEstablecimiento');

    try {
        const establecimientos = await establecimientoAPI.mostrarTodos();

        selectorEstablecimiento.innerHTML = '<option value="" disabled selected>-- Selecciona un establecimiento --</option>';

        establecimientos.forEach(establecimiento => {
            const opcion = document.createElement('option');
            opcion.value = establecimiento.id;
            opcion.textContent = `${establecimiento.nombre} (${establecimiento.region})`;
            selectorEstablecimiento.appendChild(opcion);
        });

        if (establecimientoIdPreseleccionado) {
            selectorEstablecimiento.value = establecimientoIdPreseleccionado;
        }

    } catch (error) {
        console.error("Error al cargar los establecimientos:", error);
        selectorEstablecimiento.innerHTML = '<option value="" disabled>Error al cargar alojamientos</option>';
    }

    formulario.addEventListener('submit', async (event) => {
        event.preventDefault();

        const establecimientoId = selectorEstablecimiento.value;

        const nuevaHabitacion = {
            numeroHabitacion: document.getElementById('numeroHabitacion').value,
            tipoHabitacion: document.getElementById('tipoHabitacion').value,
            capacidad: parseInt(document.getElementById('capacidad').value, 10),
            precioPorNoche: parseFloat(document.getElementById('precioPorNoche').value)
        };

        try {
            await habitacionAPI.crearHabitacion(nuevaHabitacion, establecimientoId);
            alert('Habitación creada y asignada con éxito');
            if (establecimientoIdPreseleccionado) {
                window.location.href = `establecimientoDetalle.html?id=${establecimientoIdPreseleccionado}`;
            } else {
                window.location.href = 'habitacionLista.html';
            }
        } catch (error) {
            alert(`Error al crear la habitación: ${error.message}`);
        }
    });
});