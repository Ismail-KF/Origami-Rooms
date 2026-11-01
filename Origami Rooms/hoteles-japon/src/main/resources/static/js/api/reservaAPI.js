import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const reservaAPI = {
    mostrarTodas: () => fetchApi('GET', '/reservas'),
    buscarPorId: (id) => fetchApi('GET', `/reservas/${id}`),
    buscarReservas: (clienteId, estado, fechaInicio, fechaFin) => {
        const parametros = new URLSearchParams();

        if (clienteId) parametros.append('clienteId', clienteId);
        if (estado) parametros.append('estado', estado);
        if (fechaInicio) parametros.append('fechaInicio', fechaInicio);
        if (fechaFin) parametros.append('fechaFin', fechaFin);

        const queryString = parametros.toString();
        const ruta = queryString ? `/reservas/buscar?${queryString}` : '/reservas/buscar';

        return fetchApi('GET', ruta);
    },
    crearReserva: (reserva, clienteId, habitacionesId) => {
        const parametros = new URLSearchParams();
        habitacionesId.forEach(id => parametros.append('habitacionesId', id));
        return fetchApi('POST', `/reservas/cliente/${clienteId}?${parametros.toString()}`, reserva);
    },
    actualizarReserva: (id, reserva) => fetchApi('PUT', `/reservas/${id}`, reserva),
    actualizarReservaParcialmente: (id, reserva) => fetchApi('PATCH', `/reservas/${id}`, reserva),
    calcularPrecioTotal: (id) => fetchApi('GET', `/reservas/${id}/precio`),
    eliminarReserva: (id) => fetchApi('DELETE', `/reservas/${id}`)
};