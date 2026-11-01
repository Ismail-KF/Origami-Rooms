import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const ryokanAPI = {
    mostrarTodos: () => fetchApi('GET', '/ryokan'),
    buscarPorId: (id) => fetchApi('GET', `/ryokan/${id}`),
    buscarRyokan: (region, puntuacionMediaMinima, estrellas, tieneOnsen, tieneKimono, tieneJardin) => {
        const parametros = new URLSearchParams();

        if (region) parametros.append('region', region);
        if (puntuacionMediaMinima) parametros.append('puntuacionMediaMinima', puntuacionMediaMinima);
        if (estrellas) parametros.append('estrellas', estrellas);
        if (tieneOnsen) parametros.append('tieneOnsen', tieneOnsen);
        if (tieneKimono) parametros.append('tieneKimono', tieneKimono);
        if (tieneJardin) parametros.append('tieneJardin', tieneJardin);

        const queryString = parametros.toString();
        const ruta = queryString ? `/ryokan/buscar?${queryString}` : '/ryokan/buscar';

        return fetchApi('GET', ruta);
    },
    crearRyokan: (ryokan) => fetchApi('POST', '/ryokan', ryokan),
    actualizar: (id, ryokan) => fetchApi('PUT', `/ryokan/${id}`, ryokan),
    actualizarParcialmente: (id, ryokan) => fetchApi('PATCH', `/ryokan/${id}`, ryokan),
    eliminarRyokan: (id) => fetchApi('DELETE', `/ryokan/${id}`)
};