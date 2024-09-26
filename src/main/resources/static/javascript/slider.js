let index = 0;
const slides = document.querySelector('.slider-images');
const slideCount = document.querySelectorAll('.slider-image').length;

function showSlides() {
	index++;
	if (index >= slideCount) {
		index = 0;
	}
	const offset = -index * 100;
	slides.style.transform = `translateX(${offset}%)`;
}

setInterval(showSlides, 3000); // Cambia la imagen cada 3 segundos