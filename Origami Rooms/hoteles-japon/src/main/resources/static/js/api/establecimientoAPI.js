import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const establecimientoAPI = {
    mostrarTodos: () => fetchApi('GET', '/establecimientos'),
    buscarPorId: (id) => fetchApi('GET', `/establecimientos/${id}`),
    buscarEstablecimientos: (nombre, direccion, region, puntuacionMediaMinima) => {
        const parametros = new URLSearchParams();

        if (nombre) parametros.append('nombre', nombre);
        if (direccion) parametros.append('direccion', direccion);
        if (region) parametros.append('region', region);
        if (puntuacionMediaMinima) parametros.append('puntuacionMediaMinima', puntuacionMediaMinima);

        const queryString = parametros.toString();
        const ruta = queryString ? `/establecimientos/buscar?${queryString}` : '/establecimientos/buscar';

        return fetchApi('GET', ruta);
    },
    crearEstablecimiento: (establecimiento) => fetchApi('POST', '/establecimientos', establecimiento),
    actualizarEstablecimiento: (id, establecimiento) => fetchApi('PUT', `/establecimientos/${id}`, establecimiento),
    actualizarEstablecimientoParcialmente: (id, establecimiento) => fetchApi('PATCH', `/establecimientos/${id}`, establecimiento),
    eliminarEstablecimiento: (id) => fetchApi('DELETE', `/establecimientos/${id}`)
};