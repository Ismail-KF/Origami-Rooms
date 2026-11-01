import { API_CONFIG } from '../config/apiConfig.js';

export async function fetchApi(method = 'GET', path, body = null) {
    const url = `${API_CONFIG.baseURL}${path}`;
    const opciones = {
        method: method,
        headers: { 'Content-Type': 'application/json' }
    };

    if (body) opciones.body = JSON.stringify(body);

    const respuesta = await fetch(url, opciones);

    if (!respuesta.ok) {
        const error = await respuesta.text();
        throw new Error(error);
    }

    if (respuesta.status === 204) return null;
    return await respuesta.json();
}