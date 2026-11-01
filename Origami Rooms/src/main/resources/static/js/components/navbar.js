export function crearBarraNavegacion() {
    return `
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4">
            <div class="container-fluid">
                <img src="img/Logo-OrigamiRooms.png" alt="Logo Origami Rooms" height="70" class="d-inline-block align-middle me-2">
                <a class="navbar-brand text-light" href="index.html">Origami Rooms<br>折紙ルームズ</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link text-light" href="index.html">Establecimientos</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-light" href="habitacionLista.html">Habitaciones</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-light" href="clienteLista.html">Clientes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-light" href="reservaLista.html">Reservas</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    `;
}