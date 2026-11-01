import { crearBarraNavegacion } from '../components/navbar.js';
import { reservaAPI } from '../api/reservaAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    const urlParams = new URLSearchParams(window.location.search);
    const reservaId = urlParams.get('id');

    if (!reservaId) {
        alert('Error: No se ha proporcionado un ID de reserva válido.');
        window.location.href = 'reservaLista.html';
        return;
    }

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 750px;">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-warning text-dark">
                    <h4 class="mb-0"><i class="bi bi-pencil-square"></i> Modificar Datos de la Reserva</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formularioReserva">
                        <div class="row g-3">
                            
                            <div class="col-md-6">
                                <label class="form-label fw-bold text-muted">Cliente</label>
                                <input type="text" id="infoCliente" class="form-control bg-light" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold text-muted">Habitación(es) Asignada(s)</label>
                                <input type="text" id="infoHabitacion" class="form-control bg-light" readonly>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="fechaInicio" class="form-label fw-bold">Fecha de Entrada (Check-in)</label>
                                <input type="date" id="fechaInicio" class="form-control" required>
                            </div>
                            <div class="col-md-6 mt-4">
                                <label for="fechaFin" class="form-label fw-bold">Fecha de Salida (Check-out)</label>
                                <input type="date" id="fechaFin" class="form-control" required>
                            </div>

                            <div class="col-md-12 mt-4">
                                <label for="estadoReserva" class="form-label fw-bold">Estado del Registro</label>
                                <select id="estadoReserva" class="form-select" required>
                                    <option value="PENDIENTE">Pendiente de Pago</option>
                                    <option value="COMPLETADA">Completada / Pagada</option>
                                    <option value="CANCELADA">Cancelada</option>
                                </select>
                            </div>

                            <div class="col-12 mt-5 text-end">
                                <a href="reservaDetalle.html?id=${reservaId}" id="btnCancelar" class="btn btn-outline-secondary me-2">Cancelar</a>
                                <button type="submit" class="btn btn-warning text-dark fw-bold">Guardar Cambios</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    let reservaOriginal = null;

    try {
        reservaOriginal = await reservaAPI.buscarPorId(reservaId);

        const nombreCliente = `${reservaOriginal.cliente.nombre} ${reservaOriginal.cliente.apellido}`;

        const numHabitaciones = reservaOriginal.habitaciones.map(habitaciones => `Habitación ${habitaciones.numeroHabitacion}`).join(', ');

        document.getElementById('infoCliente').value = nombreCliente;
        document.getElementById('infoHabitacion').value = numHabitaciones;
        document.getElementById('fechaInicio').value = reservaOriginal.fechaEntrada || ''
        document.getElementById('fechaFin').value = reservaOriginal.fechaSalida || '';
        document.getElementById('estadoReserva').value = reservaOriginal.estadoReserva || 'PENDIENTE';

    } catch (error) {
        console.error('Error al recuperar los datos de la reserva:', error);
        alert('No se pudieron procesar los datos de la reserva especificada.');
        window.location.href = 'reservaLista.html';
        return;
    }

    document.getElementById('formularioReserva').addEventListener('submit', async (event) => {
        event.preventDefault();

        const entrada = document.getElementById('fechaInicio').value;
        const salida = document.getElementById('fechaFin').value;
        const estadoSeleccionado = document.getElementById('estadoReserva').value;

        if (new Date(entrada) >= new Date(salida)) {
            alert('Error: La fecha de salida debe ser estrictamente posterior a la fecha de entrada.');
            return;
        }

        const datosActualizados = {
            id: parseInt(reservaId),
            fechaEntrada: entrada,
            fechaSalida: salida,
            estadoReserva: estadoSeleccionado
        };

        console.log("Enviando cambios al backend:", datosActualizados);

        try {
            await reservaAPI.actualizarReservaParcialmente(reservaId, datosActualizados);
            alert('La reserva ha sido modificada con éxito.');
            window.location.href = `reservaDetalle.html?id=${reservaId}`;
        } catch (error) {
            alert(`Error al actualizar la reserva: ${error.message}`);
        }
    });
});