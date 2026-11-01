import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const hotelAPI = {
    mostrarTodos: () => fetchApi('GET', '/hoteles'),
    buscarPorId: (id) => fetchApi('GET', `/hoteles/${id}`),
    buscarHoteles: (region, puntuacionMediaMinima, estrellas, tieneRestaurante, tieneGimnasio, tieneServicioHabitaciones) => {
        const parametros = new URLSearchParams();

        if (region) parametros.append('region', region);
        if (puntuacionMediaMinima) parametros.append('puntuacionMediaMinima', puntuacionMediaMinima);
        if (estrellas) parametros.append('estrellas', estrellas);
        if (tieneRestaurante) parametros.append('tieneRestaurante', tieneRestaurante);
        if (tieneGimnasio) parametros.append('tieneGimnasio', tieneGimnasio);
        if (tieneServicioHabitaciones) parametros.append('tieneServicioHabitaciones', tieneServicioHabitaciones);

        const queryString = parametros.toString();
        const ruta = queryString ? `/hoteles/buscar?${queryString}` : '/hoteles/buscar';

        return fetchApi('GET', ruta);
    },
    crearHotel: (hotel) => fetchApi('POST', '/hoteles', hotel),
    actualizar: (id, hotel) => fetchApi('PUT', `/hoteles/${id}`, hotel),
    actualizarParcialmente: (id, hotel) => fetchApi('PATCH', `/hoteles/${id}`, hotel),
    eliminarHotel: (id) => fetchApi('DELETE', `/hoteles/${id}`)
};