let calcScrollValue = () => {
    let scrollProgress = document.getElementById("progress-scroll");
    let pos = document.documentElement.scrollTop;
    let calcHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    let scrollValue = Math.round((pos * 100) / calcHeight);

    // Si bajamos más de 100px, mostramos el botón
    if (pos > 100) {
        scrollProgress.style.display = "grid";
    } else {
        scrollProgress.style.display = "none";
    }

    // Al hacer click, sube suavemente
    scrollProgress.addEventListener("click", () => {
        document.documentElement.scrollTop = 0;
    });

    // Actualizamos el color del borde según el porcentaje
    scrollProgress.style.background = `conic-gradient(var(--primary) ${scrollValue}%, #eeeeee ${scrollValue}%)`;
};

// Ejecutar al hacer scroll y al cargar la página
window.onscroll = calcScrollValue;
window.onload = calcScrollValue;