import { crearBarraNavegacion } from '../components/navbar.js';
import { reservaAPI } from '../api/reservaAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    const urlParams = new URLSearchParams(window.location.search);
    const habitacionIdDesdeUrl = urlParams.get('habitacionId');

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 750px;">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-success text-white">
                    <h4 class="mb-0"><i class="bi bi-calendar-plus"></i> Registrar Nueva Reserva</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formularioCrearReserva">
                        <div class="row g-3">
                            
                            <div class="col-md-6">
                                <label for="clienteId" class="form-label fw-bold">ID del Cliente</label>
                                <input type="number" id="clienteId" class="form-control" placeholder="Ej: 1" required>
                            </div>
                            
                            <div class="col-md-6">
                                <label for="habitacionesId" class="form-label fw-bold">ID de Habitación</label>
                                <input type="text" id="habitacionesId" class="form-control" 
                                    placeholder="Ej: 4" 
                                    value="${habitacionIdDesdeUrl || ''}" required>
                            </div>

                            <hr class="my-4">

                            <div class="col-md-6">
                                <label for="fechaEntrada" class="form-label fw-bold">Fecha de Entrada</label>
                                <input type="date" id="fechaEntrada" class="form-control" required>
                            </div>
                            
                            <div class="col-md-6">
                                <label for="fechaSalida" class="form-label fw-bold">Fecha de Salida</label>
                                <input type="date" id="fechaSalida" class="form-control" required>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="estadoReserva" class="form-label fw-bold">Estado Inicial</label>
                                <select id="estadoReserva" class="form-select" required>
                                    <option value="PENDIENTE" selected>Pendiente de Pago</option>
                                    <option value="COMPLETADA">Completada / Pagada</option>
                                    <option value="CANCELADA">Cancelada</option>
                                </select>
                            </div>

                            <div class="col-12 mt-5 text-end">
                                <a href="reservaLista.html" class="btn btn-outline-secondary me-2">Volver al Listado</a>
                                <button type="submit" class="btn btn-success fw-bold px-4">Confirmar y Guardar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    document.getElementById('formularioCrearReserva').addEventListener('submit', async (event) => {
        event.preventDefault();

        const entrada = document.getElementById('fechaEntrada').value;
        const salida = document.getElementById('fechaSalida').value;

        if (new Date(entrada) >= new Date(salida)) {
            alert('Error: La fecha de salida debe ser estrictamente posterior a la fecha de entrada.');
            return;
        }

        const clienteId = document.getElementById('clienteId').value.trim();
        const habitacionesInput = document.getElementById('habitacionesId').value;

        const habitacionesId = habitacionesInput.split(',')
            .map(id => parseInt(id.trim()))
            .filter(id => !isNaN(id));

        if (habitacionesId.length === 0) {
            alert('Por favor, introduce al menos un ID de habitación válido.');
            return;
        }

        const nuevaReserva = {
            fechaEntrada: entrada,
            fechaSalida: salida,
            estadoReserva: document.getElementById('estadoReserva').value,
        };

        try {
            await reservaAPI.crearReserva(nuevaReserva, clienteId, habitacionesId);

            alert('¡La reserva ha sido creada y registrada con éxito!');
            window.location.href = 'reservaLista.html';

        } catch (error) {
            alert(`Error al crear la reserva: ${error.message}`);
        }
    });
});