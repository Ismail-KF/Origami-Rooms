import { crearBarraNavegacion } from '../components/navbar.js';
import { establecimientoAPI } from '../api/establecimientoAPI.js';
import { crearElementoHabitacion } from '../components/habitacionComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    if (!id) {
        root.innerHTML = `<div class="alert alert-danger m-4">Error: No se proporcionó un ID de establecimiento.</div>`;
        return;
    }

    try {
        const establecimiento = await establecimientoAPI.buscarPorId(id);
        let htmlContenido = `
            <div class="container mt-4 mb-5">
                <div class="card shadow border-0 overflow-hidden">
                    <div class="bg-dark text-white p-4 d-flex justify-content-between align-items-center">
                        <div>
                            <span class="badge bg-primary mb-2 text-uppercase">${establecimiento.tipo}</span>
                            <h1 class="display-5 mb-0 fw-bold">${establecimiento.nombre}</h1>
                        </div>
                        <div class="text-end">
                            <div class="fs-3 text-warning fw-bold">${establecimiento.puntuacionMedia ? establecimiento.puntuacionMedia.toFixed(1) : 'S/P'}</div>
                            <small class="text-white-50">Puntuación Media</small>
                        </div>
                    </div>
                    
                    <div class="card-body p-4">
                        <h4 class="fw-bold mb-3">Descripción</h4>
                        <p class="lead text-secondary mb-4">${establecimiento.descripcion}</p>
                        
                        <hr class="my-4">
                        <h4 class="fw-semibold mb-3">Dirección</h3>
                        <p class="lead text-secondary mb-4">${establecimiento.direccion}</p>
                        <hr class="my-4">
                        
                        <h4 class="fw-bold mb-3">Características y Servicios del Alojamiento</h4>
                        <div class="row g-3">
                            <div class="col-md-4">
                                <strong>Categoría:</strong> ${establecimiento.estrellas} Estrellas
                            </div>
        `;

        if (establecimiento.tipo === 'hotel') {
            htmlContenido += `
                <div class="col-md-4">
                    <strong>Gimnasio:</strong> ${establecimiento.tieneGimnasio ? 'Disponible' : 'No disponible'}
                </div>
                <div class="col-md-4">
                    <strong>Restaurante:</strong> ${establecimiento.tieneRestaurante ? 'Disponible' : 'No disponible'}
                </div>
                <div class="col-md-4 mt-3">
                    <strong>Servicio de Habitaciones:</strong> ${establecimiento.tieneServicioHabitaciones ? 'Disponible' : 'No disponible'}
                </div>
            `;
        } else if (establecimiento.tipo === 'ryokan') {
            htmlContenido += `
                <div class="col-md-4">
                    <strong>Onsen Tradicional:</strong> ${establecimiento.tieneOnsen ? 'Incluido' : 'No incluido'}
                </div>
                <div class="col-md-4">
                    <strong>Servicio de Kimono / Yukata:</strong> ${establecimiento.tieneKimono ? 'Incluido' : 'No incluido'}
                </div>
                <div class="col-md-4 mt-3">
                    <strong>Jardín Japonés:</strong> ${establecimiento.tieneJardin ? 'Incluido' : 'No incluido'}
                </div>
            `;
        }

        let habitacionesHTML = '';
        if (establecimiento.habitaciones && establecimiento.habitaciones.length > 0) {
            habitacionesHTML = `
                <h3 class="mt-5 mb-4" style="font-family: 'Noto Serif', serif; color: var(--color-bosque);">
                Habitaciones Disponibles
                </h3>
                <div class="row row-cols-1 row-cols-md-2 g-4">
                    ${establecimiento.habitaciones.map(hab => `
                        <div class="col">
                            ${crearElementoHabitacion(hab)}
                        </div>
                    `).join('')}
                </div>
            `;
        } else {
            habitacionesHTML = `
                <div class="alert alert-info mt-5" role="alert">
                Actualmente no hay habitaciones registradas en este alojamiento.
                </div>
            `;
        }

        htmlContenido += `
                        </div> 
                        <div class="mt-5 d-flex gap-2">
                            <a href="index.html" class="btn btn-outline-secondary">Volver al catálogo</a>
                            <a href="editarEstablecimiento.html?id=${establecimiento.id}&tipo=${establecimiento.tipo}" class="btn btn-warning">Editar Datos</a>
                            <a href="asignarHabitacion.html?establecimientoId=${establecimiento.id}" class="btn btn-success">Añadir Habitación</a>
                            <button id="btn-eliminar" class="btn btn-danger ms-auto">Eliminar</button>
                        </div>
                    </div>
                </div>
                
                ${habitacionesHTML}
            </div>
        `;

        root.innerHTML = htmlContenido;

        document.getElementById('btn-eliminar').addEventListener('click', async () => {
            const confirmar = confirm(`¿Estás completamente seguro de eliminar a ${establecimiento.nombre}?\nEsta acción no se puede deshacer.`);
            if (confirmar) {
                try {
                    await establecimientoAPI.eliminarEstablecimiento(establecimiento.id);
                    alert('Establecimiento eliminado con éxito.');
                    window.location.href = 'index.html';
                } catch (error) {
                    alert('Error: No se pudo eliminar al establecimiento.');
                    console.error(error);
                }
            }
        });

    } catch (error) {
        alert(error.message);
        root.innerHTML = `<div class="alert alert-danger m-4">Error al conectar con el servidor para obtener los detalles del establecimiento.</div>`;
    }
});