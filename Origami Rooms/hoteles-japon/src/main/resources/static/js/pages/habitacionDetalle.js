import { crearBarraNavegacion } from '../components/navbar.js';
import { habitacionAPI } from '../api/habitacionAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    const parametros = new URLSearchParams(window.location.search);
    const idHabitacion = parametros.get('id');
    const root = document.getElementById('root');

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    try {
        const habitacion = await habitacionAPI.buscarPorId(idHabitacion);

        document.title = `Habitación ${habitacion.numeroHabitacion} - ${habitacion.establecimiento?.nombre || 'Hoteles Nihon'}`;

        root.innerHTML = `
            <div class="container mt-4">
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center py-3">
                        <h2 class="mb-0">Detalles de la Habitación #${habitacion.numeroHabitacion}</h2>
                        <span class="badge bg-light text-primary fs-5">${habitacion.precioPorNoche} € / noche</span>
                    </div>
                    
                    <div class="card-body p-4">
                        <div class="row g-4">
                            <div class="col-md-6">
                                <h4 class="text-secondary border-bottom pb-2">Información General</h4>
                                <p class="fs-5"><strong>Tipo:</strong> ${habitacion.tipoHabitacion}</p>
                                <p class="fs-5"><strong>Capacidad:</strong> ${habitacion.capacidad} persona(s)</p>
                                <p class="fs-5"><strong>ID:</strong> ${habitacion.id}</p>
                                <br>
                                <button id = "btn-eliminar" class="btn btn-danger ms-auto">Eliminar</button>
                                <br>
                                <br>
                                <a href="editarHabitacion.html?id=${habitacion.id}" class="btn btn-warning">Editar Datos</a>
                            </div>
                            
                            <div class="col-md-6">
                                <h4 class="text-secondary border-bottom pb-2">Establecimiento</h4>
                                ${habitacion.establecimiento ? `
                                    <p class="fs-5"><strong>Hotel:</strong> ${habitacion.establecimiento.nombre}</p>
                                    <p class="fs-5"><strong>Región:</strong> ${habitacion.establecimiento.region}</p>
                                    <button id="btnVerEstablecimiento" class="btn btn-outline-primary mt-2">
                                        Ver Establecimiento al que pertenece
                                    </button>
                                ` : '<p class="text-muted fs-5">No tiene ningún establecimiento asignado.</p>'}
                            </div>
                        </div>

                        <div class="row mt-4">
                            <div class="col-12">
                                <h4 class="text-secondary border-bottom pb-2">Reservas</h4>
                                ${habitacion.reservas && habitacion.reservas.length > 0 ? `
                                    <div class="table-responsive">
                                        <table class="table table-hover align-middle mt-2">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>ID Reserva</th>
                                                    <th>Fecha Entrada</th>
                                                    <th>Fecha Salida</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                ${habitacion.reservas.map(reserva => `
                                                    <tr>
                                                        <td><strong>#${reserva.id}</strong></td>
                                                        <td>${reserva.fechaEntrada}</td>
                                                        <td>${reserva.fechaSalida}</td>
                                                        <td>
                                                            <a href="reservaDetalle.html?id=${reserva.id}" class="btn btn-sm btn-secondary">Ver</a>
                                                        </td>
                                                    </tr>
                                                `).join('')}
                                            </tbody>
                                        </table>
                                    </div>
                                ` : '<div class="alert alert-light border text-muted mt-2">Sin reservas asignadas actualmente a esta habitación.</div>'}
                            </div>
                        </div>
                    </div>

                    <div class="card-footer bg-light p-3 d-flex justify-content-between">
                        <a href="habitacionLista.html" class="btn btn-secondary">Volver a la Lista</a>
                    </div>
                </div>
            </div>
        `;

        const btnEstablecimiento = document.getElementById('btnVerEstablecimiento');
        if (btnEstablecimiento && habitacion.establecimiento) {
            btnEstablecimiento.addEventListener('click', () => {
                window.location.href = `establecimientoDetalle.html?id=${habitacion.establecimiento.id}`;
            });
        }

        document.getElementById('btn-eliminar').addEventListener('click', async () => {
            const confirmar = confirm(`¿Estás completamente seguro de eliminar esta habitación?\nEsta acción no se puede deshacer.`);
            if (confirmar) {
                try {
                    await habitacionAPI.eliminarHabitacion(habitacion.id);
                    alert('Habitación eliminada con éxito.');
                    window.location.href = 'habitacionLista.html';
                } catch (error) {
                    alert('Error: No se pudo eliminar la habitación.');
                    console.error(error);
                }
            }
        });

    } catch (error) {
        alert(`Error al cargar la habitación: ${error.message}`);
        root.innerHTML = `
            <div class="container mt-4">
                <div class="alert alert-danger shadow-sm">
                    No se pudo cargar la información de la habitación. Comprueba que el ID sea correcto.
                </div>
                <a href="habitacionLista.html" class="btn btn-secondary mt-2">Volver a la lista de habitaciones</a>
            </div>
        `;
    }
});