const outline = document.querySelector(".cursor-outline");

let mouseX = 0;
let mouseY = 0;


window.addEventListener("mousemove", (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;

    // El 'transform' con 'translate' es más eficiente para animaciones
    outline.style.transform = `translate(${mouseX}px, ${mouseY}px)`;
});
