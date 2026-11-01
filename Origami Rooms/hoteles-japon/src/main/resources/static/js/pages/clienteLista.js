import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoCliente } from '../components/clienteComponente.js';
import { clienteAPI } from '../api/clienteAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1>Gestión de Clientes</h1>
                <a href="crearCliente.html" class="btn btn-primary">Nuevo Cliente</a>
            </div>

            <div class="card mb-4 shadow-sm border-0 bg-light">
                <div class="card-body">
                    <form id="formularioBusqueda" class="row g-3">
                        <div class="col-md-3">
                            <select id="criterioBusqueda" class="form-select">
                                <option value="todos">Mostrar Todos</option>
                                <option value="id">Buscar por ID</option>
                                <option value="pasaporte">Buscar por Pasaporte</option>
                                <option value="telefono">Buscar por Teléfono</option>
                                <option value="email">Buscar por Email</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <input type="text" id="valorBusqueda" class="form-control" placeholder="Introduce el término de búsqueda..." disabled>
                        </div>
                        <div class="col-md-3 d-flex gap-2">
                            <button type="submit" class="btn btn-primary w-100" id="btnBuscar">Buscar</button>
                            <button type="button" class="btn btn-secondary" id="btnLimpiar" title="Restablecer">Restablecer</button>
                        </div>
                    </form>
                </div>
            </div>

            <div id="listaClientes" class="row row-cols-1 row-cols-md-2 g-3"></div>
        </div>
    `;

    const contenedorListaClientes = document.getElementById('listaClientes');
    const criterioBusqueda = document.getElementById('criterioBusqueda');
    const valorBusqueda = document.getElementById('valorBusqueda');
    const formularioBusqueda = document.getElementById('formularioBusqueda');
    const btnLimpiar = document.getElementById('btnLimpiar');

    function mostrarClientes(datos) {
        const lista = Array.isArray(datos) ? datos : (datos ? [datos] : []);

        if (lista.length === 0 || !lista[0]) {
            contenedorListaClientes.innerHTML = `
                <div class="col-12 text-center mt-4">
                    <p class="alert alert-warning">No se encontró ningún cliente con los criterios especificados.</p>
                </div>`;
            return;
        }

        contenedorListaClientes.innerHTML = lista.map(cliente => crearElementoCliente(cliente)).join('');
    }

    async function cargarTodosLosClientes() {
        try {
            contenedorListaClientes.innerHTML = '<div class="text-center w-100"><div class="spinner-border text-primary" role="status"></div></div>';
            const clientes = await clienteAPI.mostrarTodos();
            mostrarClientes(clientes);
        } catch (error) {
            console.error("Error al cargar todos:", error);
            contenedorListaClientes.innerHTML = `<div class="col-12"><div class="alert alert-danger">Error al conectar con el servidor.</div></div>`;
        }
    }

    await cargarTodosLosClientes();

    criterioBusqueda.addEventListener('change', () => {
        if (criterioBusqueda.value === 'todos') {
            valorBusqueda.value = '';
            valorBusqueda.disabled = true;
            cargarTodosLosClientes();
        } else {
            valorBusqueda.disabled = false;
            valorBusqueda.focus();
        }
    });

    formularioBusqueda.addEventListener('submit', async (evento) => {
        evento.preventDefault();

        const criterio = criterioBusqueda.value;
        const valor = valorBusqueda.value.trim();

        if (criterio !== 'todos' && !valor) {
            alert('Por favor, introduce un valor para buscar.');
            return;
        }

        try {
            let resultado = null;

            if (criterio === 'todos') {
                await cargarTodosLosClientes();
                return;
            } else if (criterio === 'id') {
                resultado = await clienteAPI.buscarPorId(valor);
            } else if (criterio === 'pasaporte') {
                resultado = await clienteAPI.buscarPorPasaporte(valor);
            } else if (criterio === 'telefono') {
                resultado = await clienteAPI.buscarPorTelefono(valor);
            } else if (criterio === 'email') {
                resultado = await clienteAPI.buscarPorEmail(valor);
            }

            mostrarClientes(resultado);

        } catch (error) {
            alert(`Búsqueda sin resultados en el servidor: ${error.message}`);
            mostrarClientes(null);
        }
    });

    btnLimpiar.addEventListener('click', () => {
        formularioBusqueda.reset();
        valorBusqueda.disabled = true;
        cargarTodosLosClientes();
    });
});