import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const clienteAPI = {
    mostrarTodos: () => fetchApi('GET', '/clientes'),
    buscarPorId: (id) => fetchApi('GET', `/clientes/${id}`),
    buscarPorEmail: (email) => fetchApi('GET', `/clientes/email/${email}`),
    buscarPorTelefono: (telefono) => fetchApi('GET', `/clientes/telefono/${telefono}`),
    buscarPorPasaporte: (pasaporte) => fetchApi('GET', `/clientes/pasaporte/${pasaporte}`),
    crearCliente: (cliente) => fetchApi('POST', '/clientes', cliente),
    actualizarCliente: (id, cliente) => fetchApi('PUT', `/clientes/${id}`, cliente),
    actualizarClienteParcialmente: (id, cliente) => fetchApi('PATCH', `/clientes/${id}`, cliente),
    eliminarCliente: (id) => fetchApi('DELETE', `/clientes/${id}`)
};