import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const habitacionAPI = {
    mostrarTodas: () => fetchApi('GET', '/habitaciones'),
    buscarPorId: (id) => fetchApi('GET', `/habitaciones/${id}`),
    buscarHabitaciones: (tipo, capacidadMinima, precioMaximo) => {
        const parametros = new URLSearchParams();

        if (tipo) parametros.append('tipo', tipo);
        if (capacidadMinima) parametros.append('capacidadMinima', capacidadMinima);
        if (precioMaximo) parametros.append('precioMaximo', precioMaximo);

        const queryString = parametros.toString();
        const ruta = queryString ? `/habitaciones/buscar?${queryString}` : '/habitaciones/buscar';

        return fetchApi('GET', ruta);
    },

    crearHabitacion: (habitacion, establecimientoId) => fetchApi('POST', `/habitaciones/establecimiento/${establecimientoId}`, habitacion),
    actualizarHabitacion: (id, habitacion) => fetchApi('PUT', `/habitaciones/${id}`, habitacion),
    actualizarHabitacionParcialmente: (id, habitacion) => fetchApi('PATCH', `/habitaciones/${id}`, habitacion),
    estaDisponible: (habitacionId, fechaEntrada, fechaSalida) => {
        const parametros = new URLSearchParams();

        parametros.append('fechaEntrada', fechaEntrada);
        parametros.append('fechaSalida', fechaSalida);

        const ruta = `/habitaciones/${habitacionId}/disponibilidad?${parametros.toString()}`;

        return fetchApi('GET', ruta);
    },
    eliminarHabitacion: (id) => fetchApi('DELETE', `/habitaciones/${id}`)
};