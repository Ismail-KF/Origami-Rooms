import { crearBarraNavegacion } from '../components/navbar.js';
import { habitacionAPI } from '../api/habitacionAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    const urlParams = new URLSearchParams(window.location.search);
    const habitacionId = urlParams.get('id');

    if (!habitacionId) {
        alert('Error: No se ha proporcionado un ID de habitacion valido.');
        window.location.href = 'index.html';
        return;
    }

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 750px;">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-warning text-dark">
                    <h4 class="mb-0"><i class="bi bi-pencil-square"></i> Modificar Datos de la Habitacion</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formularioHabitacion">
                        <div class="row g-3">
                            
                            <div class="col-md-6">
                                <label for="numeroHabitacion" class="form-label fw-bold">Numero de Habitacion</label>
                                <input type="number" id="numeroHabitacion" class="form-control" min="1" required>
                            </div>
                            <div class="col-md-6">
                                <label for="tipoHabitacion" class="form-label fw-bold">Tipo de Habitacion</label>
                                <select id="tipoHabitacion" class="form-select" required>
                                    <option value="INDIVIDUAL">Individual</option>
                                    <option value="DOBLE">Doble</option>
                                    <option value="SUITE">Suite</option>
                                    <option value="TRIPLE">Triple</option>
                                    <option value="ECONOMICA">Económica</option>
                                </select>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="precioPorNoche" class="form-label fw-bold">Precio por Noche</label>
                                <input type="number" step="0.01" id="precioPorNoche" class="form-control" min="0" required>
                            </div>
                            <div class="col-md-6 mt-4">
                                <label for="capacidad" class="form-label fw-bold">Capacidad Maxima (Personas)</label>
                                <input type="number" id="capacidad" class="form-control" min="1" max="10" required>
                            </div>

                            <div class="col-12 mt-5 text-end">
                                <a href="index.html" class="btn btn-outline-secondary me-2">Cancelar</a>
                                <button type="submit" class="btn btn-warning text-dark fw-bold">Guardar Cambios</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    let habitacionOriginal = null;

    try {
        habitacionOriginal = await habitacionAPI.buscarPorId(habitacionId);

        document.getElementById('numeroHabitacion').value = habitacionOriginal.numeroHabitacion;
        document.getElementById('tipoHabitacion').value = habitacionOriginal.tipoHabitacion;
        document.getElementById('precioPorNoche').value = habitacionOriginal.precioPorNoche;
        document.getElementById('capacidad').value = habitacionOriginal.capacidad;
    } catch (error) {
        console.error('Error al recuperar los datos de la habitacion:', error);
        alert('No se pudieron cargar los datos de la habitacion solicitada.');
        window.location.href = 'index.html';
        return;
    }

    document.getElementById('formularioHabitacion').addEventListener('submit', async (event) => {
        event.preventDefault();

        const inputNumero = document.getElementById('numeroHabitacion').value.trim();
        const inputTipo = document.getElementById('tipoHabitacion').value;
        const inputPrecio = document.getElementById('precioPorNoche').value;
        const inputCapacidad = document.getElementById('capacidad').value;

        const datosActualizados = {
            id: parseInt(habitacionId),
            numeroHabitacion: inputNumero || habitacionOriginal.numeroHabitacion,
            tipoHabitacion: inputTipo,
            capacidad: parseInt(inputCapacidad) || habitacionOriginal.capacidad,
            precioPorNoche: parseFloat(inputPrecio) || habitacionOriginal.precioPorNoche
        };

        try {
            await habitacionAPI.actualizarHabitacionParcialmente(habitacionId, datosActualizados);
            alert('¡Habitación actualizada con éxito!');

            const paginaAnterior = document.referrer;
            const establecimientoId = habitacionOriginal.establecimiento ? habitacionOriginal.establecimiento.id : null;

            if (paginaAnterior.includes('establecimientoDetalle.html') && establecimientoId) {
                window.location.href = `establecimientoDetalle.html?id=${establecimientoId}`;
            } else {
                window.location.href = 'habitacionLista.html';
            }
        } catch (error) {
            alert(`Error al actualizar la habitación: ${error.message}`);
        }
    });
});